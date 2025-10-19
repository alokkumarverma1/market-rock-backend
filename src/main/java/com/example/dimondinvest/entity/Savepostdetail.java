package com.example.dimondinvest.entity;

import com.example.dimondinvest.dto.Dnewslinedto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "savepostdetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Savepostdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // <- ye important hai large text ke liye
    @Column(columnDefinition = "TEXT")// optional, agar database me LONGTEXT chahiye
    private String  heading;

    @OneToMany(mappedBy = "savepostdetail",cascade = CascadeType.ALL)
    private List<SavePostLine> savePostLines;

    @ManyToOne
    @JoinColumn(name = "savepost_id")
    private Savepost savepost;

}
