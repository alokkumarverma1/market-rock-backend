package com.example.dimondinvest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dnews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dnews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String compnayname;
    private String title;
    private LocalDate date;
    private LocalTime time;

    @OneToMany(mappedBy = "dnews",cascade = CascadeType.ALL)
    private List<Dnewsdetail> dnewsdetails;

    @ManyToOne
    @JoinColumn(name = "dntype_id")
    private Dntype dntype;
}
