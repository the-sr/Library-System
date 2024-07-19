package com.example.demo.payloads.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRes {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
