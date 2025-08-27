package com.poseidon.services;

import com.poseidon.domain.DTO.TradeResponseForList;
import com.poseidon.domain.DTO.TradeResponseForUpdate;
import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeCrudService extends AbstractCrudDtoService<Trade, TradeResponseForList, TradeResponseForUpdate> {

    public TradeCrudService(TradeRepository repository) {
        super(repository);
    }

    @Override
    protected TradeResponseForList toListDto(Trade entity) {
        return new TradeResponseForList(entity.getId(), entity.getAccount(), entity.getType(), entity.getBuyQuantity());
    }

    @Override
    protected TradeResponseForUpdate toUpdateDto(Trade entity) {
        return new TradeResponseForUpdate(entity.getId(), entity.getAccount(), entity.getType(), entity.getBuyQuantity());
    }
}
