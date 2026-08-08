package com.example.market_rock.service;

import com.example.market_rock.dto.swingStockDto.SwingStockDto;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class RockService {

    // firebase
    private final Firestore db;
    public RockService(Firestore db) {
        this.db = db;
    }



    // add swing stock
    public String addSwingStock(SwingStockDto swingStockDto){
     try{
         db.collection("swingstocks").add(swingStockDto).get();
         return "add success";
      }catch (Exception e){
       return "add failed";
     }
    }

}
