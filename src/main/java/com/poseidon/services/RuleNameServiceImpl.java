package com.poseidon.services;

import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



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
        if(ruleName == null) throw new IllegalArgumentException("RuleName Must Not Be Null");

        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName findById(Integer id) {
        if(id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if(id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
    }

    @Override
    public RuleName updateRuleNameById(Integer id, RuleName ruleName) {
        if(id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if(id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");
        if(ruleName == null) throw new IllegalArgumentException("RuleName Must Not Be Null");

        RuleName ruleUpdated = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
        ruleUpdated.setName(ruleName.getName());
        ruleUpdated.setDescription(ruleName.getDescription());
        ruleUpdated.setJson(ruleName.getJson());
        ruleUpdated.setSqlPart(ruleName.getSqlPart());
        ruleUpdated.setSqlStr(ruleName.getSqlStr());
        return ruleNameRepository.save(ruleUpdated);
    }

    @Override
    public void deleteRuleNameById(Integer id) {
        if(id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if(id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");
        ruleNameRepository.deleteById(id);
    }
}
