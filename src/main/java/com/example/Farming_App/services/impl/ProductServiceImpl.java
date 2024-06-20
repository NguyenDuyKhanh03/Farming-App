package com.example.Farming_App.services.impl;

import com.example.Farming_App.dto.ProductDto;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Product;
import com.example.Farming_App.mapper.Mapper;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.repositories.ProductRepository;
import com.example.Farming_App.services.JWTService;
import com.example.Farming_App.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final Mapper<Product,ProductDto> mapper;
    private final JWTService jwtService;

    public boolean addProduct(ProductDto productDto) {

        Optional<Account> account=getAccount();
        if(account.isPresent())
            productDto.setSeller(account.get());

        productDto.setSoldQuantity(0);
        Product product=mapper.mapFrom(productDto);
        return productRepository.save(product)!=null;
    }

    private Optional<Account> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username ="";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<ProductDto> getListProduct() {
        Optional<Account> account=getAccount();
        List<Product> products=productRepository.findBySeller(account.get());
        return products.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }
}
