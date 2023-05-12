package com.zonebug.debugging.domain.checklist;

import com.zonebug.debugging.domain.bug.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    @Query("SELECT c.contents FROM CheckList c WHERE c.bug = :bug")
    List<String> findContentsByBug(Bug bug);
}
