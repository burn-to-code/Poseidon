package com.poseidon.services;

import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import com.poseidon.services.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class RuleNameCrudService extends AbstractCrudService<RuleName> {

    public RuleNameCrudService(RuleNameRepository repository) {
        super(repository);
    }
}
