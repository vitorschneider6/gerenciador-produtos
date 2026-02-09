package com.vitor.gerenciadordeprodutos.Infrastructure.Repositories;

import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
