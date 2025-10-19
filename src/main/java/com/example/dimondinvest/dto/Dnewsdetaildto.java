package com.example.dimondinvest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dnewsdetaildto {
    private String  heading;
    private List<Dnewslinedto> dnewslinedto;
}
