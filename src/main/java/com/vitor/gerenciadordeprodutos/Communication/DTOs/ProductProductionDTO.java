package com.vitor.gerenciadordeprodutos.Communication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductProductionDTO {
    private Long id;
    private String name;
    private String code;
    private BigDecimal unitValue;
    private BigDecimal productionValue;
    private int producibleAmount;
}