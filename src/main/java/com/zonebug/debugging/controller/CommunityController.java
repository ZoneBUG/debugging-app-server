package com.zonebug.debugging.controller;

import com.zonebug.debugging.dto.response.MainPostResponseDTO;
import com.zonebug.debugging.dto.response.SimplePostResponseDTO;
import com.zonebug.debugging.dto.response.PostResponseDTO;
import com.zonebug.debugging.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/")
    public ResponseEntity<MainPostResponseDTO> getMainPosts(UserDetails authUser) {
        return ResponseEntity.ok(communityService.getMainPosts(authUser));
    }

    @GetMapping("/{tag}")
    public ResponseEntity<SimplePostResponseDTO> getSimplePosts(
            @PathVariable String tag,
            @AuthenticationPrincipal UserDetails authUser,
            Integer pageNum) {
        return ResponseEntity.ok(communityService.getTagPosts(authUser, tag, pageNum));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> readPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails authUser) {
        return ResponseEntity.ok(communityService.readPost(authUser, postId));
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<SimplePostResponseDTO> searchPost(
            @PathVariable String keyword,
            @RequestParam(name = "pageNum") Integer pageNum,
            @AuthenticationPrincipal UserDetails authUser) {
        return ResponseEntity.ok(communityService.searchPosts(authUser, keyword, pageNum));
    }
}
