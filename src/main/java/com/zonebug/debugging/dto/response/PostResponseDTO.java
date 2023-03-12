package com.zonebug.debugging.dto.response;

import com.zonebug.debugging.dto.CommentDTO;
import com.zonebug.debugging.dto.PostDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {

    private PostDTO post;

    private List<CommentDTO> commentList;
}
