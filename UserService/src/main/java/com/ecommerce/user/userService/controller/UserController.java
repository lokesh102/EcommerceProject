package com.ecommerce.user.userService.controller;

import com.ecommerce.user.userService.Models.AuthRequest;
import com.ecommerce.user.userService.Models.AuthResponse;
import com.ecommerce.user.userService.Models.Response;
import com.ecommerce.user.userService.Models.UserRequest;
import com.ecommerce.user.userService.entity.User;
import com.ecommerce.user.userService.repository.UserRepository;
import com.ecommerce.user.userService.service.UserService;
import com.ecommerce.user.userService.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.parameters.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Login User", description = "Authenticate a user and generate a JWT token")
    @ApiResponse(responseCode = "200", description = "Successful Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Register User", description = "Register a new user in the system")
    @ApiResponse(responseCode = "201", description = "User successfully registered")
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserRequest user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Validate Token", description = "Validate a JWT token")
    @ApiResponse(responseCode = "200", description = "Token is valid")
    @ApiResponse(responseCode = "400", description = "Invalid Token")
    @PostMapping("/validate")
    public String getUserByUsername(@RequestParam("token") String token, @RequestBody AuthRequest authRequest) {
        return jwtUtil.validateToken(token, authRequest) ? "Token is Valid" : "Token is Invalid";
    }

    @Operation(summary = "Get User by Username", description = "Retrieve the username of the currently authenticated user")
    @ApiResponse(responseCode = "200", description = "Username retrieved successfully")
    @GetMapping("/{username}")
    public User getUserByUsernames(@Parameter(description = "Username to be fetched") @PathVariable String username) {
        return  userRepository.findByUsername(username).get();
    }

}