package com.poseidon.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CurvePointResponseForList {

    private Integer id;

    private int curvePointId;

    private Double term;

    private Double value;
}
