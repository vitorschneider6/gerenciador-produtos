package com.vitor.gerenciadordeprodutos.Communication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductMaterialDTO {
    private Long id;
    private int requiredQuantity;
}
