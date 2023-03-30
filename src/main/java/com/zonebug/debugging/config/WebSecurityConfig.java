package com.zonebug.debugging.config;


import com.zonebug.debugging.security.jwt.JwtAccessDeniedHandler;
import com.zonebug.debugging.security.jwt.JwtAuthenticationEntryPoint;
import com.zonebug.debugging.security.jwt.JwtSecurityConfig;
import com.zonebug.debugging.security.jwt.TokenProvider;
import com.zonebug.debugging.security.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            CustomUserDetailsService customUserDetailsService
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests((req) ->
                        req
                                .requestMatchers("/user/authenticate", "/user/signup", "/user/signin", "/user/test/**").permitAll()
                                .requestMatchers("/oauth", "/oauth/kakao", "/oauth/callback/kakao", "/oauth/naver/**").permitAll()
                                .requestMatchers("/source/url").permitAll()
                                .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)



                .apply(new JwtSecurityConfig(tokenProvider));


        return http.build();
    }

}
