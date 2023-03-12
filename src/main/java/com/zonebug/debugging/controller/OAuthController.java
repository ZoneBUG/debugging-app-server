package com.zonebug.debugging.controller;

import com.zonebug.debugging.dto.response.OAuthResponseDTO;
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
    public ResponseEntity<OAuthResponseDTO> kakaoSignUp(@Valid @RequestParam(name = "code") String code) {
        return ResponseEntity.ok(oauthService.signUp(code, "kakao"));
    }

    @GetMapping("/naver")
    public ResponseEntity<OAuthResponseDTO> naverSignUp(@Valid @RequestParam(name = "code") String code) {
        return ResponseEntity.ok(oauthService.signUp(code, "naver"));
    }

//    @PostMapping("/")
//    public ResponseEntity<LoginDto> signIn(){
//        return ResponseEntity.ok(oauthService.signIn);
//    }
}
