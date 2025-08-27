package com.poseidon.services;


import com.poseidon.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;

public class AbstractCrudService<MODEL extends BaseEntity<MODEL>> implements CrudInterface<MODEL> {

    protected final JpaRepository<MODEL, Integer> repository;
    
    protected AbstractCrudService(JpaRepository<MODEL, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public MODEL getById(Integer id) {
        Assert.notNull(id, "Id must not be null");
        Assert.isTrue(id > 0, "Id must be greater than zero");
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<MODEL> getAll() {
        return repository.findAll();
    }

    @Override
    public MODEL save(MODEL model) {
        Assert.notNull(model, "Model must not be null");
        Assert.isNull(model.getId(), "Id must be null");

        return repository.save(model);
    }

    @Override
    public void update(Integer id, MODEL model) {
        Assert.notNull(model, "Model must not be null");
        Assert.notNull(id, "Id must not be null");
        Assert.isTrue(id > 0, "Id must be greater than zero");

        MODEL existingModel = getById(id);

        existingModel.update(model);

        repository.save(existingModel);

    }

    @Override
    public void deleteById(Integer id) {
        Assert.notNull(id, "Id must not be null");
        Assert.isTrue(id > 0, "Id must be greater than zero");

        repository.deleteById(id);
    }
    
}
