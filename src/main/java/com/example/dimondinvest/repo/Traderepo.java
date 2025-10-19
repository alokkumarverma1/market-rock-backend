package com.example.dimondinvest.repo;

import com.example.dimondinvest.dto.Tradedto;
import com.example.dimondinvest.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Traderepo extends JpaRepository<Trade,Long> {

    List<Trade> findAllByRegisterId(Long id);

    List<Trade> findAllByTrademonth_Id(Long id);

    List<Trade> findAllByTradeday_Id(Long id);

    List<Trade> findAllByStrategyId(Long id);
}
