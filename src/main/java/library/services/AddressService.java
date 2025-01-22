package library.services;

import java.util.List;

import library.dto.AddressDto;

public interface AddressService {
    List<AddressDto> getAddressByUserId(long userId);
}
