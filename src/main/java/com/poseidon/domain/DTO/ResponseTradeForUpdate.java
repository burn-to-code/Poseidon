package com.poseidon.domain.DTO;

import com.poseidon.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseTradeForUpdate implements ConvertibleDtoFromEntity<Trade, ResponseTradeForUpdate>{
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;

    public ResponseTradeForUpdate() {
    }

    @Override
    public ResponseTradeForUpdate fromEntity(Trade trade) {
        return new ResponseTradeForUpdate(trade.getId(), trade.getAccount(), trade.getType(), trade.getBuyQuantity());
    }
}
