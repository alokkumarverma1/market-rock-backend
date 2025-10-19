package com.example.dimondinvest.controller;

import com.example.dimondinvest.dto.Registerdto;
import com.example.dimondinvest.dto.TopStrategy;
import com.example.dimondinvest.entity.Register;
import com.example.dimondinvest.entity.Strategy;
import com.example.dimondinvest.entity.Trade;
import com.example.dimondinvest.repo.Registerrepo;
import com.example.dimondinvest.repo.Strategyrepo;
import com.example.dimondinvest.repo.Traderepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProfitableStrategys {

    @Autowired
    private Strategyrepo strategyrepo;
 @Autowired
 private Traderepo traderepo;
 @Autowired
 private Registerrepo registerrepo;


    @GetMapping("/topstrategy")
    public ResponseEntity<?> topStrategy() {
        List<Strategy> strategies = strategyrepo.findAll();
        if(strategies == null){
            return ResponseEntity.ok("dont have any strategy");
        }
        List<Map<String, Object>> allData = new ArrayList<>();

        for (Strategy strategy : strategies) {
            List<Trade> trades = traderepo.findAllByStrategyId(strategy.getId());

            int profitCount = 0;
            int totalTrades = trades.size();

            for (Trade trade : trades) {
                if ("profit".equalsIgnoreCase(trade.getTradestatus())) {
                    profitCount++;
                }
            }
            int profitPercent = (totalTrades > 0) ? (profitCount * 100 / totalTrades) : 0;
            Register register = registerrepo.findById(strategy.getRegister().getId()).orElse(null);

            // Make one entry per strategy
            Map<String, Object> data = new HashMap<>();
            data.put("strategy", strategy.getSname());
            data.put("user", register != null ? register.getName() : "Unknown");
            data.put("totalTrades", totalTrades);
            data.put("profitTrades", profitCount);
            data.put("profitPercent", profitPercent);
            allData.add(data);
        }
        allData.sort((a, b) -> ((Integer) b.get("profitPercent")).compareTo((Integer) a.get("profitPercent")));
        List<Map<String, Object>> top10 = allData.stream().limit(10).toList();

        return ResponseEntity.ok(top10);
    }



}
