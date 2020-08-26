package com.litvaj.eshop.controller;

import com.litvaj.eshop.model.User;
import com.litvaj.eshop.model.request.UserDto;
import com.litvaj.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public User registerUserAccount(@RequestBody UserDto userDto) {
        return userService.registerNewUser(userDto);
    }
}
