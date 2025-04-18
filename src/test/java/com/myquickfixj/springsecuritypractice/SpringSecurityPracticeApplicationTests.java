package com.myquickfixj.springsecuritypractice;

import com.myquickfixj.springsecuritypractice.Entity.User;
import com.myquickfixj.springsecuritypractice.services.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class SpringSecurityPracticeApplicationTests {
    @Autowired
    private JwtService jwtService;



    @Test
    void contextLoads() {


      /* User user=new User(UUID.randomUUID().toString(),"pranjals45@gmail.com","password",true,null);
        String token=jwtService.generateToken(user);
        System.out.println(token);
        System.out.println(jwtService.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcmFuamFsczQ1QGdtYWlsLmNvbSIsImVtYWlsIjoicHJhbmphbHM0NUBnbWFpbC5jb20iLCJpYXQiOjE3NDUwMDUyMDAsImV4cCI6MTc0NTAwNTI2MH0.GMfqh1Lf3fDGq7ZFFZTWQi2yVOSP_T678NkWdh505uw",user));

*/


    }
}
