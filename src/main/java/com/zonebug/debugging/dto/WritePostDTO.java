package com.zonebug.debugging.dto;

import com.zonebug.debugging.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WritePostDTO {

    private String tag;
    private String title;
    private String image;
    private String contents;
}
