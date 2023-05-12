package com.zonebug.debugging.domain.drug;

import com.zonebug.debugging.domain.bug.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrugRepository extends JpaRepository<Drug, Long> {


    List<Drug> findByBug(Bug bug);
}
