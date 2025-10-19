package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Register;
import com.example.dimondinvest.entity.TradeDay;
import com.example.dimondinvest.entity.Trademonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Tradedayrepo extends JpaRepository<TradeDay,Long> {


    Optional<TradeDay> findByRegisterAndDayname(Register register, String day);

    List<TradeDay> findAllByTrademonth_Id(Long id);
}
