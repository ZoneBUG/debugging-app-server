package com.zonebug.debugging.controller;

import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.dto.AddInfoDTO;
import com.zonebug.debugging.dto.UserDto;
import com.zonebug.debugging.dto.response.OAuthResponseDTO;
import com.zonebug.debugging.service.OAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oauthService;

    public OAuthController(OAuthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/kakao")
    public ResponseEntity<OAuthResponseDTO> signUp(@Valid @RequestParam(name = "code") String code) {
        return ResponseEntity.ok(oauthService.signUp(code, "kakao"));
    }

    @GetMapping("/naver")
    public ResponseEntity<OAuthResponseDTO> signup(@Valid @RequestParam String code, @RequestParam String state) {
        return ResponseEntity.ok(oauthService.signUp(code, "naver"));
    }

    @PostMapping("/info")
    public ResponseEntity<User> addInfo(@Valid @AuthenticationPrincipal UserDetails authUser, @RequestBody AddInfoDTO addInfoDTO) {
        return ResponseEntity.ok(oauthService.addInfo(authUser, addInfoDTO));
    }

//    @PostMapping("/")
//    public ResponseEntity<LoginDto> signIn(){
//        return ResponseEntity.ok(oauthService.signIn);
//    }
}
