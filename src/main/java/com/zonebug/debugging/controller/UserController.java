package com.zonebug.debugging.controller;

import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.dto.LoginDto;
import com.zonebug.debugging.dto.TokenDto;
import com.zonebug.debugging.dto.UserDto;
import com.zonebug.debugging.security.jwt.JwtFilter;
import com.zonebug.debugging.security.jwt.TokenProvider;
import com.zonebug.debugging.security.user.CustomUserDetails;
import com.zonebug.debugging.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @GetMapping("")
    public ResponseEntity<User> userOk(@AuthenticationPrincipal CustomUserDetails principalDetails) {
        return ResponseEntity.ok(principalDetails.getUser());
    }

    @GetMapping("/test/{email}")
    public ResponseEntity<TokenDto> test(@PathVariable String email) {
        return ResponseEntity.ok(new TokenDto("acc", "res"));
    }


    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signUp(userDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signIn(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.signIn(loginDto));
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

    @GetMapping("/info")
    public ResponseEntity<Optional<User>> info() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    // Authentication 회원 정보
    @GetMapping("/info2")
    public ResponseEntity<User> user(@AuthenticationPrincipal CustomUserDetails principalDetails) {
        User user = principalDetails.getUser();

        return ResponseEntity.ok(principalDetails.getUser());
    }
}
