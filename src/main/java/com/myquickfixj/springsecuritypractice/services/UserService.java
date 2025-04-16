package com.myquickfixj.springsecuritypractice.services;

import com.myquickfixj.springsecuritypractice.Dto.UserDto;

import java.util.List;

public interface UserService {


    UserDto create(UserDto userDto);

    List<UserDto> getAllUsers();
}
