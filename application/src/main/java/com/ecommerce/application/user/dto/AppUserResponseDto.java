package com.ecommerce.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponseDto {

    private String firstName;
    private String lastName;

    private String Email;
    //do not return the password
    private String address;

}
