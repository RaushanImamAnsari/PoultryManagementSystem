package com.mms.MMS.service;


import com.mms.MMS.model.User;
import com.mms.MMS.model.UserEntry;
import com.mms.MMS.repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {


    @Autowired
    UserEntryRepo userEntryRepo;

    @Autowired
    UserService userService;

    public void  savedUser(UserEntry userEntry, String userName){
        User user = userService.findByUserName(userName);
        UserEntry saved = userEntryRepo.save(userEntry);
        user.getUserEntryList().add(saved);
        userService.savedUser(user);
    }

    public void  savedUser(UserEntry userEntry){
        userEntryRepo.save(userEntry);
    }

    public List<UserEntry> getAll(){
        return userEntryRepo.findAll();

    }

    public Optional<UserEntry> userById(ObjectId id){
        return userEntryRepo.findById(id);
    }


    public void deleteUserById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getUserEntryList().removeIf(x -> x.getId().equals(id));
        userService.savedUser(user);
        userEntryRepo.deleteById(id);
    }
}
