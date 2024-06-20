package com.example.Farming_App.dto;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Category;
import com.example.Farming_App.entity.Image;
import com.example.Farming_App.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private int discount;
    private String description;
    private Long categoryId;
    private int quantity;
    private int soldQuantity;
    private Account seller;
    private List<Image> images;
}
