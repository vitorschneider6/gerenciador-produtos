package com.vitor.gerenciadordeprodutos.Business.Mappers;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductMaterialDTO;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.RawMaterialDTO;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (model.getMaterials() != null && !model.getMaterials().isEmpty()) {
            List<ProductMaterialDTO> materials = model.getMaterials().stream()
                    .map(pm -> {
                        ProductMaterialDTO matDto = new ProductMaterialDTO();
                        matDto.setId(pm.getRawMaterial().getId());
                        matDto.setRequiredQuantity(pm.getRequiredQuantity());
                        return matDto;
                    })
                    .toList();

            dto.setMaterials(materials);
        }

        return dto;
    }

    public void updateEntity(ProductModel model, ProductDTO dto) {
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setActive(dto.getActive());
    }
}