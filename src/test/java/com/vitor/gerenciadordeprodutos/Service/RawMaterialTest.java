package com.vitor.gerenciadordeprodutos.Service;

import com.vitor.gerenciadordeprodutos.Business.Mappers.RawMaterialMapper;
import com.vitor.gerenciadordeprodutos.Business.Services.RawMaterialService;
import com.vitor.gerenciadordeprodutos.Communication.DTOs.RawMaterialDTO;
import com.vitor.gerenciadordeprodutos.Domain.Exceptions.BusinessException;
import com.vitor.gerenciadordeprodutos.Domain.Models.RawMaterialModel;
import com.vitor.gerenciadordeprodutos.Infrastructure.Repositories.RawMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RawMaterialTest {
    @InjectMocks
    private RawMaterialService service;

    @Mock
    private RawMaterialRepository repository;

    @Mock
    private RawMaterialMapper mapper;

    @Test
    void ShouldPaginateRawMaterialsWithoutFilter(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<RawMaterialModel> page = Page.empty();

        when(repository.findAll(pageable)).thenReturn(page);
        Page<RawMaterialModel> result = service.paginateMaterials(0, 10, null);

        assertThat(result).isSameAs(page);
        verify(repository).findAll(pageable);
    }

    @Test
    void ShouldPaginateRawMaterialsWithFilter(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<RawMaterialModel> page = Page.empty();

        when(repository.findByNameContainingIgnoreCase("material", pageable)).thenReturn(page);
        Page<RawMaterialModel> result = service.paginateMaterials(0, 10, "material");

        assertThat(result).isSameAs(page);
        verify(repository).findByNameContainingIgnoreCase("material", pageable);
    }

    @Test
    void shouldReturnRawMaterialWhenExists(){
        RawMaterialModel material = new RawMaterialModel();

        when(repository.findById(1L)).thenReturn(Optional.of(material));
        RawMaterialModel result = service.getById(1L);

        assertThat(result).isSameAs(material);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenRawMaterialNotExists(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowBusinessExceptionWhenStockLowerThanOne(){
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setAmount(0);

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Stock must be upper than 0");
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExists() {
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setAmount(2);
        dto.setName("ABC");

        when(repository.existsByName("ABC")).thenReturn(true);

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Raw material already exists");
    }

    @Test
    void shouldCreateRawMaterial(){
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setAmount(2);
        dto.setName("ABC");

        RawMaterialModel material = new RawMaterialModel();
        material.setName("ABC");

        when(repository.existsByName("ABC")).thenReturn(false);
        when(mapper.map(dto)).thenReturn(material);
        when(repository.save(material)).thenReturn(material);

        RawMaterialModel result = service.create(dto);

        assertThat(result).isSameAs(material);
        verify(repository).save(material);
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsOnUpdate() {
        RawMaterialModel material = new RawMaterialModel();
        material.setName("ABCD");
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setName("ABC");

        when(repository.findById(1L)).thenReturn(Optional.of(material));
        when(repository.existsByName("ABC")).thenReturn(true);

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Raw material name already exists");
    }

    @Test
    void shouldUpdateRawMaterial() {
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setName("ABCD");

        RawMaterialModel material = new RawMaterialModel();
        material.setName("ABC");

        when(repository.findById(1L)).thenReturn(Optional.of(material));
        when(repository.existsByName("ABCD")).thenReturn(false);
        when(repository.save(material)).thenReturn(material);

        RawMaterialModel result = service.update(1L, dto);

        assertThat(result).isSameAs(material);
        verify(repository).save(material);
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialNotExists() {
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setAmount(20);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Material not found");
    }

    @Test
    void shouldDeleteRawMaterial() {
        RawMaterialModel material = new RawMaterialModel();
        when(repository.findById(1L)).thenReturn(Optional.of(material));

        service.delete(1L);

        verify(repository).delete(material);
    }
}
