package com.example.Farming_App.services;

import com.example.Farming_App.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    boolean addProduct(ProductDto productDto);

    List<ProductDto> getListProduct();

    int removeProductById(Long id);
}
