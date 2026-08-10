package com.example.market_rock.service;

import com.example.market_rock.dto.IndexDto;
import com.example.market_rock.dto.PostDto;
import com.example.market_rock.dto.swingStockDto.SwingStockDto;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Service;

@Service
public class RockService {

    // firebase
    private final Firestore db;
    public RockService(Firestore db) {
        this.db = db;
    }



    // add swing stock
    public String addSwingStock(SwingStockDto swingStockDto) throws  Exception{
            swingStockDto.setCreatedAt(FieldValue.serverTimestamp());
         db.collection("swingstocks").add(swingStockDto).get();
         return "add success";
    }

    // add indexPrice
    public String addIndexPrice(IndexDto indexDto) throws Exception {
        indexDto.setCreatedAt(FieldValue.serverTimestamp());
        db.collection("indexprice").add(indexDto).get();
        return "add success";
    }

    // add post
    public  String addPost(PostDto postDto) throws Exception{
            postDto.setCreatedAt(FieldValue.serverTimestamp());
            db.collection("post").add(postDto).get();
            return "add success";
    };




    // end

}
