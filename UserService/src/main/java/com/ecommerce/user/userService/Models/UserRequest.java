package com.ecommerce.user.userService.Models;

import com.ecommerce.user.userService.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class UserRequest {

    private String username;
    private String email;
    private String password;
    private List<String> roles = new ArrayList<>();
}
