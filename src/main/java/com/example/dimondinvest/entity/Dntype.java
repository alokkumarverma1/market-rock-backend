package com.example.dimondinvest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "dntype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dntype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typename;

    @OneToMany(mappedBy = "dntype",cascade = CascadeType.ALL)
    private List<Dnews> dnewsList;
}
