package com.poseidon.services;

import java.util.List;

public interface DTOInterface<LIST_DTO, UPDATE_DTO> {

    List<LIST_DTO> getAllForList();

    UPDATE_DTO toDTOForUpdate(Integer id);
}
