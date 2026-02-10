package com.vitor.gerenciadordeprodutos.Business.Mappers;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.RawMaterialDTO;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.stereotype.Service;

@Service
public class RawMaterialMapper {
    public RawMaterialModel map(RawMaterialDTO dto){
        RawMaterialModel part = new RawMaterialModel();
        part.setName(dto.getName());
        part.setAmount(dto.getAmount());
        part.setDescription(dto.getDescription());

        return part;
    }

    public RawMaterialDTO map(RawMaterialModel model){
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setName(model.getName());
        dto.setAmount(model.getAmount());
        dto.setDescription(model.getDescription());

        return dto;
    }
}
