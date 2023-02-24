package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    @NotNull
    private Long postId;

    @NotNull
    private String nickname;

    @NotNull
    private String title;

    private String image;

    @NotNull
    private String contents;

    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

    @NotNull
    private Boolean isMine;

    @NotNull
    private Long hits;
}
