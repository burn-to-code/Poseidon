package com.poseidon.services.abstracts;

import com.poseidon.domain.BaseEntity;
import com.poseidon.services.interfaces.DTOInterface;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractCrudDtoService<ENTITY extends BaseEntity<ENTITY>, LIST_DTO, UPDATE_DTO> extends AbstractCrudService<ENTITY> implements DTOInterface<LIST_DTO, UPDATE_DTO> {

    protected AbstractCrudDtoService(JpaRepository<ENTITY, Integer> repository) {
        super(repository);
    }

    protected abstract LIST_DTO toListDto(ENTITY entity);

    protected abstract UPDATE_DTO toUpdateDto(ENTITY entity);

    @Override
    public List<LIST_DTO> getAllForList() {
        return getAll().stream()
                .map(this::toListDto)
                .toList();
    }

    @Override
    public UPDATE_DTO toDTOForUpdate(Integer id) {
        if(id == null) throw new IllegalArgumentException("Model must not be null");
        if(id < 0) throw new IllegalArgumentException("Id must be greater than zero");
        ENTITY model = getById(id);
        return toUpdateDto(model);
    }

}
