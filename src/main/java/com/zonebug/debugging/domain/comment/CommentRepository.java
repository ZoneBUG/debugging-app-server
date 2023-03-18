package com.zonebug.debugging.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //List<Comment> findAll();

    void deleteById(Long id);
}
