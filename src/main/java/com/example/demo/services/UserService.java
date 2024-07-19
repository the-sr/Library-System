package com.example.demo.services;

import com.example.demo.payloads.req.UserReq;
import com.example.demo.payloads.res.UserRes;

public interface UserService {
    UserRes save(UserReq userReq);
    UserRes findById(long id);
    UserRes findByEmail(String email);
}
