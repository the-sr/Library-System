package library.services;

import java.util.List;

import library.dto.AddressDto;

public interface AddressService {
    void addAddress(AddressDto address, long userId);

    List<AddressDto> getAddressByUserId(long userId);
}
