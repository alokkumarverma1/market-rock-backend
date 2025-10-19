package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.StrategyLInes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;

public interface StrategyLinerepo extends JpaRepository<StrategyLInes,Long> {
}
