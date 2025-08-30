package com.poseidon.domain.DTO;

import com.poseidon.domain.BidList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseBidListForList implements ConvertibleDtoFromEntity<BidList, ResponseBidListForList>{
    private Integer id;
    private String account;
    private String type;
    private Double bidQuantity;

    public ResponseBidListForList() {
    }

    @Override
    public ResponseBidListForList fromEntity(BidList bidList) {
        return new ResponseBidListForList(bidList.getId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }
}
