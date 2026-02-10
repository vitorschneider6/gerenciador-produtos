package com.vitor.gerenciadordeprodutos.Business.Services;

import com.vitor.gerenciadordeprodutos.Business.Mappers.RawMaterialMapper;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.RawMaterialDTO;
import com.vitor.gerenciadordeprodutos.Domain.Exceptions.BusinessException;
import com.vitor.gerenciadordeprodutos.Domain.Interfaces.RawMaterialServiceInterface;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.RawMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RawMaterialService implements RawMaterialServiceInterface {
    @Autowired
    private RawMaterialRepository repository;
    @Autowired
    private RawMaterialMapper mapper;

    @Override
    public Page<RawMaterialModel> paginateMaterials(int page, int pageSize, String name) {
        if (pageSize == 0) {
            var allMaterials = repository.findAll();
            return new org.springframework.data.domain.PageImpl<>(allMaterials);
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        if (name != null && !name.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(name, pageable);
        }

        return repository.findAll(pageable);
    }

    @Override
    public RawMaterialModel getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));
    }

    @Override
    public RawMaterialModel create(RawMaterialDTO dto) {
        if(dto.getAmount() < 0)
            throw new BusinessException("Stock must be upper than 0");

        var model = mapper.map(dto);
        if (repository.existsByName(model.getName()))
            throw new BusinessException("Raw material already exists");

        return repository.save(model);
    }

    @Override
    public RawMaterialModel update(Long id, RawMaterialDTO dto) {
        if(dto.getAmount() < 0)
            throw new BusinessException("Stock must be upper than 0");

        RawMaterialModel material = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));

        if (!material.getName().equals(dto.getName())
                && repository.existsByName(dto.getName())) {
            throw new BusinessException("Raw material name already exists");
        }

        mapper.updateEntity(material, dto);
        return repository.save(material);
    }

    @Override
    public void delete(Long id) {
        RawMaterialModel material = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));

        repository.delete(material);
    }
}
