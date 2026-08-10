package com.example.market_rock.controller;

import com.example.market_rock.dto.UserDto;
import com.example.market_rock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // user register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws  Exception{
        String res = userService.registerUser(userDto);
        return ResponseEntity.ok().body(res);
    }



    // get user profile data


    // edit user profile data


    // user logout

}
