package com.ecommerce.application.user.dto;

import lombok.Data;

@Data
public class AppUserRequestDto {
    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private String address;
}
