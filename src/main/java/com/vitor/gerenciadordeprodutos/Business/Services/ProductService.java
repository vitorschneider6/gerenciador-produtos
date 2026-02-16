package com.vitor.gerenciadordeprodutos.Business.Services;

import com.vitor.gerenciadordeprodutos.Business.Mappers.ProductMapper;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductMaterialDTO;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductProductionDTO;
import com.vitor.gerenciadordeprodutos.Domain.Exceptions.BusinessException;
import com.vitor.gerenciadordeprodutos.Domain.Interfaces.ProductServiceInterface;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductMaterialModel;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductProductionView;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.ProductProductionViewRepository;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.ProductRepository;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.RawMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private ProductProductionViewRepository productionRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public Page<ProductModel> paginateProducts(int page, int pageSize, String name) {
        Pageable pageable = PageRequest.of(page, pageSize);

        if (name != null && !name.isEmpty()) {
            return repository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(name, name, pageable);
        }

        return repository.findAll(pageable);
    }

    @Override
    public ProductModel getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public ProductModel create(ProductDTO dto) {
        if (repository.existsByCode(dto.getCode()))
            throw new BusinessException("Code already exists");

        ProductModel product = mapper.map(dto);

        if (dto.getMaterials() != null && !dto.getMaterials().isEmpty()) {

            Set<ProductMaterialModel> productMaterials = new HashSet<>();

            for (ProductMaterialDTO item : dto.getMaterials()) {

                RawMaterialModel rawMaterial = rawMaterialRepository
                        .findById(item.getId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Raw material not found: " + item.getId()
                                )
                        );

                ProductMaterialModel pm = new ProductMaterialModel();
                pm.setProduct(product);
                pm.setRawMaterial(rawMaterial);
                pm.setRequiredQuantity(item.getRequiredQuantity());

                productMaterials.add(pm);
            }

            product.setMaterials(productMaterials);
        }

        ProductModel saved = repository.save(product);
        saved.setMaterials(null);

        return saved;
    }

    @Override
    public ProductModel update(Long id, ProductDTO dto) {
        ProductModel product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (!product.getCode().equals(dto.getCode())
                && repository.existsByCode(dto.getCode())) {
            throw new BusinessException("Code already exists");
        }

        mapper.updateEntity(product, dto);
        product.getMaterials().clear();

        if (dto.getMaterials() != null && !dto.getMaterials().isEmpty()) {

            Set<ProductMaterialModel> productMaterials = new HashSet<>();

            for (ProductMaterialDTO item : dto.getMaterials()) {

                RawMaterialModel rawMaterial = rawMaterialRepository
                        .findById(item.getId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Raw material not found: " + item.getId()
                                )
                        );

                ProductMaterialModel pm = new ProductMaterialModel();
                pm.setProduct(product);
                pm.setRawMaterial(rawMaterial);
                pm.setRequiredQuantity(item.getRequiredQuantity());

                productMaterials.add(pm);
            }

            product.getMaterials().addAll(productMaterials);
        }

        return repository.save(product);
    }

    @Override
    public void delete(Long id) {
        ProductModel product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        repository.delete(product);
    }

    @Override
    public Page<ProductProductionDTO> paginateProduction(int page, int pageSize, String name) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "totalValue"));

        Page<ProductProductionView> products;

        if (name != null && !name.isEmpty()) {
            products = productionRepository.findByActiveTrueAndNameContainingIgnoreCase(name, pageable);
        } else {
            products = productionRepository.findByActiveTrue(pageable);
        }

        return products.map(p ->
                new ProductProductionDTO(
                        p.getProductId(),
                        p.getName(),
                        p.getPrice(),
                        p.getTotalValue(),
                        p.getProducibleAmount()
                )
        );
    }
}
