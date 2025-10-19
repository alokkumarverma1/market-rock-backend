package com.example.dimondinvest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthTradedto {
    private String month;
    private int year;
    private List<Tradedto> tradedtoList;
    private List<DayTradeDto> dayTradeDtos;
}
