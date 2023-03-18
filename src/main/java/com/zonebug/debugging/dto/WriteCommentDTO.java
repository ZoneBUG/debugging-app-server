package com.zonebug.debugging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteCommentDTO {

    private Long postId;
    private Long parentId;
    private String contents;
}
