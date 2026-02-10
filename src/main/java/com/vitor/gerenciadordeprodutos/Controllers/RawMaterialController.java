package com.vitor.gerenciadordeprodutos.Controllers;

import com.vitor.gerenciadordeprodutos.Communication.DTOs.RawMaterialDTO;
import com.vitor.gerenciadordeprodutos.Communication.Response.PageResponse;
import com.vitor.gerenciadordeprodutos.Communication.Response.ResponseFactory;
import com.vitor.gerenciadordeprodutos.Communication.Response.StandardResponse;
import com.vitor.gerenciadordeprodutos.Domain.Interfaces.RawMaterialServiceInterface;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials")
public class RawMaterialController {
    @Autowired
    private RawMaterialServiceInterface service;

    @GetMapping
    public ResponseEntity<StandardResponse<PageResponse<RawMaterialModel>>> paginate(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "") String name
    ){
        Page<RawMaterialModel> materials = service.paginateMaterials(page, pageSize, name);
        var response = PageResponse.from(materials);

        return ResponseEntity.ok(
                ResponseFactory.ok(response, "All materials.")
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<StandardResponse<RawMaterialModel>> getById(
            @PathVariable Long id
    ){
        var material = service.getById(id);

        return ResponseEntity.ok(
                ResponseFactory.ok(material, "Material found")
        );
    }

    @PostMapping
    public ResponseEntity<StandardResponse<RawMaterialModel>> create(
            @RequestBody RawMaterialDTO dto
    ){
        var newMaterial = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseFactory.ok(newMaterial, "New material created", HttpStatus.CREATED)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<StandardResponse<RawMaterialModel>> update(
            @PathVariable Long id,
            @RequestBody RawMaterialDTO dto
    ) {
        var updated = service.update(id, dto);

        return ResponseEntity.ok(
                ResponseFactory.ok(updated, "Material updated successfully")
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<StandardResponse<Void>> delete(
            @PathVariable Long id
    ){
        service.delete(id);

        return ResponseEntity.ok(
                ResponseFactory.ok(null, "Material deleted successfully")
        );
    }

}
