package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.ProductsConstants;
import com.example.Farming_App.dto.ProductDto;
import com.example.Farming_App.dto.ResponseDto;
import com.example.Farming_App.services.ImageService;
import com.example.Farming_App.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;


    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addProduct(@RequestParam("name") String name,
                                             @RequestParam("price") double price,
                                             @RequestParam("discount") int discount,
                                             @RequestParam("description") String description,
                                             @RequestParam("categoryId") Long categoryId,
                                             @RequestParam("quantity") int quantity,
                                             @RequestPart("images") List<MultipartFile> images){

        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setDiscount(discount);
        productDto.setDescription(description);
        productDto.setCategoryId(categoryId);
        productDto.setQuantity(quantity);
        productDto.setImages(images);

        boolean isAdded = productService.addProduct(productDto);
        if (isAdded) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }

    @GetMapping("/list-product")
    public ResponseEntity<List<ProductDto>> getListProductFromUser(){
        return ResponseEntity.ok(productService.getListProduct());
    }

    @DeleteMapping("/remove-product/{id}")
    public ResponseEntity<ResponseDto> removeProduct(@PathVariable("id") Long id){
        int isDeleted=productService.removeProductById(id);
        if(isDeleted==1)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductsConstants.STATUS_200,ProductsConstants.MESSAGE_200));
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String keyword){
        List<ProductDto> productDtos=productService.searchProduct(keyword);
        if(productDtos!=null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productDtos);
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(ProductsConstants.STATUS_500,ProductsConstants.MESSAGE_500));
        }
    }

}
