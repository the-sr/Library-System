package com.example.demo.payloads.req;

import com.example.demo.enums.AddressType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AddressReq {

    @NotNull(message = "Address Type is required")
    private AddressType addressType;
    @NotNull(message = "Street is required.")
    private String street;
    @NotNull(message = "City is required.")
    private String city;
    private String state;
    @NotNull(message = "Zip Code is required.")
    private String zip;
    @NotNull(message = "Country is required.")
    private String country;
    private boolean isPerTemp;
}
