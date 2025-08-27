package com.poseidon.services;

import com.poseidon.domain.BaseEntity;

import java.util.List;

public interface CrudInterface<MODEL extends BaseEntity<MODEL>> {

    MODEL getById(Integer id);

    List<MODEL> getAll();

    MODEL save(MODEL model);

    void update(Integer id, MODEL model);

    void deleteById(Integer id);

}
