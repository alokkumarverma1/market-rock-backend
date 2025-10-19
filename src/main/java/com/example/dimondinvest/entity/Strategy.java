package com.example.dimondinvest.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "strategy")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sname;

    @OneToMany(mappedBy = "strategy",cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<StrategyLInes> strategyLInes;

    @ManyToOne
    @JoinColumn(name = "register_id")
    private Register register; // user who created this strategy

    @OneToMany(mappedBy = "strategy",cascade = CascadeType.ALL)
    private List<Trade> trades;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Strategy other = (Strategy) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
