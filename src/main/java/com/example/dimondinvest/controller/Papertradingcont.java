package com.example.dimondinvest.controller;

import com.example.dimondinvest.dto.GetStockdto;
import com.example.dimondinvest.service.PaperTradingSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class Papertradingcont {

    @Autowired
    private PaperTradingSer paperTradingSer;

 @PostMapping("/getstock")
 public ResponseEntity<?> getstock(@RequestBody GetStockdto getStockdto){
     String data = paperTradingSer.getstockdetail(getStockdto);
     return ResponseEntity.ok(data);
 }

}
