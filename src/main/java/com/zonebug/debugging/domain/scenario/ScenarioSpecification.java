package com.zonebug.debugging.domain.scenario;

import org.springframework.data.jpa.domain.Specification;
import com.zonebug.debugging.domain.user.User;

import java.time.LocalDate;
import java.util.Date;

public class ScenarioSpecification {

    public static Specification<Scenario> findScenario(int period, User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate nDaysAgo = currentDate.minusDays(period);

        Date fromDate = java.sql.Date.valueOf(nDaysAgo);
        Date toDate = java.sql.Date.valueOf(currentDate);

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.between(root.get("createdAt"), fromDate, toDate),
                        criteriaBuilder.equal(root.get("user"), user)
                );
    }
}
