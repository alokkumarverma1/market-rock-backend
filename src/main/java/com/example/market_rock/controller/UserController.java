package com.example.market_rock.controller;

import com.example.market_rock.dto.UserDto;
import com.example.market_rock.security.CustomUserDetails;
import com.example.market_rock.security.CustomUserDetailsService;
import com.example.market_rock.security.JwtService;
import com.example.market_rock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtService jwtService;


    // login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String uid)throws Exception{
        UserDetails user = customUserDetailsService.loadUserByUsername(uid);
        String res = jwtService.generateToken((CustomUserDetails) user);
        return ResponseEntity.ok().body(res);
    }

    // user register
    @PostMapping("/register")
    public ResponseEntity<HashMap<String,String>> register(@RequestBody UserDto userDto) throws  Exception{
        String data = userService.registerUser(userDto);
        UserDetails user = customUserDetailsService.loadUserByUsername(userDto.getId());
        String token = jwtService.generateToken((CustomUserDetails) user);
        HashMap<String,String> res  = new HashMap<>();
        res.put("token" , token);
        res.put("status" , "success");
        return ResponseEntity.ok().body(res);
    }



    // get user profile data


    // edit user profile data


    // user logout

}
