package com.example.dimondinvest.dto;

import com.example.dimondinvest.entity.Register;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registerdto {
    private String name;
    private Long number;
    private String email;
    private String city;
    private String password;
    private Long newnumber;


    public Registerdto(Register register) {
    }
}
