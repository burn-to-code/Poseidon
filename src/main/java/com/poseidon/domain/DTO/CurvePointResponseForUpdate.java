package com.poseidon.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurvePointResponseForUpdate {

    private Integer id;
    private double term;
    private double value;

}
