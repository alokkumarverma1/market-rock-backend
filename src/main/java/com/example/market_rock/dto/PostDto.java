package com.example.market_rock.dto;

import com.google.cloud.firestore.FieldValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private String heading;
    private String date;
    private String details;
    private FieldValue createdAt;

}
