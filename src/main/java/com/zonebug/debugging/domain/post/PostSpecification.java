package com.zonebug.debugging.domain.post;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;


public class PostSpecification {

    public static Specification<Post> equalTag(String tag) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tag"), tag);
    }

    public static Specification<Post> findHot() {
        Date date = new Date();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("createdAt"), new Date(date.getTime()+(1000*60*60*24*-1)), date);
    }
}
