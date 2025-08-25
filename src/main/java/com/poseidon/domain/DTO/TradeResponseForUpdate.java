package com.poseidon.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TradeResponseForUpdate {
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;
}
