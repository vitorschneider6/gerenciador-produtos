package com.vitor.gerenciadordeprodutos.Controllers;

import com.vitor.gerenciadordeprodutos.Communication.Response.PageResponse;
import com.vitor.gerenciadordeprodutos.Communication.Response.ResponseFactory;
import com.vitor.gerenciadordeprodutos.Communication.Response.StandardResponse;
import com.vitor.gerenciadordeprodutos.Domain.Interfaces.RawMaterialServiceInterface;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
public class RawMaterialController {
    @Autowired
    private RawMaterialServiceInterface service;

    @GetMapping
    public ResponseEntity<StandardResponse<PageResponse<RawMaterialModel>>> paginate(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ){
        Page<RawMaterialModel> materials = service.paginateMaterials(page, pageSize);
        var response = PageResponse.from(materials);

        return ResponseEntity.ok(
                ResponseFactory.ok(response, "All materials.")
        );
    }
}
