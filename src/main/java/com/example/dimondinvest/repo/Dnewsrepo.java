package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Dnews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Dnewsrepo extends JpaRepository<Dnews,Long> {
    List<Dnews> findAllByDntypeId(Long id);
}
