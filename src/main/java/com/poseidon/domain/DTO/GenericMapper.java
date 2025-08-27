package com.poseidon.domain.DTO;

import java.util.List;

public class GenericMapper {

    public static <ENTITY, DTO extends ConvertibleDtoFromEntity<ENTITY, DTO>> List<DTO> mapList(List<ENTITY> entities, DTO prototype) {
        return entities.stream()
                .map(prototype::fromEntity)
                .toList();
    }

    public static <ENTITY, DTO extends ConvertibleDtoFromEntity<ENTITY, DTO>> DTO mapOne(ENTITY entity, DTO prototype) {
        return prototype.fromEntity(entity);
    }
}
