package com.zonebug.debugging.dto.response;

import com.zonebug.debugging.dto.SimplePostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimplePostResponseDTO {


    private List<SimplePostDTO> postList;

    private Integer totalPages;

    private Long totalResults;
}
