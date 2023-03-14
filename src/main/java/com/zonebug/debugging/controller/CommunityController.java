package com.zonebug.debugging.controller;

import com.zonebug.debugging.dto.WritePostDTO;
import com.zonebug.debugging.dto.response.MainPostResponseDTO;
import com.zonebug.debugging.dto.response.PostIdResponseDTO;
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
    public ResponseEntity<MainPostResponseDTO> getMainPosts(
            @AuthenticationPrincipal UserDetails authUser) {
        return ResponseEntity.ok(communityService.getMainPosts(authUser));
    }

    @PostMapping("/post")
    public ResponseEntity<PostIdResponseDTO> writePost(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestBody WritePostDTO writePost) {
        return ResponseEntity.ok(communityService.writePost(authUser, writePost));
    }

    @PutMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<PostIdResponseDTO> updatePost(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long postId,
            @RequestBody WritePostDTO writePost
    ) {
        return ResponseEntity.ok(communityService.updatePost(authUser, postId, writePost));
    }

    @DeleteMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<PostIdResponseDTO> deletePost(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(communityService.deletePost(authUser, postId));
    }

    @GetMapping("/{tag}")//?tag=으로 수정할 것
    public ResponseEntity<SimplePostResponseDTO> getSimplePosts(
            @PathVariable String tag,
            @AuthenticationPrincipal UserDetails authUser,
            Integer pageNum) {
        return ResponseEntity.ok(communityService.getTagPosts(authUser, tag, pageNum));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponseDTO> readPost(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(communityService.readPost(authUser, postId));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<SimplePostResponseDTO> searchPost(
            @PathVariable String keyword,
            @RequestParam(name = "pageNum") Integer pageNum,
            @AuthenticationPrincipal UserDetails authUser) {
        return ResponseEntity.ok(communityService.searchPosts(authUser, keyword, pageNum));
    }
}
