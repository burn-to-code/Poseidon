package com.poseidon.services;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponse;
import com.poseidon.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BidListService {

    private final BidListRepository bidListRepository;


    public List<BidListResponse> getBidList() {
        List<BidList> bidList = bidListRepository.findAll();

        return bidList.stream()
                .map(b -> new BidListResponse(b.getBidListId(), b.getAccount(), b.getType(), b.getBidQuantity())).toList();
    }
}
