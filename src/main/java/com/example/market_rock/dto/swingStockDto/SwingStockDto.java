package com.example.market_rock.dto.swingStockDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwingStockDto {

    private String stockName;
    private String date;
    //rok anlaysis data
    private String currentPrice;
    private String stopLoss;
    private String minTarget;
    private String maxTarget;
    // return data
    private String yearlyReturn;
    private String halfReturn;
    private String monthReturn;
    private String dayReturn;
    // key fector data
    private String peRatio;
    private String marketCap;
    private String bookValue;
    private String roe;
    // holding statement
    private String promoter;
    private String fii;
    private String retail;
    private String other;
    // yearly profit
    private SwingStockReturnDto swingStockReturnDto;

}
