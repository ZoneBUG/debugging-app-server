package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @NotNull
    private Long commentId;

    @NotNull
    private Long parentId;

    @NotNull
    private String nickname;

    @NotNull
    private String contents;

    @NotNull
    private Boolean isMine;
}
