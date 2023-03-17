package com.zonebug.debugging.service;

import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.domain.user.UserRepository;
import com.zonebug.debugging.dto.LoginDto;
import com.zonebug.debugging.dto.TokenDto;
import com.zonebug.debugging.dto.UserDto;
import com.zonebug.debugging.security.util.SecurityUtil;
import com.zonebug.debugging.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signUp(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .period(userDto.getPeriod())
                .type("default")
                .build();

        return userRepository.save(user);
    }

    public TokenDto signIn(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println(authentication.getPrincipal());

        TokenDto tokenDto = new TokenDto(tokenProvider.createAccessToken(authentication), tokenProvider.createRefreshToken(authentication));
        return tokenDto;
    }


    @Transactional(readOnly = true)
    public Optional<User> getCurrentUser() {
        return SecurityUtil.getCurrentUser();
    }


}
