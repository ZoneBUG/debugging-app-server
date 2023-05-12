package com.zonebug.debugging.domain.bug;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bug")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bug {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "species", length = 20)
    private String species;

    @Column(name = "description", length = 500)
    private String description;
}
