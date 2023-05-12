package com.zonebug.debugging.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListDTO {

    private String bug;

    private List<String> checklist;
}
