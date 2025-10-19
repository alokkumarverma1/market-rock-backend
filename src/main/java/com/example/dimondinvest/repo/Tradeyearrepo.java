package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Register;
import com.example.dimondinvest.entity.TradeYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Tradeyearrepo extends JpaRepository<TradeYear,Long> {

    List<TradeYear> findAllByRegisterId(Long id);

    Optional<TradeYear> findByRegisterAndYear(Register register, String year);
}
