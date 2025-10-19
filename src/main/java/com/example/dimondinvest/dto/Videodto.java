package com.example.dimondinvest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Videodto {
    private Long id;
    private String title;
    private String date;
    private MultipartFile video;
    private MultipartFile thumbnail;
}
