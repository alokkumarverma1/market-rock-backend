package com.example.dimondinvest.dto;


import com.example.dimondinvest.entity.Dntype;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dnewsdto {
    private Long id;
    private Long postid; // original post id
    private String compnayname;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private List<Dnewsdetaildto> dnewsdetaildtos;
    private String typename;

}
