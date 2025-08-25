package com.poseidon.services;

import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.DTO.CurvePointResponseForList;
import com.poseidon.domain.DTO.CurvePointResponseForUpdate;
import com.poseidon.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@AllArgsConstructor
public class CurvePointServiceImpl implements CurvePointServices{

    private final CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePointResponseForList> findAllForResponseList() {
        List<CurvePoint> curvePoints = curvePointRepository.findAll();

        return curvePoints.stream()
                .map(c -> new CurvePointResponseForList(c.getId(), c.getCurveId(), c.getTerm(), c.getValue())
                ).toList();
    }

    @Override
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        curvePoint.setCreationDate(new Date(System.currentTimeMillis()));
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePointResponseForUpdate getUpdateCurvePointById(Integer id) {
        assertNotNull(id, "Id Must Not Be Null:");
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));

        return new CurvePointResponseForUpdate(curvePoint.getId(), curvePoint.getTerm(), curvePoint.getValue());
    }

    @Override
    public CurvePoint updateCurvePointById(Integer id, CurvePoint curvePoint) {
        assertNotNull(id, "Id Must Not Be Null:");
        CurvePoint curve = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));

        curve.setTerm(curvePoint.getTerm());
        curve.setValue(curvePoint.getValue());

        return curvePointRepository.save(curve);
    }

    @Override
    public void deleteCurvePointById(Integer id) {
        assertNotNull(id, "Id Must Not Be Null:");

        CurvePoint cuverPoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));

        curvePointRepository.delete(cuverPoint);
    }
}
