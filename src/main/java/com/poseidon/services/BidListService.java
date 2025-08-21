package com.poseidon.services;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;

import java.util.List;

public interface BidListService {

    List<BidListResponseForList> getBidListForResponseList();

    BidList saveBidList(BidList bidList);

    BidListResponseForUpdate getBidListByIdForResponse(Integer bidListId);

    BidList updateBidListById(Integer bidListId, BidListResponseForUpdate bidList);

    void deleteBidListById(Integer id);
}
