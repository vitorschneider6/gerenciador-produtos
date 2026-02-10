package com.vitor.gerenciadordeprodutos.Business.Services;

import com.vitor.gerenciadordeprodutos.Business.Mappers.ProductMapper;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Domain.Interfaces.ProductServiceInterface;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.ProductRepository;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.RawMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class ProductService implements ProductServiceInterface {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public Page<ProductModel> paginateProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return repository.findAll(pageable);
    }

    @Override
    public ProductModel getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public ProductModel create(ProductDTO dto) {
        ProductModel product = mapper.map(dto);

        if (dto.getMaterials() != null && !dto.getMaterials().isEmpty()) {

            Set<RawMaterialModel> materials =
                    new HashSet<>(rawMaterialRepository.findAllById(dto.getMaterials()));

            if (materials.size() != dto.getMaterials().size()) {
                throw new EntityNotFoundException("One or more raw materials not found");
            }

            product.setMaterials(materials);
        }

        return repository.save(product);
    }
}
