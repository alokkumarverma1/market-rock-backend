package com.example.dimondinvest.entity;

import com.fasterxml.jackson.annotation.JacksonInject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tradeday")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TradeDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dayname;


    @OneToMany(mappedBy = "tradeday",cascade = CascadeType.ALL)
    private List<Trade> trades;
    @ManyToOne
    @JoinColumn(name = "trademonth_id")
    private Trademonth trademonth;

    @ManyToOne
    @JoinColumn(name = "register_id")
    private Register register;
}
