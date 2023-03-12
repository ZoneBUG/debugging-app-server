package com.zonebug.debugging.dto.response;

import com.zonebug.debugging.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostIdResponseDTO {

    private Long postId;

    public PostIdResponseDTO(Post post) {
        this.postId = post.getId();
    }
}
