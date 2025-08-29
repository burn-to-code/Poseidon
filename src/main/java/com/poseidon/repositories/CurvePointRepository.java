package com.poseidon.repositories;

import com.poseidon.domain.CurvePoint;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    Boolean existsByCurveId(@NotNull(message = "must not be null") Integer curveId);
}
