package com.example.Farming_App.mapper.impl;

import com.example.Farming_App.dto.ProductDto;
import com.example.Farming_App.entity.Product;
import com.example.Farming_App.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements Mapper<Product, ProductDto> {
    private final ModelMapper modelMapper;
    @Override
    public ProductDto mapTo(Product product) {
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public Product mapFrom(ProductDto productDto) {
        return modelMapper.map(productDto,Product.class);
    }
}
