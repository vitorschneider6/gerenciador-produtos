package com.vitor.gerenciadordeprodutos.Infrastructure.Repositories;

import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialRepository extends JpaRepository<RawMaterialModel, Long> {
    boolean existsByName(String name);
    Page<RawMaterialModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
