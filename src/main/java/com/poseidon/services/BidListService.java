package com.poseidon.services;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BidListService {

    private final BidListRepository bidListRepository;


    public List<BidListResponseForList> getBidListForResponseList() {
        List<BidList> bidList = bidListRepository.findAll();

        return bidList.stream()
                .map(b -> new BidListResponseForList(b.getBidListId(), b.getAccount(), b.getType(), b.getBidQuantity())).toList();
    }

    public BidList saveBidList(BidList bidList) {
        if (bidList.getBidListId() == null) {
            bidList.setCreationDate(new java.util.Date());
            bidList.setCreationName("admin");
        } else {
            bidList.setRevisionDate(new java.util.Date());
            bidList.setRevisionName("admin");
        }

        return bidListRepository.save(bidList);
    }

    public BidListResponseForUpdate getBidListByIdForResponse(Integer id) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));

        return new BidListResponseForUpdate(bidList.getBidListId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }

    public BidList updateBidListById(Integer id, BidListResponseForUpdate bidList) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bid.setAccount(bidList.getAccount());
        bid.setType(bidList.getType());
        bid.setBidQuantity(bidList.getBidQuantity());
        return bidListRepository.save(bid);
    }

    public void deleteBidListById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
