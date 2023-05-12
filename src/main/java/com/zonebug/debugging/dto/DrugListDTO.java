package com.zonebug.debugging.dto;

import com.zonebug.debugging.domain.drug.Drug;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrugListDTO {

    private String bug;

    private List<Drug> druglist;
}
