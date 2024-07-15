package com.example.Farming_App.mapper.impl;

import com.example.Farming_App.dto.ProductDto;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Product;
import com.example.Farming_App.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements Mapper<Product, ProductDto> {
    private final ModelMapper modelMapper;

    @Override
    public ProductDto mapTo(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDiscount(product.getDiscount());
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setQuantity(product.getQuantity());
        productDto.setSoldQuantity(product.getSoldQuantity());
//        productDto.setSeller(product.getSeller());

        List<String> urls=new ArrayList<>();
        for(Image image:product.getImages()){
            urls.add(image.getUrl());
        }
        productDto.setImage(urls);
        return productDto;
    }

    @Override
    public Product mapFrom(ProductDto productDto) {
        return modelMapper.map(productDto,Product.class);
    }
}
