package com.example.dimondinvest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "savepostline")
public class SavePostLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // <- ye important hai large text ke liye
    @Column(columnDefinition = "TEXT") // optional, agar database me LONGTEXT chahiye
    private String news;

    @ManyToOne
    @JoinColumn(name = "savepostdetail_id")
    private Savepostdetail savepostdetail;
}
