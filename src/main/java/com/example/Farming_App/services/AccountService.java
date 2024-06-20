package com.example.Farming_App.services;

import com.example.Farming_App.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService {

    UserDetailsService userDetailsService();
}
