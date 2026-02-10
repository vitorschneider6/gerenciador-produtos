package com.vitor.gerenciadordeprodutos.Business.Mappers;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.RawMaterialDTO;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductModel map(ProductDTO dto){
        ProductModel product = new ProductModel();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setActive(dto.getActive());

        return product;
    }

    public ProductDTO map(ProductModel model){
        ProductDTO dto = new ProductDTO();
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setActive(model.isActive());

        return dto;
    }

    public void updateEntity(ProductModel model, ProductDTO dto) {
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setActive(dto.getActive());
    }
}