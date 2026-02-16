package com.vitor.gerenciadordeprodutos.Communication.DTOs;

import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialDTO {
    private Long id;
    private String name;
    private String description;
    private int amount;
}
