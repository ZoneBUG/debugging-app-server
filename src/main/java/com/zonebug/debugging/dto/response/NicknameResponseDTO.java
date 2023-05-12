package com.zonebug.debugging.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NicknameResponseDTO {

    private boolean isPresent;
}
