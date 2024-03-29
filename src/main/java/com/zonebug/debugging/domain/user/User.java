package com.zonebug.debugging.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter @Setter
@Builder
@AllArgsConstructor
public class User {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 45, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", length = 150)
    private String password;

    @Column(name = "nickname", length = 45, unique = true)
    private String nickname;

    @Column(name = "period")
    private Integer period;

    @Column(name = "type", length = 45)
    private String type;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public User(){
        this.email = getEmail();
    }

}
