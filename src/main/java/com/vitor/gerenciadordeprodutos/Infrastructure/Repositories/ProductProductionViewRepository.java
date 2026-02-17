package com.vitor.gerenciadordeprodutos.Infrastructure.Repositories;

import com.vitor.gerenciadordeprodutos.Domain.Models.ProductProductionView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductProductionViewRepository extends JpaRepository<ProductProductionView, Long> {
    Page<ProductProductionView> findByActiveTrue(Pageable pageable);
    Page<ProductProductionView> findByActiveTrueAndNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
            String name,
            String code,
            Pageable pageable
    );

}
