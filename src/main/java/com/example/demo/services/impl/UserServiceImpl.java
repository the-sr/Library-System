package com.example.demo.services.impl;

import com.example.demo.config.Security.AuthenticationFacade;
import com.example.demo.exception.CustomException;
import com.example.demo.models.Address;
import com.example.demo.models.OTP;
import com.example.demo.models.User;
import com.example.demo.dto.req.ResetPassReq;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.res.PagewiseRes;
import com.example.demo.projection.UserProjection;
import com.example.demo.repository.AddressRepo;
import com.example.demo.repository.OTPRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.AddressService;
import com.example.demo.services.UserService;
import com.example.demo.services.mappers.AddressMapper;
import com.example.demo.services.mappers.UserMapper;
import com.example.demo.utils.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final AddressService addressService;
    private final AddressRepo addressRepo;
    private final AuthenticationFacade facade;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final OTPRepo otpRepo;

    @Override
    public String save(UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword()))
            throw new CustomException("Confirm Password and Password must be same", HttpStatus.BAD_REQUEST);
        User user = userMapper.dtoToEntity(userDto);
        user.setId(userRepo.findNextId());
        userRepo.save(user);
        emailService.sendMail(user.getEmail(), "Account Registration",
                "Your account has been successfully registered.");
        if (userDto.getAddress() != null) {
            userDto.getAddress().forEach(address -> {
                addressService.addAddress(address, user.getId());
            });
        }
        return "User successfully created";
    }

    @Override
    public UserDto findById(long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty())
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        UserDto res = userMapper.entityToDto(user.get());
        // List<Address> addressList=addressRepo.findByUserId(id);
        // if(!addressList.isEmpty())
        // addressList.forEach(address -> {
        // res.getAddress().add(addressMapper.entityToDto(address));
        // });
        return res;
    }

    @Override
    public UserDto findByEmail(String email) {
        Optional<UserProjection> user = userRepo.findByEmail(email);
        if (user.isEmpty())
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        UserDto res = userMapper.projectionToDto(user.get());
        List<Address> addressList = addressRepo.findByUserId(res.getId());
        addressList.forEach(address -> {
            res.getAddress().add(addressMapper.entityToDto(address));
        });
        return res;
    }

    @Override
    public String forgotPassword(String email) {
        UserProjection user = userRepo.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        int otp = 100000 + new Random().nextInt(900000);
        String body = String.format("Your OTP to reset password is %d. It is valid for next 5 minutes.", otp);
        emailService.sendMail(user.getEmail(), "Password Reset Request", body);
        otpRepo.save(OTP.builder().otp(otp).email(email).createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5)).build());
        return "OTP is sent to your email, use otp to reset your password.";
    }

    @Override
    public String validateOTP(int otp, String email) {
        if (!otpRepo.existsByOtpAndEmail(otp, email))
            throw new CustomException("Invalid OTP", HttpStatus.BAD_REQUEST);
        return "OTP is valid";
    }

    @Override
    public String resetPassword(String email, String password) {
        long userId = userRepo.findByEmail(email)
                .orElseThrow(() -> new CustomException("User Not found", HttpStatus.NOT_FOUND)).getId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CustomException("User Not Found", HttpStatus.NOT_FOUND));
        user.setPassword(password);
        userRepo.save(user);
        emailService.sendMail(user.getEmail(), "Password Reset Request", "Your password has been successfully reset.");
        return "Password reset successful";
    }

    @Override
    public String changePassword(ResetPassReq passReq) {
        long userId = facade.getAuthentication().getUserId();
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty())
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        if (passReq.getOldPassword().equals(user.get().getPassword())) {
            user.get().setPassword(passReq.getNewPassword());
            userRepo.save(user.get());
        } else
            throw new CustomException("Old password is not correct", HttpStatus.BAD_REQUEST);
        return "Password changed successfully";
    }

    @Override
    public List<UserDto> findAllActiveUsers() {
        List<UserProjection> userList = userRepo.findAllByIsActive();
        return getUserDtos(userList);
    }

    @Override
    public List<UserDto> findAllInactiveUsers() {
        List<UserProjection> userList = userRepo.findAllByInactive();
        return getUserDtos(userList);
    }

    @Override
    public UserDto updateById(long id, UserDto userDto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User Not Found", HttpStatus.NOT_FOUND));
        user.setFirstName(userDto.getFirstName().trim());
        user.setMiddleName(userDto.getMiddleName().trim());
        user.setLastName(userDto.getLastName().trim());
        user.setEmail(userDto.getEmail().trim());
        user.setPhone(userDto.getPhone().trim());
        user = userRepo.save(user);
        return userMapper.entityToDto(user);
    }

    @Override
    public String deleteById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        user.setActive(false);
        userRepo.save(user);
        emailService.sendMail(user.getEmail(), "Account Deletion", "Your account will be deleted within a week.");
        return "User Deleted Successfully";
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
        List<UserDto> res = new ArrayList<>();
        userList.forEach(user -> {
            res.add(userMapper.entityToDto(user));
        });
        res.forEach(user -> {
            List<Address> addressList = addressRepo.findByUserId(user.getId());
            addressList.forEach(address -> {
                user.getAddress().add(addressMapper.entityToDto(address));
            });
        });
        return res;
    }

    @Override
    public PagewiseRes<UserDto> getAllUsersPagewise(Integer pageNumber, Integer pageSize, String sortBy,
            String sortDirection) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<UserProjection> users = userRepo.findAllPagewise(pageable);
        List<UserDto> res = new ArrayList<>();
        users.forEach(user -> {
            UserDto userDto = userMapper.projectionToDto(user);
            List<Address> addressList = addressRepo.findByUserId(user.getId());
            addressList.forEach(address -> {
                userDto.getAddress().add(addressMapper.entityToDto(address));
            });
            res.add(userDto);
        });
        PagewiseRes<UserDto> pagewiseRes = new PagewiseRes<>();
        pagewiseRes.setRes(res);
        pagewiseRes.setTotalPages(users.getTotalPages());
        pagewiseRes.setTotalElements(users.getTotalElements());
        pagewiseRes.setCurrentPage(users.getNumber());
        pagewiseRes.setPageSize(users.getSize());
        pagewiseRes.setLast(users.isLast());
        return pagewiseRes;
    }

    private List<UserDto> getUserDtos(List<UserProjection> userList) {
        List<UserDto> res = new ArrayList<>();
        userList.forEach(user -> {
            res.add(userMapper.projectionToDto(user));
        });
        res.forEach(user -> {
            List<Address> addressList = addressRepo.findByUserId(user.getId());
            addressList.forEach(address -> {
                user.getAddress().add(addressMapper.entityToDto(address));
            });
        });
        return res;
    }

}
