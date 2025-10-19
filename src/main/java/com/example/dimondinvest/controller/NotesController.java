package com.example.dimondinvest.controller;

import com.example.dimondinvest.dto.AddStrategydto;
import com.example.dimondinvest.dto.MonthTradedto;
import com.example.dimondinvest.dto.Tradedto;
import com.example.dimondinvest.dto.YearTradedot;
import com.example.dimondinvest.entity.Strategy;
import com.example.dimondinvest.repo.Strategyrepo;
import com.example.dimondinvest.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class NotesController {

    @Autowired
    private NotesService notesService;

    // add strategy
    @PostMapping("/addstrategy")
    public ResponseEntity<?> addstrategy(@RequestBody AddStrategydto addStrategydto, Authentication authentication){
       ResponseEntity res = notesService.addstrategy(addStrategydto,authentication);
        return ResponseEntity.ok(res);
    }

    // mystrategy
    @GetMapping("/mystrategy")
    public ResponseEntity<?> mystrategy(Authentication authentication){
        ResponseEntity res = notesService.mystrategy(authentication);
        return ResponseEntity.ok(res);
    }

    // delete strategy

    @PostMapping("/deletestrategy")
    public ResponseEntity<?> deletestrategy(@RequestBody AddStrategydto addStrategydto ,Authentication authentication) {
        boolean deleted = notesService.deletestrategys(addStrategydto,authentication);
        if (deleted) {
            return ResponseEntity.ok(" Strategy deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Strategy not found!");
        }
    }

    // loadstrategy
    @GetMapping("/loadstrategy")
    public ResponseEntity<?> loaddata(Authentication authentication){
        ResponseEntity responseEntity = notesService.loadstrategys(authentication);
        return ResponseEntity.ok(responseEntity);
    }

    // add trade
    @PostMapping("/addtrade")
    public ResponseEntity<?> addtrade(@RequestBody Tradedto tradedto,Authentication authentication){
        ResponseEntity res = notesService.addtrades(tradedto,authentication);
        return ResponseEntity.ok(res);
    }

    // my trade anlaysis head home
    @GetMapping("/tradehomedata")
    public ResponseEntity<?> tradehomedata(Authentication authentication){
        ResponseEntity data = notesService.tradehomedata(authentication);
        return ResponseEntity.ok(data);
    }

    //all trade home
    @GetMapping("/alltrade")
    public ResponseEntity<List<YearTradedot>> allyeartrade(Authentication authentication){
        List<YearTradedot> yearTradedots = notesService.allyeartrades(authentication);
        return ResponseEntity.ok(yearTradedots);
    }

    // my year trade
    @GetMapping("/allmyyeartrades")
    public ResponseEntity<YearTradedot> myyeartrades(Authentication authentication){
        YearTradedot yearTradedot = notesService.myyeartrade(authentication);
        return ResponseEntity.ok(yearTradedot);
    }

    // this month
    @GetMapping("/thismonth")
    public ResponseEntity<?> monthtrade(Authentication authentication) {
       ResponseEntity monthTradedto = notesService.mymonthtrade(authentication);
        return ResponseEntity.ok(monthTradedto);
    }
}
