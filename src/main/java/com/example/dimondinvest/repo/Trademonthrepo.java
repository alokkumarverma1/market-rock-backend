package com.example.dimondinvest.repo;

import com.example.dimondinvest.entity.Register;
import com.example.dimondinvest.entity.Trademonth;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface Trademonthrepo extends JpaRepository<Trademonth,Long> {
    Trademonth findByMonth(String month);

    List<Trademonth> findAllByRegisterId(Long id);

    List<Trademonth> findAllByTradeYear_Id(Long id);

    Optional<Trademonth> findByRegisterAndMonth(Register register, String month);
}
