package com.poseidon.services;

import com.poseidon.domain.DTO.TradeResponseForList;
import com.poseidon.domain.DTO.TradeResponseForUpdate;
import com.poseidon.domain.Trade;

import java.util.List;

public interface TradeService {

    List<TradeResponseForList> findAll();

    Trade saveTrade(Trade trade);

    TradeResponseForUpdate getTradeByIdForAddForm(Integer id);

    Trade updateTradeById(Integer id, Trade trade);

    void deleteTradeById(Integer id);
}
