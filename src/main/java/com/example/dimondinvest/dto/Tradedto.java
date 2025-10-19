package com.example.dimondinvest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tradedto {
    private String sname;
    private String tradestatus;
        private String date;
    private String day;
    private String month;
    private String year;


}
