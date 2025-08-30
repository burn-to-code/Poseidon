package com.poseidon.services.abstracts;


import com.poseidon.domain.BaseEntity;
import com.poseidon.services.interfaces.CrudInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Classe abstraite fournissant une implémentation générique des opérations CRUD pour les entités.
 * <p>
 * Cette classe est destinée à être étendue par des services spécifiques à une entité.
 * Elle centralise la logique commune de gestion des entités persistantes via un {@link JpaRepository}.
 * </p>
 *
 * <p>
 * Paramètre générique :
 * <ul>
 *     <li>{@code MODEL} : type de l'entité gérée, qui doit étendre {@link BaseEntity}.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Cette classe implémente l'interface {@link CrudInterface}, fournissant les méthodes suivantes :
 * <ul>
 *     <li>{@link #getById(Integer)} : récupère une entité par son identifiant.</li>
 *     <li>{@link #getAll()} : récupère toutes les entités.</li>
 *     <li>{@link #save(BaseEntity)} : sauvegarde une nouvelle entité.</li>
 *     <li>{@link #update(Integer, BaseEntity)} : met à jour une entité existante.</li>
 *     <li>{@link #deleteById(Integer)} : supprime une entité par son identifiant.</li>
 * </ul>
 * </p>
 *
 * <p>
 * La classe utilise {@link org.springframework.util.Assert} pour valider les paramètres et {@link lombok.extern.slf4j.Slf4j} pour la journalisation.
 * </p>
 *
 * @param <MODEL> Le type d'entité géré par ce service.
 */
@Slf4j
public abstract class AbstractCrudService<MODEL extends BaseEntity<MODEL>> implements CrudInterface<MODEL> {

    protected final JpaRepository<MODEL, Integer> repository;


    /**
     * Constructeur de service CRUD générique.
     *
     * @param repository Le repository JPA utilisé pour accéder aux entités.
     */
    protected AbstractCrudService(JpaRepository<MODEL, Integer> repository) {
        this.repository = repository;
    }

    /**
     * Récupère une entité par son identifiant.
     *
     * @param id L'identifiant de l'entité à récupérer. Doit être non nul et supérieur à zéro.
     * @return L'entité correspondante.
     * @throws IllegalArgumentException si l'entité n'existe pas ou si l'identifiant est invalide.
     */
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

    /**
     * Récupère toutes les entités de ce type.
     *
     * @return Une liste contenant toutes les entités.
     */
    @Override
    public List<MODEL> getAll() {
        log.debug("Récupération de toutes les entités");
        return repository.findAll();
    }

    /**
     * Sauvegarde une nouvelle entité.
     *
     * @param model L'entité à sauvegarder. L'identifiant doit être nul pour indiquer qu'il s'agit d'une création.
     * @return L'entité sauvegardée avec son identifiant généré.
     * @throws IllegalArgumentException si le modèle est nul ou si l'identifiant n'est pas nul.
     */
    @Override
    public MODEL save(MODEL model) {
        log.debug("Tentative de sauvegarde d'une nouvelle entité : {}", model);
        Assert.notNull(model, "Model must not be null");
        Assert.isNull(model.getId(), "Id must be null");

        MODEL saved = repository.save(model);

        log.info("Nouvelle entité sauvegardée avec succès : {}", saved);
        return saved;
    }

    /**
     * Met à jour une entité existante.
     *
     * @param id    L'identifiant de l'entité à mettre à jour.
     * @param model L'entité contenant les nouvelles données. Doit être non nulle.
     * @throws IllegalArgumentException si l'entité n'existe pas ou si les paramètres sont invalides.
     */
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

    /**
     * Supprime une entité par son identifiant.
     *
     * @param id L'identifiant de l'entité à supprimer. Doit être non nul et supérieur à zéro.
     */
    @Override
    public void deleteById(Integer id) {
        log.debug("Tentative de suppression de l'entité avec id {}", id);

        Assert.notNull(id, "Id must not be null");
        Assert.isTrue(id > 0, "Id must be greater than zero");

        repository.deleteById(id);

        log.info("Entité avec id {} supprimée avec succès", id);
    }
    
}
