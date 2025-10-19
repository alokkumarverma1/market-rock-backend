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
@Table(name = "savepost")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Savepost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postid;
    private String compnayname;
    private String title;
    private LocalDate date;
    private LocalTime time;

    @OneToMany(mappedBy = "savepost",cascade = CascadeType.ALL)
    private List<Savepostdetail> savepostdetails;

    @ManyToOne
    @JoinColumn(name = "register_id")
    private Register register;
}
