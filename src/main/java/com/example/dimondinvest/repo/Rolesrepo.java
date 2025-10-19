package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Rolesrepo extends JpaRepository<Roles,Long> {
    Roles findByRolename(String user);
}
