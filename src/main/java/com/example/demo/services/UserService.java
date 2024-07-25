package com.example.demo.services;

import com.example.demo.payloads.req.ResetPassReq;
import com.example.demo.payloads.req.UserReq;
import com.example.demo.payloads.res.UserRes;

import java.util.List;

public interface UserService {
    UserRes save(UserReq userReq);
    UserRes findById(long id);
    List<UserRes> getAllUsers();
    String deleteById(long id);
    UserRes updateById(long id,UserReq userReq);
    UserRes findByEmail(String email);
    String resetPassword(ResetPassReq passReq);
}
