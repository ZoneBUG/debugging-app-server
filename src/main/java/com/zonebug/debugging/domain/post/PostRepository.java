package com.zonebug.debugging.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Specification<Post> spec, Pageable pageable);
    Optional<Post> findByIdAndUserId(Long id, Long userId);

    Optional<Post> findById(Long id);

    void deleteById(Long id);
}
