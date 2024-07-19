package com.example.demo.services.impl;

import com.example.demo.payloads.req.UserReq;
import com.example.demo.exception.CustomException;
import com.example.demo.models.User;
import com.example.demo.payloads.res.UserRes;
import com.example.demo.projection.UserProjection;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.AddressService;
import com.example.demo.services.UserService;
import com.example.demo.utils.TransferObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;

    @Override
    public UserRes save(UserReq userReq) {
        if(userRepo.existsByEmail(userReq.getEmail()))
            throw new CustomException("Email already registered. Please try again with new Email.",HttpStatus.CONFLICT);
        if (!userReq.getPassword().equals(userReq.getConfirmPassword()))
            throw new CustomException("Confirm Password and Password must be same", HttpStatus.BAD_REQUEST);
        User user = User.builder()
                .id(userRepo.findNextId())
                .firstName(userReq.getFirstName().trim())
                .middleName(userReq.getMiddleName().trim())
                .lastName(userReq.getLastName().trim())
                .email(userReq.getEmail().trim())
                .password(passwordEncoder.encode(userReq.getPassword()))
                .phone(userReq.getPhone().trim())
                .build();
        userRepo.save(user);
        userReq.getAddress().forEach(address->{
            addressService.addAddress(address, user.getId());
        });
        return findById(user.getId());
    }

    @Override
    public UserRes findById(long id) {
        Optional<UserProjection> user = userRepo.findById(id);
        if (user.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        UserRes res= TransferObject.convert(user.get(), UserRes.class);
        res.setAddress(addressService.getAddressByUserId(user.get().getId()));
        return res;
    }

    @Override
    public UserRes findByEmail(String email) {
        Optional<UserProjection> user = userRepo.findByEmail(email);
        if (user.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        UserRes res= TransferObject.convert(user.get(), UserRes.class);
        res.setAddress(addressService.getAddressByUserId(user.get().getId()));
        return res;
    }
}
