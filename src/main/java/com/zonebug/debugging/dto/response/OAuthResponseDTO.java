package com.zonebug.debugging.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class OAuthResponseDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Boolean isMember;

    public OAuthResponseDTO(Long userId, String accessToken, String refreshToken, Boolean isMember) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isMember = isMember;
    }
}
