package com.poseidon.services;

import com.poseidon.domain.DTO.TradeResponseForList;
import com.poseidon.domain.DTO.TradeResponseForUpdate;
import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TradeServiceImpl implements TradeService{

    private final TradeRepository tradeRepository;

    @Override
    public List<TradeResponseForList> findAll() {
        List<Trade> trades = tradeRepository.findAll();

        return trades.stream()
                .map(p -> new TradeResponseForList(p.getTradeId(), p.getAccount(), p.getType(), p.getBuyQuantity()))
                .toList();
    }

    @Override
    public Trade saveTrade(Trade trade) {
        if(trade == null) throw new IllegalArgumentException("Trade Must Not Be Null");

        return tradeRepository.save(trade);
    }

    @Override
    public TradeResponseForUpdate getTradeByIdForAddForm(Integer id) {
        if (id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if (id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");

        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id:" + id));

        return new TradeResponseForUpdate(trade.getTradeId(), trade.getAccount(), trade.getType(), trade.getBuyQuantity());
    }

    @Override
    public Trade updateTradeById(Integer id, Trade trade) {
        if(id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if(id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");
        if(trade == null) throw new IllegalArgumentException("Trade Must Not Be Null");

        Trade tradeToUpdate = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id:" + id));
        tradeToUpdate.setAccount(trade.getAccount());
        tradeToUpdate.setType(trade.getType());
        tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
        tradeToUpdate.setSellQuantity(trade.getSellQuantity());

        return tradeRepository.save(tradeToUpdate);
    }

    @Override
    public void deleteTradeById(Integer id) {
        if (id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if (id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");

        tradeRepository.deleteById(id);
    }
}
