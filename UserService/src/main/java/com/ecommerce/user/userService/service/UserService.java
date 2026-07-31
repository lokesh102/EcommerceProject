package com.ecommerce.user.userService.service;


import com.ecommerce.user.userService.Models.Response;
import com.ecommerce.user.userService.Models.UserRequest;
import com.ecommerce.user.userService.entity.User;

import java.util.Optional;

public interface UserService {
    Response createUser(UserRequest user);

}