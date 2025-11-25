package com.mms.MMS.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@AllArgsConstructor
@Data    // @Data --> @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
@NoArgsConstructor
@Document(collection = "entries")
public class UserEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String password;
    private LocalDateTime date;

//    public long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPassword(int password) {
//        this.password = password;
//    }
//
//    public int getPassword() {
//        return password;
//    }
}
