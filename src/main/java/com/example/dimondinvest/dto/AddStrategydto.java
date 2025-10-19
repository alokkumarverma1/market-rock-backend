package com.example.dimondinvest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStrategydto {
    private Long id;
    private String sname;
    private List<String> mystrategy;
}
