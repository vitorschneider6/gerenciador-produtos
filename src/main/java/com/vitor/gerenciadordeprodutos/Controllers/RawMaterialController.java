package com.vitor.gerenciadordeprodutos.Controllers;

import com.vitor.gerenciadordeprodutos.Domain.Interfaces.RawMaterialServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
public class RawMaterialController {
    @Autowired
    private RawMaterialServiceInterface service;
}
