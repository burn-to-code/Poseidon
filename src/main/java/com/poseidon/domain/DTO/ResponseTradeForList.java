package com.poseidon.domain.DTO;

import com.poseidon.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseTradeForList implements ConvertibleDtoFromEntity<Trade, ResponseTradeForList>{
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;

    public ResponseTradeForList() {
    }

    @Override
    public ResponseTradeForList fromEntity(Trade trade) {
        return new ResponseTradeForList(trade.getId(), trade.getAccount(), trade.getType(), trade.getBuyQuantity());
    }
}
