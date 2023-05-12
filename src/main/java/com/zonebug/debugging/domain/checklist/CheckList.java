package com.zonebug.debugging.domain.checklist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zonebug.debugging.domain.bug.Bug;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "checklist")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckList {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)  // Cascade
    @JsonBackReference
    @JoinColumn(name="bug_id", nullable = false)
    private Bug bug;

    @Column(name = "contents", length = 200)
    private String contents;
}
