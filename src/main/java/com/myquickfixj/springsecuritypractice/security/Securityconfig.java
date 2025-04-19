package com.myquickfixj.springsecuritypractice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myquickfixj.springsecuritypractice.Dto.CustomMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class Securityconfig {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.cors(cors-> {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("http://localhost:3000");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            config.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            cors.configurationSource(source);
        });

        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/doc.html","/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()

                );
        httpSecurity.sessionManagement(e-> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.exceptionHandling(exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            CustomMessage customMessage = new CustomMessage();
                            customMessage.setMessage("You do not have permission to access this resource "+ accessDeniedException.getMessage());
                            customMessage.setStatus(false);
                            ObjectMapper objectMapper = new ObjectMapper();
                            response.getWriter().println(objectMapper.writeValueAsString(customMessage));
                         })
        );

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
