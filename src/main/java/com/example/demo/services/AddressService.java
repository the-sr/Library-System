package com.example.demo.services;

import com.example.demo.dto.AddressDto;

import java.util.List;

public interface AddressService {
    void addAddress(AddressDto address, long userId);

    List<AddressDto> getAddressByUserId(long userId);
}
