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
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<MainPostDTO> tagClassify(String tag) {
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;
        Pageable limit;
        if (Objects.equals(tag, "issue")) {
            spec = spec.and(PostSpecification.findHot());
            limit = PageRequest.of(0, 3, Sort.by("hits").descending());
        } else {
            spec = spec.and(PostSpecification.equalTag(tag));
            limit = PageRequest.of(0, 3, Sort.by("createdAt").descending());
        }
        Page<Post> findPosts = postRepository.findAll(spec, limit);

        List<MainPostDTO> list = new ArrayList<>();

        for (Post post : findPosts) {
            Long postId = post.getId();
            String postTag = post.getTag();
            String contents = post.getContents();
            Date createdAt = post.getCreatedAt();
            MainPostDTO mainpostDTO = new MainPostDTO(postId, postTag, contents, createdAt);
            list.add(mainpostDTO);
        }
        return list;
    }


    public MainPostResponseDTO getMainPosts(CustomUserDetails authUser) {

        User user = authUser.getUser();

        List<MainPostDTO> issueListDTO = tagClassify("issue");
        List<MainPostDTO> askListDTO = tagClassify("ask");
        List<MainPostDTO> shareListDTO = tagClassify("share");

        return new MainPostResponseDTO(issueListDTO, askListDTO, shareListDTO);
    }

    public SimplePostResponseDTO getTagPosts(
            CustomUserDetails authUser, String tag, Integer pageNum
    ) {
        User user = authUser.getUser();

        PageRequest pageRequest = PageRequest.of(pageNum, 10, Sort.by("createdAt").descending());
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;
        spec = spec.and(PostSpecification.equalTag(tag));

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);

        List<SimplePostDTO> list = new ArrayList<>();
        for (Post post : findPosts) {
            Long postId = post.getId();
            String nickname = post.getUser().getNickname();
            String title = post.getTitle();
            Date createdAt = post.getCreatedAt();
            SimplePostDTO SimplePostDTO = new SimplePostDTO(postId, nickname, title, createdAt);
            list.add(SimplePostDTO);
        }
        return new SimplePostResponseDTO(list, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    public PostResponseDTO readPost(CustomUserDetails authUser, Long postId) {


        Post post = postRepository.findById(postId)
                .orElseThrow();
        User postUser = userRepository.findByEmail(post.getUser().getEmail())
                .orElseThrow();
        User loginUser = authUser.getUser();

        post.setHits(post.getHits() + 1);   // 조회수 증가
        Post savedPost = postRepository.saveAndFlush(post);

        PostDTO postDTO = new PostDTO(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getImage(), post.getContents(), post.getCreatedAt(), post.getUpdatedAt(), postUser == loginUser, savedPost.getHits());

        List<Comment> findComments = commentRepository.findAll();
        List<CommentDTO> list = new ArrayList<>();
        for (Comment c : findComments) {
            if(c.getPost().getId() == postId) {
                Long commentId = c.getId();
                Long parentId = c.getParentId();
                String nickname = c.getUser().getNickname();
                String contents = c.getContents();
                CommentDTO commentDTO = new CommentDTO(commentId, parentId, nickname, contents, c.getUser() == loginUser);
                list.add(commentDTO);
            }
        }
        return new PostResponseDTO(postDTO, list);
    }

    public SimplePostResponseDTO searchPosts(CustomUserDetails authUser, String keyword, Integer pageNum) {

        User user = authUser.getUser();

        PageRequest pageRequest = PageRequest.of(pageNum, 10, Sort.by("createdAt").descending());
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;
        spec = spec.and(PostSpecification.likeTitle(keyword))
                .or(PostSpecification.likeContents(keyword));

        Page<Post> findPosts = postRepository.findAll(spec, pageRequest);

        List<SimplePostDTO> list = new ArrayList<>();
        for (Post post : findPosts) {
            Long postId = post.getId();
            String nickname = post.getUser().getNickname();
            String title = post.getTitle();
            Date createdAt = post.getCreatedAt();
            SimplePostDTO SimplePostDTO = new SimplePostDTO(postId, nickname, title, createdAt);
            list.add(SimplePostDTO);
        }
        return new SimplePostResponseDTO(list, findPosts.getTotalPages(), findPosts.getTotalElements());
    }

    public PostIdResponseDTO writePost(CustomUserDetails authUser, WritePostDTO writePost) {

        User findUser = authUser.getUser();

        Post post = Post.builder()
                .user(findUser)
                .tag(writePost.getTag())
                .title(writePost.getTitle())
                .image(writePost.getImage())
                .contents(writePost.getContents())
                .hits(0L)
                .createdAt(new Date())
                .build();

        Post savedPost = postRepository.saveAndFlush(post);

        return new PostIdResponseDTO(savedPost);
    }

    public PostIdResponseDTO updatePost(CustomUserDetails authUser, Long postId, WritePostDTO writePost){

        User findUser = authUser.getUser();
        Post findPost = postRepository.findByIdAndUserId(postId, findUser.getId())
                .orElseThrow();

        findPost.update(writePost);
        postRepository.save(findPost);

        return new PostIdResponseDTO(findPost);
    }

    public PostIdResponseDTO deletePost(CustomUserDetails authUser, Long postId) {

        User findUser = authUser.getUser();
        Post findPost = postRepository.findByIdAndUserId(postId, findUser.getId())
                .orElseThrow();

        postRepository.deleteById(findPost.getId());

        return new PostIdResponseDTO(findPost);
    }

}