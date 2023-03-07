package com.zonebug.debugging.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPathResponseDTO {

    private URL url;
}
