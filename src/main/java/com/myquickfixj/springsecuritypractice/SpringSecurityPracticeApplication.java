package com.myquickfixj.springsecuritypractice;

import com.myquickfixj.springsecuritypractice.Entity.Role;
import com.myquickfixj.springsecuritypractice.Entity.User;
import com.myquickfixj.springsecuritypractice.config.AppConstants;
import com.myquickfixj.springsecuritypractice.repository.RoleRepo;
import com.myquickfixj.springsecuritypractice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class SpringSecurityPracticeApplication implements CommandLineRunner {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public SpringSecurityPracticeApplication(UserRepo userRepo,
                                             RoleRepo roleRepo) {
        this.userRepo = userRepo;


        this.roleRepo = roleRepo;
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityPracticeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {




        // Ensure roles are created only if they do not exist
        Role role1 = roleRepo.findByRoleName(AppConstants.ROLE_ADMIN)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(AppConstants.ROLE_ADMIN);
                    newRole.setRoleId(UUID.randomUUID().toString());
                    return roleRepo.save(newRole);
                });

        Role role2 = roleRepo.findByRoleName(AppConstants.ROLE_GUEST)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(AppConstants.ROLE_GUEST);
                    newRole.setRoleId(UUID.randomUUID().toString());
                    return roleRepo.save(newRole);
                });










        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail("abc@gmail.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setActive(true);
        user.assignRoles(role2);
        userRepo.findByEmail(user.getEmail()).ifPresentOrElse(
                existingUser -> {
                    System.out.println("User already exists");
                },
                () -> {
                    userRepo.save(user);
                    System.out.println("User created");
                }
        );





        User user2 = new User();
        user2.setUserId(UUID.randomUUID().toString());
        user2.setEmail("pranjal@gmail.com");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setActive(true);
        user2.assignRoles(role1);
        userRepo.findByEmail(user2.getEmail()).ifPresentOrElse(
                existingUser -> {
                    System.out.println("User already exists");
                },
                () -> {
                    userRepo.save(user2);
                    System.out.println("User created");
                }
        );


        User user3 = new User();
        user3.setUserId(UUID.randomUUID().toString());
        user3.setEmail("xyz@gmail.com");
        user3.setPassword(passwordEncoder.encode("password"));
        user3.setActive(true);
        user3.assignRoles(role1);
        userRepo.findByEmail(user3.getEmail()).ifPresentOrElse(
                existingUser -> {
                    System.out.println("User already exists");
                },
                () -> {
                    userRepo.save(user3);
                    System.out.println("User created");
                }
        );



        User user4 = new User();
        user4.setUserId(UUID.randomUUID().toString());
        user4.setEmail("pqr@gmail.com");
        user4.setPassword(passwordEncoder.encode("password"));
        user4.setActive(true);
        user4.assignRoles(role1);
        user4.assignRoles(role2);
        userRepo.findByEmail(user4.getEmail()).ifPresentOrElse(
                existingUser -> {
                    System.out.println("User already exists");
                },
                () -> {
                    userRepo.save(user4);
                    System.out.println("User created");
                }
        );


        User user5 = new User();
        user5.setUserId(UUID.randomUUID().toString());
        user5.setEmail("pqrs@gmail.com");
        user5.setPassword(passwordEncoder.encode("password"));
        user5.setActive(true);
        user5.assignRoles(role1);
        user5.assignRoles(role2);
        userRepo.findByEmail(user5.getEmail()).ifPresentOrElse(
                existingUser -> {
                    System.out.println("User already exists");
                },
                () -> {
                    userRepo.save(user5);
                    System.out.println("User created");
                }
        );

    }
}
