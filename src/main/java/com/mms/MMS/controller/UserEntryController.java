package com.mms.MMS.controller;

import com.mms.MMS.model.UserEntry;
import com.mms.MMS.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userEntry")
public class UserEntryController {


    @Autowired
    private UserEntryService userEntryService;


    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers(){
         List<UserEntry> alluser = userEntryService.getAll();
         if(alluser != null && !alluser.isEmpty()){
             return new ResponseEntity<>(alluser, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create")
    public ResponseEntity<UserEntry> createEntry(@RequestBody UserEntry userEntry){
        try{
            userEntry.setDate(LocalDateTime.now());
            userEntryService.savedUser(userEntry);
            return new ResponseEntity<>(userEntry, HttpStatus.CREATED);
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
    public ResponseEntity<UserEntry> getUserById(@PathVariable ObjectId id){
        Optional<UserEntry> user = userEntryService.userById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatingData(@PathVariable ObjectId id, @RequestBody UserEntry update){
        UserEntry oldUserEntry = userEntryService.userById(id).orElse(null);

        if(oldUserEntry != null){
            oldUserEntry.setTitle(update.getTitle() != null && !update.getTitle().equals("") ? update.getTitle() : oldUserEntry.getTitle());
            oldUserEntry.setPassword(update.getPassword() != null && !update.getPassword().equals("") ? update.getPassword() : oldUserEntry.getPassword());

            userEntryService.savedUser(oldUserEntry);
            return new ResponseEntity<>(oldUserEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        userEntryService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}