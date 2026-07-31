package com.ecommerce.user.userService.service;


import com.ecommerce.user.userService.Models.Response;
import com.ecommerce.user.userService.Models.UserRequest;
import com.ecommerce.user.userService.entity.Role;
import com.ecommerce.user.userService.entity.User;
import com.ecommerce.user.userService.repository.RoleRepository;
import com.ecommerce.user.userService.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Response createUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

        Set<Role> userRoles = new HashSet<>();

        for (String roleName : userRequest.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElse(null);
            if(role == null){
                role = roleRepository.save(Role.builder().name(roleName.toUpperCase()).build());
            }
            userRoles.add(role);
        }

        user.setRoles(userRoles);
        User users =  userRepository.save(user);
        final String token = jwtUtil.generateToken(userRequest.getUsername());
        return new Response(users, token);
    }

}