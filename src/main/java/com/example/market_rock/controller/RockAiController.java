package com.example.market_rock.controller;

import com.example.market_rock.service.RockAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rockAi")
public class RockAiController {
    @Autowired
    private RockAiService rockAiService;

    @PostMapping("/message")
    public ResponseEntity<?> handleMessage(@RequestBody String message){
       ResponseEntity<?>  res = rockAiService.askAnswer(message);
        return ResponseEntity.ok().body(res);
    }

}
