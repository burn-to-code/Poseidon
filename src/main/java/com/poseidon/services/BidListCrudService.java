package com.poseidon.services;

import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;
import com.poseidon.services.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class BidListCrudService extends AbstractCrudService<BidList> {


    public BidListCrudService(BidListRepository repository) {
        super(repository);
    }

}
