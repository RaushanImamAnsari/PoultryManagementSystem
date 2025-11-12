package com.mms.MMS.controller;

import com.mms.MMS.model.User;
import com.mms.MMS.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers(){
         List<User> alluser = userService.getAll();
         if(alluser != null && !alluser.isEmpty()){
             return new ResponseEntity<>(alluser, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create")
    public ResponseEntity<User> createEntry(@RequestBody User user){
        try{
            user.setDate(LocalDateTime.now());
            userService.savedUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/id/{id}")
//    public User getUserById(@PathVariable ObjectId id){
//        return userService.userById(id).orElse(null);
//    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId id){
        Optional<User> user = userService.userById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatingData(@PathVariable ObjectId id, @RequestBody User update){
        User oldUser = userService.userById(id).orElse(null);

        if(oldUser != null){
            oldUser.setName(update.getName() != null && !update.getName().equals("") ? update.getName() : oldUser.getName());
            oldUser.setPassword(update.getPassword() != null && !update.getPassword().equals("") ? update.getPassword() : oldUser.getPassword());

            userService.savedUser(oldUser);
            return new ResponseEntity<>(oldUser,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}