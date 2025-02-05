package library.services.impl;

import library.dto.PasswordDto;
import library.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import library.dto.UserDto;
import library.dto.PageWiseResDto;
import library.exception.CustomException;
import library.models.User;
import library.repository.UserRepo;
import library.services.UserService;
import library.services.mappers.UserMapper;
import library.utils.EmailService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final AddressService addressService;
    private final EmailService emailService;

    @Value("${otp-length}")
    private String optLength;

    private static Map<String, Integer> optMap = new HashMap<>();
    private static Map<String, String> tokenMap = new HashMap<>();

    @Override
    public UserDto save(UserDto req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new CustomException("Email already registered", HttpStatus.BAD_REQUEST);
        if (!req.getPassword().equals(req.getConfirmPassword()))
            throw new CustomException("Confirm Password and Password must be same", HttpStatus.BAD_REQUEST);
        User user = userRepo.save(userMapper.dtoToEntity(req));
        if (req.getAddress() != null) {
            req.getAddress().parallelStream().forEach(address -> {
                address.setUserId(user.getId());
                addressService.saveAddress(address);
            });
        }
        emailService.sendMail(user.getEmail(), "Account Registration", "Your account has been successfully registered.");
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto findById(long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        return userMapper.entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(Boolean status) {
        List<User> userList = userRepo.findAllByIsActive(status);
        return userList.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public PageWiseResDto<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, Boolean status) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> users = userRepo.findAllPagewiseByIsActive(pageable, status);
        List<UserDto> res = users.stream().map(userMapper::entityToDto).collect(Collectors.toList());
        PageWiseResDto<UserDto> pageWiseResDto = new PageWiseResDto<>();
        pageWiseResDto.setRes(res);
        pageWiseResDto.setTotalPages(users.getTotalPages());
        pageWiseResDto.setTotalElements(users.getTotalElements());
        pageWiseResDto.setCurrentPage(users.getNumber());
        pageWiseResDto.setPageSize(users.getSize());
        pageWiseResDto.setLast(users.isLast());
        return pageWiseResDto;
    }

    @Override
    public UserDto updateById(UserDto req) {
        User user = userRepo.findById(req.getId()).orElseThrow(() -> new CustomException("User Not Found", HttpStatus.NOT_FOUND));
        user.setFirstName(req.getFirstName());
        user.setMiddleName(req.getMiddleName());
        user.setLastName(req.getLastName());
        if (userRepo.existsByEmail(req.getEmail()))
            throw new CustomException("Email already registered", HttpStatus.BAD_REQUEST);
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUpdatedDate(LocalDate.now());
        return userMapper.entityToDto(userRepo.save(user));
    }

    @Override
    public String forgotPassword(PasswordDto req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            int otp = (int) (Math.pow(10, Integer.parseInt(optLength) - 1) + Math.random() * 9 * Math.pow(10, Integer.parseInt(optLength) - 1));
            optMap.put(req.getEmail(), otp);
        }
        return "Please check you email for OPT to change password";
    }

    @Override
    public PasswordDto validateOTP(PasswordDto req) {
        if (optMap.containsKey(req.getEmail())) {
            if (optMap.get(req.getEmail()).equals(req.getOtp())) {
                String token="just_a_random_token";
                tokenMap.put(req.getEmail(),token);
                return PasswordDto.builder().token(token).build();
            } else throw new CustomException("Invalid request", HttpStatus.BAD_REQUEST);
        } else throw new CustomException("Invalid request", HttpStatus.BAD_REQUEST);
    }

    @Override
    public String changePassword(PasswordDto req) {
        if (req.getOldPassword() != null && !req.getOldPassword().isEmpty()) {
            User user=userRepo.findByUsername(req.getEmail()).orElseThrow(()->new CustomException("User not found",HttpStatus.NOT_FOUND));
            if(user.getPassword().equals(new BCryptPasswordEncoder().encode(req.getOldPassword()))){
                user.setPassword(new BCryptPasswordEncoder().encode(req.getNewPassword()));
                userRepo.save(user);
                return "Password changed successfully";
            }else throw new CustomException("Incorrect old password", HttpStatus.BAD_REQUEST);
        } else {
            if (tokenMap.containsKey(req.getEmail())) {
                if (tokenMap.get(req.getEmail()).equals(req.getToken())) {
                    User user=userRepo.findByUsername(req.getEmail()).orElseThrow(()->new CustomException("User not found",HttpStatus.NOT_FOUND));
                    user.setPassword(new BCryptPasswordEncoder().encode(req.getNewPassword()));
                    userRepo.save(user);
                    return "Password changed successfully";
                } else throw new CustomException("Invalid request", HttpStatus.BAD_REQUEST);
            } else throw new CustomException("Invalid request", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String deleteById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        user.setIsActive(false);
        user.setUpdatedDate(LocalDate.now());
        userRepo.save(user);
        emailService.sendMail(user.getEmail(), "Account Deletion", "Your account will be deleted within a month. ");
        return "Your account will be deleted within a month";
    }


}
