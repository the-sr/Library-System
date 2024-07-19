package com.example.demo.services.impl;

import com.example.demo.models.User;
import com.example.demo.payloads.req.AddressReq;
import com.example.demo.exception.CustomException;
import com.example.demo.models.Address;
import com.example.demo.payloads.res.AddressRes;
import com.example.demo.projection.AddressProjection;
import com.example.demo.repository.AddressRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.AddressService;
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

    @Override
    public AddressRes addAddress(AddressReq addressReq, long userId) {
        Address address = Address.builder()
                .id(addressRepo.findNextId())
                .addressType(addressReq.getAddressType())
                .street(addressReq.getStreet())
                .city(addressReq.getCity())
                .state(addressReq.getState())
                .zip(addressReq.getZip())
                .country(addressReq.getCountry())
                .user(TransferObject.convert(userRepo.findById(userId), User.class))
                .build();
        addressRepo.save(address);
        return findById(address.getId());
    }

    @Override
    public List<AddressRes> getAddressByUserId(long userId) {
        List<AddressProjection> addressList = addressRepo.findByUserId(userId);
        if(addressList.isEmpty()) throw new CustomException("Address Not Found",HttpStatus.NOT_FOUND);
        return TransferObject.convert(addressList,AddressRes.class);
    }

    public AddressRes findById(long id) {
        Optional<AddressProjection> address = addressRepo.findById(id);
        if (address.isEmpty()) throw new CustomException("Address not found", HttpStatus.NOT_FOUND);
        return TransferObject.convert(address.get(),AddressRes.class);
    }
}
