package com.example.dimondinvest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendVideodto {
    private Long id;
    private String title;
    private String date;
    private String videoUrl;
    private String thumbnail;
}
