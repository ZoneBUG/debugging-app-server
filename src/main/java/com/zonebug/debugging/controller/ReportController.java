package com.zonebug.debugging.controller;

import com.zonebug.debugging.dto.response.ReportResponseDTO;
import com.zonebug.debugging.security.user.CustomUserDetails;
import com.zonebug.debugging.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{period}")
    public ResponseEntity<ReportResponseDTO> getReport(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @PathVariable(name = "period") Integer period){
        return ResponseEntity.ok(reportService.getReport(authUser, period));
    }
}
