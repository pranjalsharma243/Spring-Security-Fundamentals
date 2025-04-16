package com.myquickfixj.springsecuritypractice.Dto;

import com.myquickfixj.springsecuritypractice.Entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;


    private String email;

    private String password;

    private boolean isActive;
    Set<Role> roles=new HashSet<>();

}
