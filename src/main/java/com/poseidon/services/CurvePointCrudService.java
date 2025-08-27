package com.poseidon.services;

import com.poseidon.domain.CurvePoint;
import com.poseidon.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

@Service
public class CurvePointCrudService extends AbstractCrudService<CurvePoint> {

    protected CurvePointCrudService(CurvePointRepository repository) {
        super(repository);
    }
}
