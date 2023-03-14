package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPostDTO {

    @NotNull
    private Long postId;

    @NotNull
    private String tag;

    @NotNull
    private String title;

    @NotNull
    private Date createdAt;
}
