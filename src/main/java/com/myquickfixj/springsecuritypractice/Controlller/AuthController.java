package com.myquickfixj.springsecuritypractice.Controlller;

import com.myquickfixj.springsecuritypractice.Dto.CustomUserDetails;
import com.myquickfixj.springsecuritypractice.Dto.JwtResponse;
import com.myquickfixj.springsecuritypractice.Dto.LoginRequest;
import com.myquickfixj.springsecuritypractice.Dto.UserDto;
import com.myquickfixj.springsecuritypractice.Entity.User;
import com.myquickfixj.springsecuritypractice.services.CustomUserDetailService;
import com.myquickfixj.springsecuritypractice.services.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody LoginRequest loginRequest) {

        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            authentication = manager.authenticate(authenticationToken);

        }
        //wo user DB m hone chahiye nhi toh allow nhi hoga
        catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid User Details !!");
        }
        //authenticated
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        User user = userDetails.getUser();
        JwtResponse response = JwtResponse.builder()
                .token(token)
                .user(mapper.map(user, UserDto.class))
                .build();
        return ResponseEntity.ok(response);


    }

}
