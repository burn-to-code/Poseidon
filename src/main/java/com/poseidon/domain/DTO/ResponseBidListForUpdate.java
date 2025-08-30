package com.poseidon.domain.DTO;

import com.poseidon.domain.BidList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseBidListForUpdate implements ConvertibleDtoFromEntity<BidList, ResponseBidListForUpdate> {
    private Integer bidListId;
    private String account;
    private String type;
    private Double bidQuantity;

    public ResponseBidListForUpdate() {
    }

    @Override
    public ResponseBidListForUpdate fromEntity(BidList bidList) {
        return new ResponseBidListForUpdate(bidList.getId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }
}
