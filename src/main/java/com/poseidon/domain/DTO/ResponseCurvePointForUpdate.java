package com.poseidon.domain.DTO;

import com.poseidon.domain.CurvePoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseCurvePointForUpdate implements ConvertibleDtoFromEntity<CurvePoint, ResponseCurvePointForUpdate> {

    private Integer id;
    private int curveId;
    private double term;
    private double value;

    public ResponseCurvePointForUpdate() {
    }

    @Override
    public ResponseCurvePointForUpdate fromEntity(CurvePoint curvePoint) {
        return new ResponseCurvePointForUpdate(curvePoint.getId(), curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue());
    }
}
