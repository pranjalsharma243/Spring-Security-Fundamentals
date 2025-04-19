package com.myquickfixj.springsecuritypractice.Controlller;

import com.myquickfixj.springsecuritypractice.Dto.UserDto;
import com.myquickfixj.springsecuritypractice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*@SecurityRequirement(
        name = "jwtScheme"
)*/
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation( summary = "Create a new user",
                description = "This API is used to create a new user in the system. The user details are provided in the request body and the created user details are returned in the response."
    )
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "501", description = "Internal server error")
    @PostMapping
    public UserDto createUser(@RequestBody  UserDto userDto) {

        return userService.create(userDto);
    }
    @GetMapping("/getallusers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

   // TODO: in case of updateUSer we can pass id of the user by @Parameter(decription="something")


}
