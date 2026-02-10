package com.vitor.gerenciadordeprodutos.Controllers;

import com.vitor.gerenciadordeprodutos.Business.Mappers.ProductMapper;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductProductionDTO;
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

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    public ResponseEntity<StandardResponse<PageResponse<ProductModel>>> paginate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String name
    ){
        Page<ProductModel> products = service.paginateProducts(page, pageSize, name);
        var response = PageResponse.from(products);

        return ResponseEntity.ok(
                ResponseFactory.ok(response, "All products.")
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<StandardResponse<ProductDTO>> getById(
            @PathVariable Long id
    ){
        var product = service.getById(id);
        var dto = mapper.map(product);

        return ResponseEntity.ok(
                ResponseFactory.ok(dto, "Product found")
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

    @DeleteMapping("{id}")
    public ResponseEntity<StandardResponse<Void>> delete(
            @PathVariable Long id
    ) {
        service.delete(id);

        return ResponseEntity.ok(
                ResponseFactory.ok(null, "Product deleted successfully")
        );
    }

    @GetMapping("/production")
    public ResponseEntity<StandardResponse<PageResponse<ProductProductionDTO>>> paginateProduction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<ProductProductionDTO> result =
                service.paginateProduction(page, pageSize);

        return ResponseEntity.ok(
                ResponseFactory.ok(
                        PageResponse.from(result),
                        "Products production capacity"
                )
        );
    }


}
