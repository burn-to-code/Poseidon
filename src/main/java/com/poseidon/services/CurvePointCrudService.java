package com.poseidon.services;

import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.DTO.CurvePointResponseForList;
import com.poseidon.domain.DTO.CurvePointResponseForUpdate;
import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.services.abstracts.AbstractCrudDtoService;
import org.springframework.stereotype.Service;

@Service
public class CurvePointCrudService extends AbstractCrudDtoService<CurvePoint, CurvePointResponseForList, CurvePointResponseForUpdate> {

    protected CurvePointCrudService(CurvePointRepository repository) {
        super(repository);
    }

    @Override
    protected CurvePointResponseForList toListDto(CurvePoint entity) {
        return new CurvePointResponseForList(entity.getId(), entity.getCurveId(), entity.getTerm(), entity.getValue());
    }

    @Override
    protected CurvePointResponseForUpdate toUpdateDto(CurvePoint entity) {
        return new CurvePointResponseForUpdate(entity.getId(), entity.getTerm(), entity.getValue());
    }
}
