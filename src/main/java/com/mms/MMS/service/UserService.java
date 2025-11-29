package com.mms.MMS.service;


import com.mms.MMS.model.User;
import com.mms.MMS.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    UserRepo userRepo;

    public void savedUser(User user){
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();

    }

    public Optional<User> userById(ObjectId id){
        return userRepo.findById(id);
    }


    public void deleteUserById(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
}
