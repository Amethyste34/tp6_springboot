package app.dao;

import app.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface DAO pour l'accès aux départements.
 * <p>
 * Elle étend {@link JpaRepository} pour bénéficier des méthodes CRUD standard.
 * Permet également de rechercher un département par son code.
 * </p>
 */
public interface DepartementDao extends JpaRepository<Departement, Long> {

    /**
     * Recherche un département par son code unique.
     * @param code code du département
     * @return le département correspondant ou null si non trouvé
     */
    Optional<Departement> findByCode(String code);
}