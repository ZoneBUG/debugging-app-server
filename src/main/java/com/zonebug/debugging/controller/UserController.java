package com.zonebug.debugging.controller;

import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.dto.LoginDto;
import com.zonebug.debugging.dto.TokenDto;
import com.zonebug.debugging.dto.UserDto;
import com.zonebug.debugging.dto.response.NicknameResponseDTO;
import com.zonebug.debugging.security.jwt.TokenProvider;
import com.zonebug.debugging.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signUp(userDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signIn(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.signIn(loginDto));
    }

    @GetMapping("/nickname")
    public ResponseEntity<NicknameResponseDTO> checkNickname(
            @RequestParam String nickname
    ){
        return ResponseEntity.ok(userService.checkNickname(nickname));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        return new ResponseEntity(new TokenDto(accessToken, refreshToken), HttpStatus.OK);
    }


    // Authentication 회원 정보
    @GetMapping("/info")
    public ResponseEntity<Optional<User>> info() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }


}
