package com.poseidon.domain.DTO;

import com.poseidon.domain.BidList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BidListResponseForUpdate implements ConvertibleDtoFromEntity<BidList, BidListResponseForUpdate> {
    private Integer bidListId;
    private String account;
    private String type;
    private Double bidQuantity;

    public BidListResponseForUpdate() {
    }

    @Override
    public BidListResponseForUpdate fromEntity(BidList bidList) {
        return new BidListResponseForUpdate(bidList.getId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }
}
