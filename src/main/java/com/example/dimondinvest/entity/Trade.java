package com.example.dimondinvest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trade")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tradestatus;
    private String date;
    private String day;

    @PrePersist
    public void prePersist() {
        this.date = String.valueOf(LocalDate.now().getDayOfMonth());
    }

    @ManyToOne
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @ManyToOne
    @JoinColumn(name = "trademonth_id")
   private Trademonth trademonth;

  @ManyToOne
    @JoinColumn(name = "register_id")
    private Register register;

    @ManyToOne
    @JoinColumn(name = "tradeday_id")
    private TradeDay tradeday; //

}
