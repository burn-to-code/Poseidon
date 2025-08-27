package com.poseidon.services;

import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeCrudService extends AbstractCrudService<Trade> {

    public TradeCrudService(TradeRepository repository) {
        super(repository);
    }
}
