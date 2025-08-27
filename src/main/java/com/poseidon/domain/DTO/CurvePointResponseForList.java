package com.poseidon.domain.DTO;

import com.poseidon.domain.CurvePoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CurvePointResponseForList implements ConvertibleDtoFromEntity<CurvePoint, CurvePointResponseForList> {

    private Integer id;

    private int curvePointId;

    private Double term;

    private Double value;

    public CurvePointResponseForList() {

    }

    @Override
    public CurvePointResponseForList fromEntity(CurvePoint curvePoint) {
        return new CurvePointResponseForList(curvePoint.getId(), curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue());
    }
}
