package com.example.demo.payloads.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRes {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private List<AddressRes> address;
}