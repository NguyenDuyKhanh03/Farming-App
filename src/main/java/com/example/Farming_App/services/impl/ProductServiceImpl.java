package com.example.Farming_App.services.impl;

import com.example.Farming_App.dto.ProductDto;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Category;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Product;
import com.example.Farming_App.exception.ResourceNotFoundException;
import com.example.Farming_App.mapper.Mapper;
import com.example.Farming_App.mapper.impl.JsonMapper;
import com.example.Farming_App.repositories.*;
import com.example.Farming_App.services.ImageService;
import com.example.Farming_App.services.JWTService;
import com.example.Farming_App.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final Mapper<Product,ProductDto> mapper;
    private final JWTService jwtService;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;

    private final ProductRedisRepository productRedisRepository;

    private final JsonMapper<ProductDto> productJsonMapper;
    public boolean addProduct(ProductDto productDto) {

        Optional<Account> account=getAccount();
//        if(account.isPresent())
//            productDto.setSeller(account.get());

        productDto.setSoldQuantity(0);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setDescription(productDto.getDescription());
        // Load the category from the database
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","id",String.valueOf(productDto.getCategoryId())));
        product.setCategory(category);
        product.setQuantity(productDto.getQuantity());
        product.setSoldQuantity(0);
        product.setSeller(account.get());


        Product savedProduct= productRepository.save(product);

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : productDto.getImages()) {
            String imageUrl = imageService.upload(file);
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setName(savedProduct.getName());
            image.setRelationId(savedProduct.getId());
            image.setType("product");
            images.add(image);
        }

        savedProduct.setImages(images);

        return productRepository.save(savedProduct)!=null;
    }

    private Optional<Account> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username ="";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return accountRepository.findByMail(username);
    }

    @Override
    public List<ProductDto> getListProduct() {
        Optional<Account> account=getAccount();
        List<String> s=productRedisRepository.findAllProductsBySeller(account.get().getId());
        if(s==null || s.isEmpty()) {
            List<Product> products=productRepository.findBySeller(account.get());
            List<ProductDto> productDtos=products.stream()
                    .map(mapper::mapTo)
                    .collect(Collectors.toList());
            productDtos.forEach(productRedisRepository::saveProduct);
            return productDtos;
        }
        return s.stream()
                .map(this::convertJsonToProductDto)
                .collect(Collectors.toList());
    }


    @Override
    public int removeProductById(Long id) {

        Optional<Account> account=getAccount();
        productRepository.getProductByIdAndSeller(id,account.get())
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Product","id",String.valueOf(id))
                );
        return productRepository.removeProductById(id);
    }

    public List<ProductDto> searchProduct(String keyword){
        List<Product> products=productRepository.search(keyword)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Product","keyword",keyword)
                );
        if(products.isEmpty())
            throw  new ResourceNotFoundException("Product","keyword",keyword);
        return products.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    private ProductDto convertJsonToProductDto(String productJson) {
        return productJsonMapper.mapFrom(productJson,ProductDto.class);
    }


}
