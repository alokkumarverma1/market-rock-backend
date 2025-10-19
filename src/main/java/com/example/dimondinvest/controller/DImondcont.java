package com.example.dimondinvest.controller;

import com.example.dimondinvest.dto.Dnewsdto;
import com.example.dimondinvest.dto.Dnewstypedto;
import com.example.dimondinvest.service.Mainservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class DImondcont {


    @Autowired
    private Mainservice mainservice;

    // save all diamond news
    @PostMapping("/dimondnews")
    public String dimondnews(@RequestBody Dnewsdto dnewsdto, Authentication authentication){
        String data = mainservice.dimondsnews(dnewsdto,authentication);
        return data;
    }

    // dimond trading news
    @GetMapping("/tradingnews")
    public ResponseEntity<?> tradingnews(){
        ResponseEntity dnewsdtos = mainservice.alltradingnews();
        return ResponseEntity.ok(dnewsdtos);
    }

//dimond invest  news
    @GetMapping("/investnews")
    public ResponseEntity<?> investnews(){
        ResponseEntity dnewsdtos = mainservice.allinvestnews();
        return ResponseEntity.ok(dnewsdtos);
    }

  //dimond prime news
    @GetMapping("/primenews")
    public ResponseEntity<?> primenews(){
        ResponseEntity dnewsdtos = mainservice.allprimenews();
        return ResponseEntity.ok(dnewsdtos);
    }

  // save post
    @PostMapping("/savepost")
    public String savepost(@RequestBody Dnewsdto dnewsdto,Authentication authentication){
        String data = mainservice.saveposts(dnewsdto,authentication);
        return data;
    }
//
    //all save posts
    @GetMapping("/mysavepost")
    public ResponseEntity<?> allsavepost(Authentication authentication){
        ResponseEntity dnewsdtos = mainservice.allsavepost(authentication);
        return ResponseEntity.ok(dnewsdtos);
    }

    // unsavepost
    @PostMapping("/unsavepost")
    public ResponseEntity<?> unsavepost(@RequestBody Dnewsdto dnewsdto,Authentication authentication){
       String  data = mainservice.unsavepost(dnewsdto,authentication);
        return ResponseEntity.ok(data);
    }

    // delete post
    @PostMapping("/deletepost")
    public ResponseEntity<?> deletepost(@RequestBody Dnewsdto dnewsdto,Authentication authentication){
        String res = mainservice.deletepost(dnewsdto,authentication);
        return ResponseEntity.ok(res);
    }

}
