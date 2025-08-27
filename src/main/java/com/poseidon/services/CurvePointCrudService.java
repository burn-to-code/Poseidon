package com.poseidon.services;

import com.poseidon.domain.CurvePoint;
import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.services.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CurvePointCrudService extends AbstractCrudService<CurvePoint> {

    public CurvePointCrudService(CurvePointRepository repository) {
        super(repository);
    }

}
