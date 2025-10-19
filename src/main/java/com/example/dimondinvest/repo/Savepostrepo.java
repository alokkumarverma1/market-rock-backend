package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Savepost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Savepostrepo extends JpaRepository<Savepost , Long> {
    List<Savepost> findAllByRegisterId(Long id);
}
