package com.zonebug.debugging.dto.response;

import com.zonebug.debugging.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentIdResponseDTO {

    private Long commentId;

    public CommentIdResponseDTO(Comment comment) {
        this.commentId = comment.getId();
    }
}
