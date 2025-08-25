package com.poseidon.services;

import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.DTO.CurvePointResponseForList;
import com.poseidon.domain.DTO.CurvePointResponseForUpdate;

import java.util.List;

public interface CurvePointServices {

    List<CurvePointResponseForList> findAllForResponseList();

    CurvePoint saveCurvePoint(CurvePoint curvePoint);

    CurvePointResponseForUpdate getUpdateCurvePointById(Integer id);

    CurvePoint updateCurvePointById(Integer id, CurvePoint curvePoint);

    void deleteCurvePointById(Integer id);
}
