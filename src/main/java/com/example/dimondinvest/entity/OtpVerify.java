package com.example.dimondinvest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otpverify")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private int otp;
    private LocalDateTime expiretime;

}
