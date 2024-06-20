package com.example.Farming_App.services.impl;

import com.example.Farming_App.dto.JwtAuthenticationResponse;
import com.example.Farming_App.dto.SignInRequest;
import com.example.Farming_App.dto.SignUpRequest;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Role;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.repositories.RoleRepository;
import com.example.Farming_App.services.AuthenticationService;
import com.example.Farming_App.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public Account signup(SignUpRequest signUpRequest){
        Account account=new Account();
        account.setFirstname(signUpRequest.getFirstname());
        account.setLastname(signUpRequest.getLastname());
        account.setUsername(signUpRequest.getUsername());
        account.setRole(roleRepository.findByName("USER"));
        account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return repository.save(account);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword()));
        var user= repository.findByUsername(signInRequest.getUsername());


        if(user.isPresent()){
            var jwt= jwtService.generateToken(user.get());
            var refreshToken= jwtService.generateRefreshToken(new HashMap<>(),user.get());
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
