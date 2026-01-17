package com.flab.coongyaboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Cross-Site Request Forgery 공격 방어 설정
                // 토큰 저장소 : 쿠키 저장
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                // 세션 정책
                // 세션 생성 정책 : 필요 시 생성
                // 세션 고정 공격 방지 : 새 세션 생성
                // 최대 동시 세션 수 1개
                // 동시 세션 제어 : 기존 세션 만료
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation().newSession()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))
                // TODO 인가 규칙 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/signup", "/api/v1/login").permitAll()
                        .anyRequest().authenticated())
                // 로그아웃 처리 설정
                // 세션 무효화, 쿠키 삭제
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "SESSION"))
                .build();
    }
}
