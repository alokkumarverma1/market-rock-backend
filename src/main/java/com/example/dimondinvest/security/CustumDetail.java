package com.example.dimondinvest.security;

import com.example.dimondinvest.entity.Register;
import com.example.dimondinvest.repo.Registerrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustumDetail implements UserDetailsService {

    @Autowired
     private Registerrepo registerrepo;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {

        Long num = Long.parseLong(number);
        Register register = registerrepo.findByNumber(num);
        if(register == null){
            throw new UsernameNotFoundException("user not found");
        }
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
//                "ROLE_" + register.getRoles().getRolename().toUpperCase()
//        );
//        return new org.springframework.security.core.userdetails.User(
//                register.getName(),    // username
//                register.getPassword(),// encoded password
//                List.of(authority) // authorities
//        );
        return new UserPrincipal(register);

    }
}
