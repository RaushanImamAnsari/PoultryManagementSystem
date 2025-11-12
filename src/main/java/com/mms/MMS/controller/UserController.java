package com.mms.MMS.controller;

import com.mms.MMS.model.User;
import com.mms.MMS.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PostMapping("/add")
    public String createUser(@RequestBody User user){
        userService.savedUser(user);
        return "User Saved!!";
    }

    public User updateUser(ObjectId id, @RequestBody User user){


    }





}