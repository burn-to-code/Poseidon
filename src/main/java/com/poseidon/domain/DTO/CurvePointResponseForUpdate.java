package com.poseidon.domain.DTO;

import com.poseidon.domain.CurvePoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurvePointResponseForUpdate implements ConvertibleDtoFromEntity<CurvePoint, CurvePointResponseForUpdate> {

    private Integer id;
    private double term;
    private double value;

    public CurvePointResponseForUpdate() {
    }

    @Override
    public CurvePointResponseForUpdate fromEntity(CurvePoint curvePoint) {
        return new CurvePointResponseForUpdate(curvePoint.getId(), curvePoint.getTerm(), curvePoint.getValue());
    }
}
