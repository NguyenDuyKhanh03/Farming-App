package com.example.Farming_App.services.impl;

import com.example.Farming_App.handler.ResourceNotFoundException;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;



    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return accountRepository.findByMail(username)
                        .orElseThrow(()->new ResourceNotFoundException("User","email",username));
            }
        };
    }
}
