package com.myquickfixj.springsecuritypractice.Controlller;

import com.myquickfixj.springsecuritypractice.Dto.UserDto;
import com.myquickfixj.springsecuritypractice.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody  UserDto userDto) {

        return userService.create(userDto);
    }
    @GetMapping("/getallusers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
