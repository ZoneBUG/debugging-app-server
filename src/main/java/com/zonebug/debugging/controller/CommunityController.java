package com.zonebug.debugging.controller;

import com.zonebug.debugging.dto.response.MainPostResponseDTO;
import com.zonebug.debugging.dto.response.TagPostResponseDTO;
import com.zonebug.debugging.dto.response.PostResponseDTO;
import com.zonebug.debugging.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/")
    public ResponseEntity<MainPostResponseDTO> getMainPosts(UserDetails authUser){
        return ResponseEntity.ok(communityService.getMainPosts(authUser));
    }

    @GetMapping("/{tag}")
    public ResponseEntity<TagPostResponseDTO> getTagPosts(
            @PathVariable String tag,
            UserDetails authUser, Integer pageNum) {
        return ResponseEntity.ok(communityService.getTagPosts(authUser, tag, pageNum));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> readPost(
            @PathVariable Long postId,
            UserDetails authUser){
        return ResponseEntity.ok(communityService.readPost(authUser, postId));
    }
}
