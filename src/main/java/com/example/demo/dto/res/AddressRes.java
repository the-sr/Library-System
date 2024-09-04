package com.example.demo.dto.res;

import com.example.demo.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRes {
    private AddressType addressType;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
