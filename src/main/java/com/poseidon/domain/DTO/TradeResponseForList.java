package com.poseidon.domain.DTO;

import com.poseidon.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TradeResponseForList implements ConvertibleDtoFromEntity<Trade, TradeResponseForList>{
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;

    public TradeResponseForList() {
    }

    @Override
    public TradeResponseForList fromEntity(Trade trade) {
        return new TradeResponseForList(trade.getId(), trade.getAccount(), trade.getType(), trade.getBuyQuantity());
    }
}
