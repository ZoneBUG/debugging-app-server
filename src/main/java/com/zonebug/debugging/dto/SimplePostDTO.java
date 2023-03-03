package com.zonebug.debugging.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimplePostDTO {

    private Long postId;

    private String nickname;

    private String title;

    private Date createdAt;
}
