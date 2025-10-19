package com.example.dimondinvest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dnewsdetail")
public class Dnewsdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // <- ye important hai large text ke liye
    @Column(columnDefinition = "TEXT") // optional, agar database me LONGTEXT chahiye
    private String heading;

    @OneToMany(mappedBy = "dnewsdetail",cascade = CascadeType.ALL)
    private List<Dnewslines> dnewslines;

    @ManyToOne
    @JoinColumn(name = "dnews_id")
    private Dnews dnews;


}
