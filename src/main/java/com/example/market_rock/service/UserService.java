package com.example.market_rock.service;

import com.example.market_rock.dto.UserDto;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Firestore db;

    private UserService(Firestore db){
        this.db= db;
    }

    // user register
    public String registerUser(UserDto userDto) throws Exception{
        if(userDto.getEmail().equalsIgnoreCase("rockteamsupport@gmail.com")){
            userDto.setRole("ADMIN");
        }else {
            userDto.setRole("USER");
        }
        db.collection("user").document(userDto.getId()).set(userDto).get();
        return  "register success";
    };


    // get user profile data


    // edit user profile data


    // user logout
}
