package com.zonebug.debugging.controller;

import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.service.OAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oauthService;

    public OAuthController(OAuthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/kakao")
    public ResponseEntity<User> signup(@Valid @RequestParam(name = "code") String code) {
        return ResponseEntity.ok(oauthService.signup(code));
    }
}
