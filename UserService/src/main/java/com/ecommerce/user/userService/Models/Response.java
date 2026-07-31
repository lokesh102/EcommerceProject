package com.ecommerce.user.userService.Models;

import com.ecommerce.user.userService.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {

    public User user;
    public String token;

}
