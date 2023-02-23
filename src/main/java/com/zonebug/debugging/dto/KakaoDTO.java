package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoDTO {

    @NotNull
    @Size(min = 10, max = 45)
    private String email;
}
