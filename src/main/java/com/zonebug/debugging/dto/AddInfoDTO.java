package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddInfoDTO {

    @NotNull
    private String nickname;

    @NotNull
    private Integer period;
}
