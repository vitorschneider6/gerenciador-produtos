package com.vitor.gerenciadordeprodutos.Controllers;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Communication.Response.PageResponse;
import com.vitor.gerenciadordeprodutos.Communication.Response.ResponseFactory;
import com.vitor.gerenciadordeprodutos.Communication.Response.StandardResponse;
import com.vitor.gerenciadordeprodutos.Domain.Interfaces.ProductServiceInterface;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<StandardResponse<ProductModel>> create(
            @RequestBody ProductDTO dto
    ){
        var product = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseFactory.ok(product, "New product created", HttpStatus.CREATED)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<StandardResponse<ProductModel>> update(
            @PathVariable Long id,
            @RequestBody ProductDTO dto
    ) {
        var product = service.update(id, dto);

        return ResponseEntity.ok(
                ResponseFactory.ok(product, "Product updated")
        );
    }

}
