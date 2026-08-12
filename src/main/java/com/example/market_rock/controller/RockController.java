package com.example.market_rock.controller;

import com.example.market_rock.dto.IndexDto;
import com.example.market_rock.dto.PostDto;
import com.example.market_rock.dto.swingStockDto.SwingStockDto;
import com.example.market_rock.service.RockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rock")

public class RockController {

    @Autowired
    private RockService rockService;


    // add rock swing stock
    @PostMapping("/addSwing")
    public ResponseEntity<?> AddSwingStock(@RequestBody SwingStockDto swingStockDto) throws  Exception{
           String res = rockService.addSwingStock(swingStockDto);
           return ResponseEntity.ok().body(res);
       }

    // add index
    @PostMapping("/addIndex")
    public ResponseEntity<?> addIndex(@RequestBody IndexDto indexDto) throws Exception{
    String res = rockService.addIndexPrice(indexDto);
    return  ResponseEntity.ok().body(res);
    }

    // add post
    @PostMapping("/addPost")
    public ResponseEntity<?> addPost (@RequestBody PostDto postDto) throws Exception{
        String res = rockService.addPost(postDto);
        return  ResponseEntity.ok().body(res);
    }




     // end
    }


