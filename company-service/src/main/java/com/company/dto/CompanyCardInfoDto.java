package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCardInfoDto {

    private String companyName;
    private String location;
    private Double ratings;
    private Integer totalClients;
}