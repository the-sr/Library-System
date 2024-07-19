package com.example.demo.services;

import com.example.demo.payloads.req.AddressReq;
import com.example.demo.payloads.res.AddressRes;

import java.util.List;

public interface AddressService {
    AddressRes addAddress(AddressReq address, long userId);
    List<AddressRes> getAddressByUserId(long userId);
}
