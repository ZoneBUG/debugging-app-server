package com.zonebug.debugging.domain.drug;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zonebug.debugging.domain.bug.Bug;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drug")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drug {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "name")
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)  // Cascade
    @JsonBackReference
    @JoinColumn(name="bug_id", nullable = false)
    private Bug bug;
}
