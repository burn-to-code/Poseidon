package com.poseidon.services;

import com.poseidon.domain.RuleName;

import java.util.List;

public interface RuleNameService {

    List<RuleName> findAll();

    RuleName saveRuleName(RuleName ruleName);

    RuleName findById(Integer id);

    RuleName updateRuleNameById(Integer id, RuleName ruleName);

    void deleteRuleNameById(Integer id);
}
