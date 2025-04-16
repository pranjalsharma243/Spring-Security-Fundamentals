package com.myquickfixj.springsecuritypractice.services;

import com.myquickfixj.springsecuritypractice.Dto.CustomUserDetails;
import com.myquickfixj.springsecuritypractice.Entity.User;
import com.myquickfixj.springsecuritypractice.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepository;

    public CustomUserDetailService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return new CustomUserDetails(user);

    }
}
