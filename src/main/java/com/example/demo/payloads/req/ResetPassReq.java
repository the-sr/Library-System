package com.example.demo.payloads.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassReq {

    private String oldPassword;
    private String newPassword;

}
