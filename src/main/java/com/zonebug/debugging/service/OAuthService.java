package com.zonebug.debugging.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonebug.debugging.security.jwt.TokenProvider;
import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.domain.user.UserRepository;
import com.zonebug.debugging.dto.response.OAuthResponseDTO;
import com.zonebug.debugging.security.user.CustomUserDetailsService;
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

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri")
    private String NAVER_REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri")
    private String NAVER_USER_INFO_URI;


    private String TYPE;
    private String CLIENT_ID;
    private String REDIRECT_URI;


    public OAuthResponseDTO signUp(String code, String type) {
        TYPE = type;

        if(type == "kakao") {
            CLIENT_ID = KAKAO_CLIENT_ID;
            REDIRECT_URI = KAKAO_REDIRECT_URI;
        } else {
            CLIENT_ID = NAVER_CLIENT_ID;
            REDIRECT_URI = NAVER_REDIRECT_URI;
        }

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

        return new OAuthResponseDTO(user.getId(), jwtToken, user.getRefreshToken(), isMember);
    }


    private String getAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        if(TYPE == "naver") body.add("client_secret", NAVER_CLIENT_SECRET);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response;

        if(TYPE == "kakao") {
            response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } else {
            response = rt.exchange(
                    "https://nid.naver.com/oauth2.0/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        }

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            System.out.println(jsonNode);
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
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response;

        if(TYPE == "kakao") {
            response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } else {
            response = rt.exchange(
                    "https://openapi.naver.com/v1/nid/me",
                    HttpMethod.POST,
                    request,
                    String.class
            );
        }

        // responseBody 정보 꺼내기
        String responseBody = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            if(TYPE == "kakao") {
                return jsonNode.get("kakao_account").get("email").asText();
            } else {
                System.out.println(jsonNode.get("response"));
                return jsonNode.get("response").get("email").asText();
            }


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
                    .password(passwordEncoder.encode(TYPE))
                    .type(TYPE)
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
