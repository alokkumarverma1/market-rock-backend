package com.example.market_rock.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RockAiService {

    private final Client client;

    public RockAiService(@Value("${gemini.api.key}") String apiKey) {

        client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

//    getAnswer
    public ResponseEntity<?> askAnswer(String message){
        GenerateContentResponse generateContentResponse = client.models.generateContent( "gemini-3.6-flash",message , null);
        return ResponseEntity.ok().body(generateContentResponse.text());
    }

}
