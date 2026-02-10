package com.vitor.gerenciadordeprodutos.Domain.Interfaces;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import org.springframework.data.domain.Page;


public interface ProductServiceInterface {
    Page<ProductModel> paginateProducts(int page, int pageSize);
    ProductModel getById(Long id);
    ProductModel create(ProductDTO dto);

}
