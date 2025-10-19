package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.OtpVerify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpVerifyrepo extends JpaRepository<OtpVerify,Long> {
    OtpVerify findByEmail(String email);
}
