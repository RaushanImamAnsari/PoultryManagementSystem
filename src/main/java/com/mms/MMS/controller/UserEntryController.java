package com.mms.MMS.controller;

import com.mms.MMS.model.User;
import com.mms.MMS.model.UserEntry;
import com.mms.MMS.service.UserEntryService;
import com.mms.MMS.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/userEntry")
public class UserEntryController {


    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    UserService userService;


    @GetMapping("/all-list")
    public ResponseEntity<?> getAllUserEntry(){
        List<UserEntry> allUserEntry = userEntryService.getAll();
        return new ResponseEntity<>(allUserEntry, HttpStatus.OK);
    }




    @GetMapping("/list")
    public ResponseEntity<?> getAllUserEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
         List<UserEntry> alluser = user.getUserEntryList();
         if(alluser != null && !alluser.isEmpty()){
             return new ResponseEntity<>(alluser, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
    @PostMapping("/createUserEntry")
    public ResponseEntity<UserEntry> createEntry(@RequestBody UserEntry userEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try{
            userEntry.setDate(LocalDateTime.now());
            userEntryService.savedUser(userEntry, userName);
            return new ResponseEntity<>(userEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserEntry> getUserById(@PathVariable ObjectId id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<UserEntry> collect = user.getUserEntryList().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<UserEntry> userEntry = userEntryService.userById(id);
            if(userEntry.isPresent()){
                return new ResponseEntity<>(userEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatingData(@PathVariable ObjectId id, @RequestBody UserEntry update){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<UserEntry> collect = user.getUserEntryList().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty()){
           Optional<UserEntry> userEntry = userEntryService.userById(id);
           if(userEntry.isPresent()){
               UserEntry oldUserEntry = userEntry.get();
               oldUserEntry.setTitle(update.getTitle() != null && !update.getTitle().equals("") ? update.getTitle() : oldUserEntry.getTitle());
               oldUserEntry.setPassword(update.getPassword() != null && !update.getPassword().equals("") ? update.getPassword() : oldUserEntry.getPassword());

               userEntryService.savedUser(oldUserEntry);
               return new ResponseEntity<>(oldUserEntry,HttpStatus.OK);
           }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        boolean removed = userEntryService.deleteUserById(id, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}