package com.vitor.gerenciadordeprodutos.Domain.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "tb_raw_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private int amount;
    @ManyToMany(mappedBy = "materials")
    private Set<ProductModel> products;
}
