package library.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import library.dto.AddressDto;
import library.exception.CustomException;
import library.models.Address;
import library.repository.AddressRepo;
import library.repository.UserRepo;
import library.services.AddressService;
import library.services.mappers.AddressMapper;
import library.utils.TransferObject;

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
    public List<AddressDto> getAddressByUserId(long userId) {
        List<Address> addressList = addressRepo.findByUserId(userId);
        if (addressList.isEmpty())
            throw new CustomException("Address Not Found", HttpStatus.NOT_FOUND);
        return TransferObject.convert(addressList, AddressDto.class);
    }

    public AddressDto findById(long id) {
        Optional<Address> address = addressRepo.findById(id);
        if (address.isEmpty())
            throw new CustomException("Address not found", HttpStatus.NOT_FOUND);
        return TransferObject.convert(address.get(), AddressDto.class);
    }
}
