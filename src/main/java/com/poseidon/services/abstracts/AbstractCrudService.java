package com.poseidon.services.abstracts;


import com.poseidon.domain.BaseEntity;
import com.poseidon.services.interfaces.CrudInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
public abstract class AbstractCrudService<MODEL extends BaseEntity<MODEL>> implements CrudInterface<MODEL> {

    protected final JpaRepository<MODEL, Integer> repository;
    
    protected AbstractCrudService(JpaRepository<MODEL, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public MODEL getById(Integer id) {
        log.debug("Tentative de récupération de l'entité avec l'id {}", id);

        Assert.notNull(id, "Id must not be null");
        Assert.isTrue(id > 0, "Id must be greater than zero");

        MODEL model = repository.findById(id).orElseThrow(() -> {
            log.error("Aucune entité trouvée avec l'id {}", id);
            return new IllegalArgumentException("Entité introuvable avec id : " + id);
        });

        log.info("Entité avec l'id {} récupérée avec succès", id);
        return model;
    }

    @Override
    public List<MODEL> getAll() {
        log.debug("Récupération de toutes les entités");
        return repository.findAll();
    }

    @Override
    public MODEL save(MODEL model) {
        log.debug("Tentative de sauvegarde d'une nouvelle entité : {}", model);
        Assert.notNull(model, "Model must not be null");
        Assert.isNull(model.getId(), "Id must be null");

        MODEL saved = repository.save(model);

        log.info("Nouvelle entité sauvegardée avec succès : {}", saved);
        return saved;
    }

    @Override
    public void update(Integer id, MODEL model) {
        log.debug("Tentative de mise à jour de l'entité avec id {} : {}", id, model);

        Assert.notNull(model, "Model must not be null");
        Assert.notNull(id, "Id must not be null");
        Assert.isTrue(id > 0, "Id must be greater than zero");

        MODEL existingModel = getById(id);
        existingModel.update(model);

        repository.save(existingModel);

        log.info("Entité avec id {} mise à jour avec succès", id);
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("Tentative de suppression de l'entité avec id {}", id);

        Assert.notNull(id, "Id must not be null");
        Assert.isTrue(id > 0, "Id must be greater than zero");

        repository.deleteById(id);

        log.info("Entité avec id {} supprimée avec succès", id);
    }
    
}
