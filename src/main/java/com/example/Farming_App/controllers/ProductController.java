package com.example.Farming_App.controllers;

import com.example.Farming_App.dto.ProductDto;
import com.example.Farming_App.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto){
        boolean isAdded = productService.addProduct(productDto);
        if (isAdded) {
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add product", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/list-product")
    public ResponseEntity<List<ProductDto>> getListProductFromUser(){
        return ResponseEntity.ok(productService.getListProduct());
    }

}
