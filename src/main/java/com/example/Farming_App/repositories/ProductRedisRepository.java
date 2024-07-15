package com.example.Farming_App.repositories;

import com.example.Farming_App.dto.ProductDto;
import com.example.Farming_App.entity.Product;

import java.util.List;

public interface ProductRedisRepository {
    void saveProduct(ProductDto product);
    List<String> findAllProductsBySeller(Long sellerId);
}
