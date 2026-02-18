package com.vitor.gerenciadordeprodutos.Service;

import com.vitor.gerenciadordeprodutos.Business.Mappers.ProductMapper;
import com.vitor.gerenciadordeprodutos.Business.Services.ProductService;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductDTO;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.ProductProductionDTO;
import com.vitor.gerenciadordeprodutos.Domain.Exceptions.BusinessException;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductModel;
import com.vitor.gerenciadordeprodutos.Domain.Models.ProductProductionView;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.ProductProductionViewRepository;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.ProductRepository;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.RawMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private RawMaterialRepository materialRepository;

    @Mock
    private ProductProductionViewRepository productionRepository;

    @Mock
    private ProductMapper mapper;

    @Test
    void ShouldPaginateProductsWithoutFilter(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductModel> page = Page.empty();

        when(repository.findAll(pageable)).thenReturn(page);
        Page<ProductModel> result = service.paginateProducts(0, 10, null);

        assertThat(result).isSameAs(page);
        verify(repository).findAll(pageable);
    }

    @Test
    void ShouldPaginateProductsWithFilter(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductModel> page = Page.empty();

        when(repository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase("product", "product", pageable)).thenReturn(page);
        Page<ProductModel> result = service.paginateProducts(0, 10, "product");

        assertThat(result).isSameAs(page);
        verify(repository).findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase("product", "product", pageable);
    }

    @Test
    void shouldReturnProductWhenExists(){
        ProductModel product = new ProductModel();

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        ProductModel result = service.getById(1L);

        assertThat(result).isSameAs(product);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenProductNotExists(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenCodeAlreadyExists() {
        ProductDTO dto = new ProductDTO();
        dto.setCode("ABC");

        when(repository.existsByCode("ABC")).thenReturn(true);

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Code already exists");
    }

    @Test
    void shouldCreateProduct(){
        ProductDTO dto = new ProductDTO();
        dto.setCode("ABC");

        ProductModel product = new ProductModel();
        product.setCode("ABC");

        when(repository.existsByCode("ABC")).thenReturn(false);
        when(mapper.map(dto)).thenReturn(product);
        when(repository.save(product)).thenReturn(product);

        ProductModel result = service.create(dto);

        assertThat(result).isSameAs(product);
        verify(repository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenCodeAlreadyExistsOnUpdate() {
        ProductModel product = new ProductModel();
        product.setCode("ABCD");
        ProductDTO dto = new ProductDTO();
        dto.setCode("ABC");

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.existsByCode("ABC")).thenReturn(true);

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Code already exists");
    }

    @Test
    void shouldUpdateProduct() {
        ProductDTO dto = new ProductDTO();
        dto.setCode("ABCD");

        ProductModel product = new ProductModel();
        product.setCode("ABC");

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.existsByCode("ABCD")).thenReturn(false);
        when(repository.save(product)).thenReturn(product);

        ProductModel result = service.update(1L, dto);

        assertThat(result).isSameAs(product);
        verify(repository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotExists() {
        ProductDTO dto = new ProductDTO();
        dto.setCode("ABC");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product not found");
    }

    @Test
    void shouldDeleteProduct() {
        ProductModel product = new ProductModel();
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        service.delete(1L);

        verify(repository).delete(product);
    }

    @Test
    void shouldPaginateProductionAndMapToDTO() {
        ProductProductionView view = new ProductProductionView(
                1L,
                "Product",
                "CODE",
                BigDecimal.TEN,
                5,
                true,
                BigDecimal.valueOf(50)
        );

        Page<ProductProductionView> page =
                new PageImpl<>(List.of(view));

        when(productionRepository.findByActiveTrue(any(Pageable.class)))
                .thenReturn(page);

        Page<ProductProductionDTO> result =
                service.paginateProduction(0, 10, null);
        List<ProductProductionDTO> content = result.getContent();

        assertThat(content).hasSize(1);

        ProductProductionDTO dto = result.getContent().get(0);
        assertThat(dto.getProductionValue()).isEqualByComparingTo("50");
    }

}
