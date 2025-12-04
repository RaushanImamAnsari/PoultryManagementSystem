package com.mms.MMS.service;


import com.mms.MMS.model.User;
import com.mms.MMS.model.UserEntry;
import com.mms.MMS.repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {


//    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserEntryRepo userEntryRepo;

    @Autowired
    UserService userService;


    @Transactional  // it means this method works as a single unit/statement if any one line throw error it will consider that
    // this method is not working irrespective of some statement/line is correct or working.
    public void  savedUser(UserEntry userEntry, String userName){
        try{
            User user = userService.findByUserName(userName);
            UserEntry saved = userEntryRepo.save(userEntry);
            user.getUserEntryList().add(saved);
            userService.savedUser(user);
        }catch (Exception e){
            System.out.print(e);
            throw new RuntimeException("User didn't save, something went wrong!!!", e);
        }
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


    @Transactional
    public boolean deleteUserById(ObjectId id, String userName){

        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getUserEntryList().removeIf(x -> x.getId().equals(id));

            if(removed){
                userService.savedUser(user);
                userEntryRepo.deleteById(id);
            }
        }
        catch (Exception e){
            System.out.print(e);
            throw new RuntimeException("An error occure while deleting user");
        }
        return removed;
    }
}
