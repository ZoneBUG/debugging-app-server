package com.zonebug.debugging.service;

import com.zonebug.debugging.domain.comment.Comment;
import com.zonebug.debugging.domain.comment.CommentRepository;
import com.zonebug.debugging.domain.post.Post;
import com.zonebug.debugging.domain.post.PostRepository;
import com.zonebug.debugging.domain.post.PostSpecification;
import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.domain.user.UserRepository;
import com.zonebug.debugging.dto.*;
import com.zonebug.debugging.dto.response.*;
import com.zonebug.debugging.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentIdResponseDTO writeComment(User user, WriteCommentDTO writeCommentDTO) {
        Long postId = writeCommentDTO.getPostId();
        Post post = postRepository.findById(postId).orElseThrow();
        Comment comment;

        if(writeCommentDTO.getParentId() == 0) {
            comment = Comment.builder()
                    .post(post)
                    .user(user)
                    .contents(writeCommentDTO.getContents())
                    .parentId(0L)
                    .createdAt(new Date())
                    .build();
        } else {
            comment = Comment.builder()
                    .post(post)
                    .user(user)
                    .contents(writeCommentDTO.getContents())
                    .parentId(writeCommentDTO.getParentId())
                    .createdAt(new Date())
                    .build();
        }

        Comment savedComment = commentRepository.saveAndFlush(comment);
        return new CommentIdResponseDTO(savedComment);
    }


    public CommentIdResponseDTO updateComment(User user, Long commentId, WriteCommentDTO writeCommentDTO) {
        Comment currentComment = commentRepository.findById(commentId).orElseThrow();

        if(checkWriter(user, currentComment.getUser())) {
            currentComment.update(writeCommentDTO);
            commentRepository.save(currentComment);
            return new CommentIdResponseDTO(currentComment);
        } else {
            throw new RuntimeException("작성자가 아닙니다.");
        }

    }


    public CommentIdResponseDTO deleteComment(User user, Long commentId) {
        Comment currentComment = commentRepository.findById(commentId).orElseThrow();

        if(checkWriter(user, currentComment.getUser())) {
            commentRepository.deleteById(commentId);
            return new CommentIdResponseDTO(commentId);
        } else {
            throw new RuntimeException("작성자가 아닙니다.");
        }

    }

    private boolean checkWriter(User currentUser, User writer) {
        if(currentUser.getId() == writer.getId()) return true;
        return false;
    }
}
