package com.poseidon.services;

import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import com.poseidon.services.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class TradeCrudService extends AbstractCrudService<Trade> {

    public TradeCrudService(TradeRepository repository) {
        super(repository);
    }

}
