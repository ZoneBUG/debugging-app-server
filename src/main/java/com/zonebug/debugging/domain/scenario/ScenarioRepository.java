package com.zonebug.debugging.domain.scenario;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    List<Scenario> findAll(Specification<Scenario> spec);
}
