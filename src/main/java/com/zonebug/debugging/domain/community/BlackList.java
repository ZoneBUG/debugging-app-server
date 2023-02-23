package com.zonebug.debugging.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zonebug.debugging.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BlackList")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackList {

    
    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "type", length = 45)
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name="black_user", nullable = false)
    private User blackUser;
}
