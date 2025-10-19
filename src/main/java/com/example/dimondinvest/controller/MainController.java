package com.example.dimondinvest.controller;

import com.example.dimondinvest.JwtService.JwtService;
import com.example.dimondinvest.dto.Emailverifydto;
import com.example.dimondinvest.dto.Objectres;
import com.example.dimondinvest.dto.Registerdto;
import com.example.dimondinvest.entity.OtpVerify;
import com.example.dimondinvest.entity.Register;
import com.example.dimondinvest.repo.OtpVerifyrepo;
import com.example.dimondinvest.repo.Registerrepo;
import com.example.dimondinvest.repo.Rolesrepo;
import com.example.dimondinvest.service.EmailService;
import com.example.dimondinvest.service.Mainservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {

    @Autowired
    private Registerrepo registerrepo;
    @Autowired
    private Rolesrepo rolesrepo;
    @Autowired
    private Objectres objectres;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authmanager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private Mainservice mainservice;
    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpVerifyrepo otpVerifyrepo;
    @Value("${spring.mail.username}")
    private String from;

    @PostMapping("/email")
    public ResponseEntity<?> userEmail(@RequestBody Emailverifydto emailverifydto){
        // 1️Check if email already registered
        Register register = registerrepo.findByEmail(emailverifydto.getEmail());
        if(register != null){
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // 2️ Generate OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);

        String from = "marketrockofficial@gmail.com";
        String subject = "Email Verification";
        // 4 Save OTP in DB
        OtpVerify otpVerify = new OtpVerify();
        otpVerify.setEmail(emailverifydto.getEmail());
        otpVerify.setOtp(otp);
        otpVerify.setExpiretime(LocalDateTime.now().plusMinutes(3));
        otpVerifyrepo.save(otpVerify);

        try {
            emailService.sendmail(emailverifydto.getEmail(), subject, "Your OTP is: " + otp, from);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send OTP");
        }

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify/email")
    public ResponseEntity<?> checkOtp(@RequestBody Emailverifydto emailverifydto) {

        OtpVerify otpVerify = otpVerifyrepo.findByEmail(emailverifydto.getEmail());
        if (otpVerify == null) {
            return ResponseEntity.badRequest().body("No OTP found. Please request again.");
        }

        // Expiry check
        if (otpVerify.getExpiretime().isBefore(LocalDateTime.now())) {
            otpVerifyrepo.delete(otpVerify); // expired OTP delete
            return ResponseEntity.badRequest().body("OTP expired. Please try again.");
        }
        if (otpVerify.getOtp() == emailverifydto.getOtp()) {
            otpVerifyrepo.delete(otpVerify); // verified OTP delete
            return ResponseEntity.ok("verified");
        }

        return ResponseEntity.badRequest().body("Invalid OTP. Please try again.");
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Registerdto registerdto) {
        String result = mainservice.userregister(registerdto);
        if(result.equals("Number already registered, try again")){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok().body(result);
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> userlogin(@RequestBody Registerdto registerdto) {
        ResponseEntity<Map<String, Object>> data = mainservice.userlogin(registerdto);
        return data;
    }

    // update password
    @PostMapping("/forgatep")
    public ResponseEntity<?> updatepassword(@RequestBody Registerdto registerdto){
       Register register = registerrepo.findByNumber(registerdto.getNumber());
       if(register == null){
           return ResponseEntity.badRequest().body("NUmber Not Registerd");
       }
       register.setPassword(passwordEncoder.encode(registerdto.getPassword()));
       registerrepo.save(register);
   return ResponseEntity.ok().body("Password update sucess");
    }


    @GetMapping("/profile")
    public ResponseEntity<Registerdto> profile(Authentication authentication) {
        System.out.println(authentication.getName());
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }
        Long dats = Long.parseLong(authentication.getName());
        Register register = registerrepo.findByNumber(dats);
        System.out.println(register.getName());

        Registerdto registerdto = new Registerdto();
        registerdto.setName(register.getName());
        registerdto.setNumber(register.getNumber());
        registerdto.setEmail(register.getEmail());
        registerdto.setCity(register.getCity());
        System.out.println(register.getName());
        System.out.println(registerdto);
        return ResponseEntity.ok(registerdto);
    }

    // profile update
    @PostMapping("/profile/update")
    public ResponseEntity<?> updateprofile(@RequestBody Registerdto registerdto,Authentication authentication){
        ResponseEntity responseEntity = mainservice.updateprofile(registerdto,authentication);
        return ResponseEntity.ok(responseEntity);
    }

    @GetMapping("/profile/delete")
    public ResponseEntity<?> deleteprofile(Authentication authentication){
        ResponseEntity res = mainservice.deleteprofile(authentication);
        return res;
    }



}
