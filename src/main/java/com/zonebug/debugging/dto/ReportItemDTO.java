package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportItemDTO {

    @NotNull
    private ReportImageDTO reportImageDTO;

    @NotNull
    private CheckListDTO checkListDTO;

    @NotNull
    private DrugListDTO drugListDTO;
}
