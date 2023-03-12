package com.zonebug.debugging.dto.response;

import com.zonebug.debugging.dto.MainPostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPostResponseDTO {


    private List<MainPostDTO> issueList;

    private List<MainPostDTO> askList;

    private List<MainPostDTO> shareList;
}
