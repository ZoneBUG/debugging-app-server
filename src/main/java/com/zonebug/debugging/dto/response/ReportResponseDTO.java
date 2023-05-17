package com.zonebug.debugging.dto.response;

import com.zonebug.debugging.dto.CheckListDTO;
import com.zonebug.debugging.dto.DrugListDTO;
import com.zonebug.debugging.dto.ReportImageDTO;
import com.zonebug.debugging.dto.ReportItemDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {

    private List<ReportItemDTO> reportItemDTO;

}
