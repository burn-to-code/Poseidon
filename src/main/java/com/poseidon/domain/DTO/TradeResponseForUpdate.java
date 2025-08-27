package com.poseidon.domain.DTO;

import com.poseidon.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TradeResponseForUpdate implements ConvertibleDtoFromEntity<Trade, TradeResponseForUpdate>{
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;

    public TradeResponseForUpdate() {
    }

    @Override
    public TradeResponseForUpdate fromEntity(Trade trade) {
        return new TradeResponseForUpdate(trade.getId(), trade.getAccount(), trade.getType(), trade.getBuyQuantity());
    }
}
