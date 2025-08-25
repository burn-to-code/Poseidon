package com.poseidon.services;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@AllArgsConstructor
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    @Override
    public List<BidListResponseForList> getBidListForResponseList() {
        List<BidList> bidList = bidListRepository.findAll();

        return bidList.stream()
                .map(b -> new BidListResponseForList(b.getBidListId(), b.getAccount(), b.getType(), b.getBidQuantity())).toList();
    }

    @Override
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

    @Override
    public BidListResponseForUpdate getBidListByIdForResponse(Integer id) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));

        return new BidListResponseForUpdate(bidList.getBidListId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
    }

    @Override
    public BidList updateBidListById(Integer id, BidListResponseForUpdate bidList) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bid.setAccount(bidList.getAccount());
        bid.setType(bidList.getType());
        bid.setBidQuantity(bidList.getBidQuantity());
        return bidListRepository.save(bid);
    }

    @Override
    public void deleteBidListById(Integer id) {
        assertNotNull(id, "Id Must Not Be Null");

        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));

        bidListRepository.delete(bid);
    }
}
