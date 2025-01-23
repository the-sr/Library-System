package library.services.impl;

import library.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import library.dto.UserDto;
import library.dto.res.PagewiseRes;
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

    @Override
    public UserDto save(UserDto req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new CustomException("Email already registered", HttpStatus.BAD_REQUEST);
        if (!req.getPassword().equals(req.getConfirmPassword()))
            throw new CustomException("Confirm Password and Password must be same", HttpStatus.BAD_REQUEST);
        User user = userRepo.save(userMapper.dtoToEntity(req));
        if(req.getAddress()!=null){
            req.getAddress().parallelStream().forEach(address->{
                address.setUserId(user.getId());
                addressService.saveAddress(address);
            });
        }
        emailService.sendMail(user.getEmail(), "Account Registration", "Your account has been successfully registered.");
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto findById(long id) {
        User user=userRepo.findById(id).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        return userMapper.entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(Boolean status) {
        List<User> userList = userRepo.findAllByIsActive(status);
        return userList.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public PagewiseRes<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, Boolean status) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> users = userRepo.findAllPagewiseByIsActive(pageable,status);
        List<UserDto> res = users.stream().map(userMapper::entityToDto).collect(Collectors.toList());
        PagewiseRes<UserDto> pagewiseRes = new PagewiseRes<>();
        pagewiseRes.setRes(res);
        pagewiseRes.setTotalPages(users.getTotalPages());
        pagewiseRes.setTotalElements(users.getTotalElements());
        pagewiseRes.setCurrentPage(users.getNumber());
        pagewiseRes.setPageSize(users.getSize());
        pagewiseRes.setLast(users.isLast());
        return pagewiseRes;
    }

    @Override
    public UserDto updateById(UserDto req) {
        User user = userRepo.findById(req.getId()).orElseThrow(() -> new CustomException("User Not Found", HttpStatus.NOT_FOUND));
        user.setFirstName(req.getFirstName());
        user.setMiddleName(req.getMiddleName());
        user.setLastName(req.getLastName());
        if(userRepo.existsByEmail(req.getEmail()))
            throw new CustomException("Email already registered", HttpStatus.BAD_REQUEST);
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUpdatedDate(LocalDate.now());
        return userMapper.entityToDto(userRepo.save(user));
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
