package com.poseidon.repositories;

import com.poseidon.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

    Boolean existsByName(String ruleName);
}
