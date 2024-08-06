package com.example.Farming_App.dto.product;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductRequest extends ProductDto {
    private List<MultipartFile> images;
}
