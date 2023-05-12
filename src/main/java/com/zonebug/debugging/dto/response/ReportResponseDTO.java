package com.zonebug.debugging.dto.response;

import com.zonebug.debugging.dto.CheckListDTO;
import com.zonebug.debugging.dto.DrugListDTO;
import com.zonebug.debugging.dto.ReportImageDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {


    private List<ReportImageDTO> reportImageDTO;

    private List<CheckListDTO> checkListDTO;

    private List<DrugListDTO> drugListDTO;

}
