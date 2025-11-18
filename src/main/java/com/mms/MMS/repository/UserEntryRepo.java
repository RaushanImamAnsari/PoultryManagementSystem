package com.mms.MMS.repository;

import com.mms.MMS.model.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepo extends MongoRepository<UserEntry, ObjectId> {

}
