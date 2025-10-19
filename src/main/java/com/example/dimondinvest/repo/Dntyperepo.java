package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Dntype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Dntyperepo extends JpaRepository<Dntype,Long> {
    Dntype findByTypename(String typename);
}
