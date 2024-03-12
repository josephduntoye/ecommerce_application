package com.ecommerce.application.user.service;

import com.ecommerce.application.exception.UserAlreadyExist;
import com.ecommerce.application.exception.UserNotFoundException;
import com.ecommerce.application.user.dto.AppUserRequestDto;
import com.ecommerce.application.user.dto.AppUserResponseDto;
import com.ecommerce.application.user.model.AppUser;
import com.ecommerce.application.user.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AppUserServiceImpl implements AppUserService{
    @Autowired
    AppUserRepository appUserRepository;
    //@Autowired
    //BCryptPasswordEncoder passwordEncoder;

    @Override
    public AppUserResponseDto createUser(AppUserRequestDto appUserRequestDto) throws UserAlreadyExist {
        Optional<AppUser> qerryUser = appUserRepository.findByEmail(appUserRequestDto.getEmail());
        if(qerryUser.isPresent()){
            throw new UserAlreadyExist("this user Already exist");
        }
        AppUser newUser = new AppUser();
        newUser.setEmail(appUserRequestDto.getEmail());
        //newUser.setPassword(passwordEncoder.encode(appUserRequestDto.getPassword()));
        newUser.setPassword(appUserRequestDto.getPassword());
        newUser.setAddress(appUserRequestDto.getAddress());
        newUser.setFirstName(appUserRequestDto.getFirstName());
        newUser.setLastName(appUserRequestDto.getLastName());

        appUserRepository.save(newUser);

        return buildResponse(newUser);
    }
    private AppUserResponseDto buildResponse(AppUser appUser){
        return AppUserResponseDto.builder()
                .Email(appUser.getEmail())
                .address(appUser.getAddress())
                .lastName(appUser.getLastName())
                .firstName(appUser.getFirstName())
                .build();
    }

    @Override
    public AppUserResponseDto findUser(String email) throws UserNotFoundException {
        if(email == null | email.isEmpty()){
            throw new IllegalArgumentException("Email is required.");
        }
        Optional<AppUser> formerUser = appUserRepository.findByEmail(email);
        if(formerUser.isEmpty()){
            throw new UserNotFoundException("User does not exist in the database");
        }
        AppUser appUser = formerUser.get();
        AppUserResponseDto appUserResponseDto = new AppUserResponseDto();
        appUserResponseDto.setEmail(appUser.getEmail());
        appUserResponseDto.setAddress(appUser.getAddress());
        appUserResponseDto.setFirstName(appUser.getFirstName());
        appUserResponseDto.setLastName(appUser.getLastName());

        return appUserResponseDto;
    }


}
