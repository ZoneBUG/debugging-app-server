package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 10, max = 45)
    private String email;

    @NotNull
    @Size(min = 8, max = 150)
    private String password;

}
