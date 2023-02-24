package com.zonebug.debugging.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonebug.debugging.config.jwt.TokenProvider;
import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.domain.user.UserRepository;
import com.zonebug.debugging.dto.response.KakaoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;


    public KakaoResponseDTO signUp(String code) {

        // "인가 코드"로 "accessToken" 요청
        String kakaoAccessToken = getAccessToken(code);

        // 토큰으로 카카오 API 호출 (이메일 정보 가져오기)
        String email = getUserInfo(kakaoAccessToken);

        // DB정보 확인 -> 없으면 DB에 저장
        User user = registerUserIfNeed(email);

        // JWT 토큰 리턴 & 로그인 처리
        String jwtToken = usersAuthorizationInput(user);

        // 회원여부 닉네임으로 확인
        Boolean isMember = checkIsMember(user);

        return new KakaoResponseDTO(user.getId(), jwtToken, user.getRefreshToken(), isMember);
    }

//    public KakaoResponseDTO signIn(){
//        return new LoginDto();
//    }

    private String getAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KAKAO_CLIENT_ID);
        body.add("redirect_uri", KAKAO_REDIRECT_URI);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            System.out.println("in exception");
            return e.toString();
        }
    }

    private String getUserInfo(String accessToken) {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기 - Post 방식
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // responseBody 정보 꺼내기
        String responseBody = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            return jsonNode.get("kakao_account").get("email").asText();
        } catch (Exception e) {
            return e.toString();
        }
    }

    // DB정보 확인 -> 없으면 DB에 저장
    private User registerUserIfNeed(String email) {
        // DB에 중복된 이메일 있는지 확인
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            // DB에 정보 등록
            User newUser = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode("kakao"))
                    .type("kakao")
                    .build();
            userRepository.save(newUser);
        }

        return userRepository.findByEmail(email).get();
    }

    private String usersAuthorizationInput(User user) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        return accessToken;
    }

    private Boolean checkIsMember(User user) {
        return user.getNickname() != null;
    }
}
