package com.myquickfixj.springsecuritypractice.services;

import com.myquickfixj.springsecuritypractice.Dto.UserDto;
import com.myquickfixj.springsecuritypractice.Entity.Role;
import com.myquickfixj.springsecuritypractice.Entity.User;
import com.myquickfixj.springsecuritypractice.config.AppConstants;
import com.myquickfixj.springsecuritypractice.repository.RoleRepo;
import com.myquickfixj.springsecuritypractice.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    private  UserRepo userRepo;

    private ModelMapper modelMapper;

    private RoleRepo roleRepo;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper,RoleRepo roleRepo,PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user=modelMapper.map(userDto, User.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role=roleRepo.findByRoleName(AppConstants.ROLE_GUEST).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        user.assignRoles(role);
        User savedUser=userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users=userRepo.findAll();
        if(users.isEmpty()){
            throw new RuntimeException("No users found");
        }
        List<UserDto> userDtos=users.stream().map(user->modelMapper.map(user, UserDto.class)).toList();
        return userDtos;
    }
}
