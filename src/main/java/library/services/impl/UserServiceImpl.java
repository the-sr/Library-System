package library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import library.config.security.AuthenticationFacade;
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
    private final AuthenticationFacade facade;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Override
    public UserDto save(UserDto req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new CustomException("Email already registered", HttpStatus.BAD_REQUEST);
        if (!req.getPassword().equals(req.getConfirmPassword()))
            throw new CustomException("Confirm Password and Password must be same", HttpStatus.BAD_REQUEST);
        User user = userMapper.dtoToEntity(req);
        user=userRepo.save(user);
        emailService.sendMail(user.getEmail(), "Account Registration",
                "Your account has been successfully registered.");
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto findById(long id) {
        User user=userRepo.findById(id).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        return userMapper.entityToDto(userRepo.findByEmail(email).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<UserDto> getAllUsers(Boolean status) {
        List<User> userList = userRepo.findAllByIsActive(status);
        return userList.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public PagewiseRes<UserDto> getAllUsersPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> users = userRepo.findAllPagewise(pageable);
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
        user.setFirstName(req.getFirstName().trim());
        user.setMiddleName(req.getMiddleName().trim());
        user.setLastName(req.getLastName().trim());
        user.setEmail(req.getEmail().trim());
        user.setPhone(req.getPhone().trim());
        user.setUpdatedDate(LocalDate.now());
        return userMapper.entityToDto(userRepo.save(user));
    }

    @Override
    public String deleteById(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        user.setIsActive(false);
        userRepo.save(user);
        emailService.sendMail(user.getEmail(), "Account Deletion", "Your account will be deleted within a week.");
        return "User Deleted Successfully";
    }


}
