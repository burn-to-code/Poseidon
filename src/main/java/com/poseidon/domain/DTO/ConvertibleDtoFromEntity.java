package com.poseidon.domain.DTO;

public interface ConvertibleDtoFromEntity<ENTITY, DTO> {

    DTO fromEntity(ENTITY entity);
}
