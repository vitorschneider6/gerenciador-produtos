package com.vitor.gerenciadordeprodutos.Domain.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(name = "vw_product_production")
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductProductionView {
    @Id
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "producible_amount")
    private int producibleAmount;
    @Column(name = "active")
    private boolean active;
    @Column(name = "total_value")
    private BigDecimal totalValue;
}
