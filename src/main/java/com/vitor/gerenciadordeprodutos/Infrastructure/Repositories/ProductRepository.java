package com.vitor.gerenciadordeprodutos.Infrastructure.Repositories;

import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Page<ProductModel> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String name, String code, Pageable pageable);
    boolean existsByCode(String code);
}
