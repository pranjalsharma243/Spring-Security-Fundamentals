package com.myquickfixj.springsecuritypractice.repository;

import com.myquickfixj.springsecuritypractice.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, String> {

    Optional<Role> findByRoleName(String roleName);

}
