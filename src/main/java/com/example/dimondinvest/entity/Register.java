package com.example.dimondinvest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "register")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long number;
    private String email;
    private String city;
    private String password;


    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Roles roles;

    @OneToMany(mappedBy = "register", cascade = CascadeType.ALL)
    private List<Savepost> saveposts;

    @OneToMany(mappedBy = "register",cascade = CascadeType.ALL)
    private List<Strategy> strategies;

    @OneToMany(mappedBy = "register",cascade = CascadeType.ALL)
    private List<Trade> trades;

    @OneToMany(mappedBy = "register",cascade = CascadeType.ALL)
    private List<TradeYear> tradeYears;

    @OneToMany(mappedBy = "register",cascade = CascadeType.ALL)
    private List<Trademonth> trademonths;

    @OneToMany(mappedBy = "register",cascade = CascadeType.ALL)
    private List<TradeDay> tradeDays;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Register other = (Register) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
