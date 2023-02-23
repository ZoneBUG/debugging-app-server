package com.zonebug.debugging.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zonebug.debugging.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "post")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {


    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @JsonIgnore
    @Column(name = "tag", length = 45)
    private String tag;

    @Column(name = "title", length = 45)
    private String title;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "contents", length = 500)
    private String contents;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date deletedAt;


//    public void update(AddPostRequestDTO addPostRequestDTO) {
//        this.tag = addPostRequestDTO.getTag();
//        this.title = addPostRequestDTO.getTitle();
//        this.image = addPostRequestDTO.getImage();
//        this.contents = addPostRequestDTO.getContents();
//        this.updatedAt = new Date();
//    }
}
