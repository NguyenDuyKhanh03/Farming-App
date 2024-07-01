package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.AccountsConstants;
import com.example.Farming_App.dto.*;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Authentication Controller",
        description = "Controller to handle user authentication operations such as signup, signin, and password reset."
)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new account inside FarmingApp"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status CREATE",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        Account account=authenticationService.signup(signUpRequest);
        if(account!=null)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
    }

    @Operation(
            summary = "Sign In REST API",
            description = "REST API to sign in an existing user inside FarmingApp"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status SignUp",
                    content = @Content(
                            schema = @Schema(implementation = JwtAuthenticationResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @Operation(
            summary = "Reset Password REST API",
            description = "REST API to reset the password of an existing user inside FarmingApp"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Reset Password",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@Email @RequestParam String mail,@Size(min = 5,max = 15) @RequestParam String newPassword){
        int isResetPassword=authenticationService.resetPassword(mail,newPassword);
        if(isResetPassword==1){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.STATUS_200));
        }
        else
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.STATUS_500));
    }
}
