package com.example.demo.services;

import com.example.demo.dto.AddressDto;
import com.example.demo.dto.res.AddressRes;

import java.util.List;

public interface AddressService {
    void addAddress(AddressDto address, long userId);

    List<AddressRes> getAddressByUserId(long userId);
}
