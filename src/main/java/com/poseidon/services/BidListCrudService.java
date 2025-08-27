package com.poseidon.services;

import com.poseidon.domain.BidList;
import com.poseidon.domain.DTO.BidListResponseForList;
import com.poseidon.domain.DTO.BidListResponseForUpdate;
import com.poseidon.repositories.BidListRepository;
import com.poseidon.services.abstracts.AbstractCrudDtoService;
import org.springframework.stereotype.Service;

@Service
public class BidListCrudService extends AbstractCrudDtoService<BidList, BidListResponseForList, BidListResponseForUpdate> {


    protected BidListCrudService(BidListRepository repository) {
        super(repository);
    }

    @Override
    protected BidListResponseForList toListDto(BidList entity) {
        return new BidListResponseForList(entity.getId(), entity.getAccount(), entity.getType(), entity.getBidQuantity());
    }

    @Override
    protected BidListResponseForUpdate toUpdateDto(BidList entity) {
        return new BidListResponseForUpdate(entity.getId(), entity.getAccount(), entity.getType(), entity.getBidQuantity());
    }
}
