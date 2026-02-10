package com.vitor.gerenciadordeprodutos.Domain.Interfaces;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.RawMaterialDTO;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.data.domain.Page;

public interface RawMaterialServiceInterface {
    Page<RawMaterialModel> paginateMaterials(int page, int pageSize);
    RawMaterialModel getById(Long id);
    RawMaterialModel create(RawMaterialDTO dto);

}
