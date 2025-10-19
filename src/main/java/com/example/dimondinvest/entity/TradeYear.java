package com.example.dimondinvest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tradeyear")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TradeYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;

    @OneToMany(mappedBy = "tradeYear", cascade = CascadeType.ALL)
    private List<Trademonth> trademonths;

    @ManyToOne
    @JoinColumn(name = "register_id")
    private Register register;

}
