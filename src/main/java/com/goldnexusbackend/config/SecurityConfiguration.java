package com.goldnexusbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldnexusbackend.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form->form.disable())
                .httpBasic(httpBasic->httpBasic.disable())
                .logout(logout->logout.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex->ex
                        .authenticationEntryPoint((req,resp,authException)->{
                            resp.setContentType("application/json;charset=utf-8");
                            resp.setStatus(401);
                            Map<String,Object> result = new HashMap<>();
                            result.put("code",401);
                            result.put("msg","未登录");
                            result.put("data",null);
                            resp.getWriter().write(new ObjectMapper().writeValueAsString(result));
                            log.info("已拦截未登录请求");
                        }))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/goldnexus/user/register","/goldnexus/user/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
