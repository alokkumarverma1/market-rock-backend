package com.example.dimondinvest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dnewslines")
public class Dnewslines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // <- ye important hai large text ke liye
    @Column(columnDefinition = "TEXT")
    private String news;


    @ManyToOne
    @JoinColumn(name = "dnewsdetail_id")
    private Dnewsdetail dnewsdetail;

}
