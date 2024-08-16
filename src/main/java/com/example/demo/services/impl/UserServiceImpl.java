package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.User;
import com.example.demo.payloads.req.ResetPassReq;
import com.example.demo.payloads.req.UserReq;
import com.example.demo.payloads.res.AddressRes;
import com.example.demo.payloads.res.PagewiseRes;
import com.example.demo.payloads.res.UserRes;
import com.example.demo.projection.UserProjection;
import com.example.demo.repository.AddressRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.AddressService;
import com.example.demo.services.UserService;
import com.example.demo.utils.EmailService;
import com.example.demo.utils.TransferObject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final AddressService addressService;
    private final AddressRepo addressRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public UserRes save(UserReq userReq) {
        if (!userReq.getPassword().equals(userReq.getConfirmPassword()))
            throw new CustomException("Confirm Password and Password must be same", HttpStatus.BAD_REQUEST);
        User user = User.builder()
                .id(userRepo.findNextId())
                .firstName(userReq.getFirstName())
                .middleName(userReq.getMiddleName())
                .lastName(userReq.getLastName())
                .email(userReq.getEmail())
                .password(passwordEncoder.encode(userReq.getPassword()))
                .role(userReq.getRole())
                .phone(userReq.getPhone())
                .build();
        userRepo.save(user);
        emailService.sendMail(user.getEmail(), "Account Registration", "Your account has been successfully registered.");
        if (userReq.getAddress() != null) {
            userReq.getAddress().forEach(address -> {
                addressService.addAddress(address, user.getId());
            });
        }
        return findById(user.getId());
    }

    @Override
    public UserRes findById(long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        UserRes res = TransferObject.convert(user.get(), UserRes.class);
        res.setAddress(TransferObject.convert(addressRepo.findByUserId(user.get().getId()), AddressRes.class));
        return res;
    }

    @Override
    public UserRes findByEmail(String email) {
        Optional<UserProjection> user = userRepo.findByEmail(email);
        if (user.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        UserRes res = TransferObject.convert(user.get(), UserRes.class);
        res.setAddress(addressService.getAddressByUserId(user.get().getId()));
        return res;
    }

    @Override
    public String resetPassword(ResetPassReq passReq) {
        long userId = 1;
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        if (passReq.getOldPassword().equals(user.get().getPassword()))
            user.get().setPassword(passReq.getNewPassword());
        else throw new CustomException("Old password is not correct", HttpStatus.BAD_REQUEST);
        return "Password changed successfully";
    }

    @Override
    public List<UserRes> findAllActiveUsers() {
        return List.of();
    }

    @Override
    public List<UserRes> findAllInactiveUsers() {
        return List.of();
    }

    @Override
    public UserRes updateById(long id, UserReq userReq) {
        User user = userRepo.findById(id).get();
        user.setFirstName(userReq.getFirstName().trim());
        user.setMiddleName(userReq.getMiddleName().trim());
        user.setLastName(userReq.getLastName().trim());
        user.setEmail(userReq.getEmail().trim());
        user.setPhone(userReq.getPhone().trim());
        userRepo.save(user);
        return findById(user.getId());
    }

    @Override
    public String deleteById(long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        user.get().setActive(false);
        userRepo.save(user.get());
        emailService.sendMail(user.get().getEmail(), "Account Deletion", "Your account will be deleted within a week.");
        return "User Deleted Successfully";
    }

    @Override
    public List<UserRes> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserRes> res = new ArrayList<>();
        users.forEach(user -> {
            UserRes userRes = TransferObject.convert(user, UserRes.class);
            userRes.setAddress(TransferObject.convert(addressRepo.findByUserId(user.getId()), AddressRes.class));
            res.add(userRes);
        });
        return res;
    }

    @Override
    public PagewiseRes<UserRes> getAllUsersPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<UserProjection> users = userRepo.findAllPagewise(pageable);
        List<UserRes> res = new ArrayList<>();
        users.forEach(user -> {
            UserRes userRes = TransferObject.convert(user, UserRes.class);
            userRes.setAddress(TransferObject.convert(addressRepo.findByUserId(user.getId()), AddressRes.class));
            res.add(userRes);
        });
        PagewiseRes<UserRes> pagewiseRes = new PagewiseRes<>();
        pagewiseRes.setRes(res);
        pagewiseRes.setTotalPages(users.getTotalPages());
        pagewiseRes.setTotalElements(users.getTotalElements());
        pagewiseRes.setCurrentPage(users.getNumber());
        pagewiseRes.setPageSize(users.getSize());
        pagewiseRes.setLast(users.isLast());

        return pagewiseRes;
    }

}
