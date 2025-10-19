package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Registerrepo extends JpaRepository<Register ,Long> {

    Register findByNumber(Long number);

    Register findByEmail(String email);
}
