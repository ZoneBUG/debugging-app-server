package com.zonebug.debugging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 10, max = 45)
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 150)
    private String password;

    @NotNull
    @Size(min = 3, max = 45)
    private String nickname;

    @NotNull
    private int period;

    @NotNull
    @Size(min = 3, max = 45)
    private String type;
}
