package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.Address;
import com.example.demo.dto.AddressDto;
import com.example.demo.dto.res.AddressRes;
import com.example.demo.repository.AddressRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.AddressService;
import com.example.demo.services.mappers.AddressMapper;
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
    private final AddressMapper addressMapper;

    @Override
    public void addAddress(AddressDto addressDto, long userId) {
        Address address = addressMapper.dtoToEntity(addressDto);
        address.setId(addressRepo.findNextId());
        address.setUser(userRepo.findById(userId).get());
        addressRepo.save(address);
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
