package com.poseidon.services;

import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;
import org.springframework.stereotype.Service;

@Service
public class BidListCrudService extends AbstractCrudService<BidList> {


    protected BidListCrudService(BidListRepository repository) {
        super(repository);
    }
}
