package com.example.Farming_App.repositories;

import com.example.Farming_App.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
