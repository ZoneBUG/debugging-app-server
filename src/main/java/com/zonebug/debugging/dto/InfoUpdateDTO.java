package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class InfoUpdateDTO {

    @Size(min = 3, max = 45)
    @NotNull
    private String nickname;

    private int period;
}
