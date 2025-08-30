package com.poseidon.domain.DTO;

import com.poseidon.domain.CurvePoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseCurvePointForList implements ConvertibleDtoFromEntity<CurvePoint, ResponseCurvePointForList> {

    private Integer id;

    private Integer curvePointId;

    private Double term;

    private Double value;

    public ResponseCurvePointForList() {

    }

    @Override
    public ResponseCurvePointForList fromEntity(CurvePoint curvePoint) {
        return new ResponseCurvePointForList(curvePoint.getId(), curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue());
    }
}
