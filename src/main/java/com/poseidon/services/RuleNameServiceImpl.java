package com.poseidon.services;

import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@AllArgsConstructor
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName saveRuleName(RuleName ruleName) {
        assertNotNull(ruleName, "RuleName cannot be null");

        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName findById(Integer id) {
        assertNotNull(id, "Id Must Not Be Null");
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
    }

    @Override
    public RuleName updateRuleNameById(Integer id, RuleName ruleName) {
        assertNotNull(id, "Id Must Not Be Null:");
        assertNotNull(ruleName, "RuleName Must Not Be Null:");
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleNameById(Integer id) {
        assertNotNull(id, "Id Must Not Be Null");
        ruleNameRepository.deleteById(id);
    }
}
