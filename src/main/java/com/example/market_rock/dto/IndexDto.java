package com.example.market_rock.dto;

import com.google.cloud.firestore.FieldValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexDto {
    private String indexPrice;
    private String indexName;
    private String entryPrice;
    private String minTarget;
    private String maxTarget;
    private String stopLoss;
    private String direction;
    private FieldValue createdAt;

}
