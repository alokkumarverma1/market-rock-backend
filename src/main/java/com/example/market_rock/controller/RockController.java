package com.example.market_rock.controller;

import com.example.market_rock.dto.swingStockDto.SwingStockDto;
import com.example.market_rock.service.RockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rock")

public class RockController {

    @Autowired
    private RockService rockService;

    // add rock swing stock
    @PostMapping("/addSwing")
    public void AddSwingStock(@RequestBody SwingStockDto swingStockDto){
           String res = rockService.addSwingStock(swingStockDto);
        System.out.println(res);
       }


     // end
    }


