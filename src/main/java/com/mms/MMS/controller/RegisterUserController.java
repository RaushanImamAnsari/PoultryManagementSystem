package com.mms.MMS.controller;

import com.mms.MMS.model.User;
import com.mms.MMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class RegisterUserController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user){
        userService.savedUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
