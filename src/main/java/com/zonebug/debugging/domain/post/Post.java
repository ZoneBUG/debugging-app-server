package com.zonebug.debugging.domain.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.dto.WritePostDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "post")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP where id = ?")
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

    @Column(name = "hits")
    private Long hits;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Date deletedAt;


    public void update(WritePostDTO addPostDTO) {
        this.tag = addPostDTO.getTag();
        this.title = addPostDTO.getTitle();
        this.image = addPostDTO.getImage();
        this.contents = addPostDTO.getContents();
        this.updatedAt = new Date();
    }
}
