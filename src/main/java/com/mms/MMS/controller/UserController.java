package com.mms.MMS.controller;

import com.mms.MMS.model.User;
import com.mms.MMS.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable ObjectId id, @RequestBody User user){
        User oldUser = userService.userById(id).orElse(null);
        if(oldUser != null){
            oldUser.setUserName(user.getUserName());
            oldUser.setUserPassword(user.getUserPassword());
            userService.savedUser(oldUser);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> deleteUser(ObjectId id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}