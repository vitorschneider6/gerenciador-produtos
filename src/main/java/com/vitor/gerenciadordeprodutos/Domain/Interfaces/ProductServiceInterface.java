package com.vitor.gerenciadordeprodutos.Domain.Interfaces;

import com.vitor.gerenciadordeprodutos.Communication.Response.StandardResponse;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import org.springframework.data.domain.Page;

public interface ProductServiceInterface {
    Page<ProductModel> paginateProducts(int page, int pageSize);
}
