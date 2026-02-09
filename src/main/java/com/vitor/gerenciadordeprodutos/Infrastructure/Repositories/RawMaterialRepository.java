package com.vitor.gerenciadordeprodutos.Infrastructure.Repositories;

import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialRepository extends JpaRepository<RawMaterialModel, Long> {
}
