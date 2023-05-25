package com.zonebug.debugging.controller;

import com.zonebug.debugging.dto.response.GetPathResponseDTO;
import com.zonebug.debugging.service.SourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/source")
@RequiredArgsConstructor
public class SourceController {

    private final SourceService sourceService;

    @GetMapping("url")
    @ResponseBody
    public ResponseEntity<GetPathResponseDTO> createURL(
            @RequestParam(name = "userId") Long userId
            ) {
        return ResponseEntity.ok(sourceService.createURL(userId));
    }
}
