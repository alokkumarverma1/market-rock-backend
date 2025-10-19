package com.example.dimondinvest.dto;

import com.example.dimondinvest.entity.Dnews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dnewstypedto {
    private String typename;
    private List<Dnews> dnewsList;
}
