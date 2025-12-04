package com.mms.MMS.service;


import com.mms.MMS.model.User;
import com.mms.MMS.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;


    public void saveNewUser(User user){
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepo.save(user);
    }

    public void savedUser(User user){    // for saving the UserEntries
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

    public void deleteByUserName(String userName){
        userRepo.deleteByUserName(userName);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
}
