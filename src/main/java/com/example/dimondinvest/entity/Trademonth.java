package com.example.dimondinvest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "trademonth")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trademonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String month;

    @OneToMany(mappedBy = "trademonth",cascade = CascadeType.ALL)
    private List<Trade> trades;

    @OneToMany(mappedBy = "trademonth",cascade = CascadeType.ALL)
    private List<TradeDay> tradeDays;

    @ManyToOne
    @JoinColumn(name = "tradeyear_id")
    private TradeYear tradeYear;

    @ManyToOne
    @JoinColumn(name = "register_id")
    private Register register;

}
