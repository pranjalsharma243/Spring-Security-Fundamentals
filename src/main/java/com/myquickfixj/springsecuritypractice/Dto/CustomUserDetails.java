package com.myquickfixj.springsecuritypractice.Dto;

import com.myquickfixj.springsecuritypractice.Entity.Role;
import com.myquickfixj.springsecuritypractice.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public User getUser() {
        return user;
    }

    public  CustomUserDetails(User user){
        this.user = user;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Role> roles=user.getRoles();
        Set<SimpleGrantedAuthority> authorities=roles.stream().map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
