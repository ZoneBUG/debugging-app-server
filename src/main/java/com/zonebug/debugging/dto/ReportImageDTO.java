package com.zonebug.debugging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportImageDTO {

    @NotNull
    private String image;

    @NotNull
    private String bug;

    @NotNull
    private Date date;
}
