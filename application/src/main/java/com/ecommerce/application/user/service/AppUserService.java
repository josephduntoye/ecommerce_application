package com.ecommerce.application.user.service;

import com.ecommerce.application.exception.UserAlreadyExist;
import com.ecommerce.application.exception.UserNotFoundException;
import com.ecommerce.application.user.dto.AppUserRequestDto;
import com.ecommerce.application.user.dto.AppUserResponseDto;

public interface AppUserService {
    AppUserResponseDto createUser(AppUserRequestDto appUserRequestDto) throws UserAlreadyExist;

    AppUserResponseDto findUser(String email) throws UserNotFoundException;
}
