package com.ecommerce.application.user.controller;

import com.ecommerce.application.exception.UserAlreadyExist;
import com.ecommerce.application.exception.UserNotFoundException;
import com.ecommerce.application.user.dto.AppUserRequestDto;
import com.ecommerce.application.user.dto.AppUserResponseDto;
import com.ecommerce.application.user.service.AppUserService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appuser")
public class AppUserController {
    @Autowired
    AppUserService appUserService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody AppUserRequestDto appUserRequestDto) throws UserAlreadyExist{
        try{
            AppUserResponseDto createdUser = appUserService.createUser(appUserRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(createdUser);
        } catch (UserAlreadyExist userAlreadyExist){
            return ResponseEntity.badRequest().body(userAlreadyExist.getMessage());
        }
    }

    @GetMapping("/findUser/{email}")
    public ResponseEntity<?> findUser(@PathVariable String email) throws UserNotFoundException{
        try{
            AppUserResponseDto foundUser = appUserService.findUser(email);
            return ResponseEntity.status(HttpStatus.OK).body(foundUser);
        } catch (UserNotFoundException userNotFoundException){
            return ResponseEntity.badRequest().body(userNotFoundException.getMessage());
        }
    }
}
