package com.vitor.gerenciadordeprodutos.Business.Mappers;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.PartDTO;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;

public class PartMapper {
    public RawMaterialModel map(PartDTO dto){
        RawMaterialModel part = new RawMaterialModel();
        part.setName(dto.getName());
        part.setAmount(dto.getAmount());
        part.setDescription(dto.getDescription());

        return part;
    }

    public PartDTO map(RawMaterialModel model){
        PartDTO dto = new PartDTO();
        dto.setName(model.getName());
        dto.setAmount(model.getAmount());
        dto.setDescription(model.getDescription());

        return dto;
    }
}
