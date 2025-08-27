package com.poseidon.domain.DTO;

import com.poseidon.domain.BidList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BidListResponseForList implements ConvertibleDtoFromEntity<BidList, BidListResponseForList>{
    private Integer id;
    private String account;
    private String type;
    private Double bidQuantity;

    public BidListResponseForList() {
    }

    @Override
    public BidListResponseForList fromEntity(BidList bidList) {
        return new BidListResponseForList(bidList.getId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }
}
