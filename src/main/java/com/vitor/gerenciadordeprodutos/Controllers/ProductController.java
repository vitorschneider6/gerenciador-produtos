package com.vitor.gerenciadordeprodutos.Controllers;

import com.vitor.gerenciadordeprodutos.Communication.Response.PageResponse;
import com.vitor.gerenciadordeprodutos.Communication.Response.ResponseFactory;
import com.vitor.gerenciadordeprodutos.Communication.Response.StandardResponse;
import com.vitor.gerenciadordeprodutos.Domain.Interfaces.ProductServiceInterface;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductServiceInterface service;

    @GetMapping
    public ResponseEntity<StandardResponse<PageResponse<ProductModel>>> paginate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        Page<ProductModel> products = service.paginateProducts(page, pageSize);
        var response = PageResponse.from(products);

        return ResponseEntity.ok(
                ResponseFactory.ok(response, "All products.")
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<StandardResponse<ProductModel>> getById(
            @PathVariable Long id
    ){
        var product = service.getById(id);

        return ResponseEntity.ok(
                ResponseFactory.ok(product, "Product found")
        );
    }
}
