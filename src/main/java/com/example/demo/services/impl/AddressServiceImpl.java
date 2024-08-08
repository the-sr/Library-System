package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.Address;
import com.example.demo.payloads.req.AddressReq;
import com.example.demo.payloads.res.AddressRes;
import com.example.demo.repository.AddressRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.AddressService;
import com.example.demo.services.mappers.AddressDetailsMapper;
import com.example.demo.utils.TransferObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final AddressDetailsMapper addressDetailsMapper;

    @Override
    public AddressRes addAddress(AddressReq addressReq, long userId) {
        Address address = addressDetailsMapper.toEntity(addressReq);
        address.setId(addressRepo.findNextId());
        address.setUser(userRepo.findById(userId).get());
        addressRepo.save(address);
        return findById(address.getId());
    }

    @Override
    public List<AddressRes> getAddressByUserId(long userId) {
        List<Address> addressList = addressRepo.findByUserId(userId);
        if (addressList.isEmpty()) throw new CustomException("Address Not Found", HttpStatus.NOT_FOUND);
        return TransferObject.convert(addressList, AddressRes.class);
    }

    public AddressRes findById(long id) {
        Optional<Address> address = addressRepo.findById(id);
        if (address.isEmpty()) throw new CustomException("Address not found", HttpStatus.NOT_FOUND);
        return TransferObject.convert(address.get(), AddressRes.class);
    }
}
