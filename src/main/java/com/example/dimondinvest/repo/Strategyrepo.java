package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Strategyrepo extends JpaRepository<Strategy,Long> {
    List<Strategy> findAllByRegisterId(Long id);
}
