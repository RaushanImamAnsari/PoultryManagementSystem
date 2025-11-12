package com.mms.MMS.service;


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

    public void  savedUser(UserEntry userEntry){
        userEntryRepo.save(userEntry);
    }

    public List<UserEntry> getAll(){
        return userEntryRepo.findAll();

    }

    public Optional<UserEntry> userById(ObjectId id){
        return userEntryRepo.findById(id);
    }


    public void deleteUserById(ObjectId id){
         userEntryRepo.deleteById(id);
    }
}
