package app.dao;

import app.entities.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO pour la gestion des villes.
 * Utilise Spring Data JPA pour l'accès à la base.
 */
@Repository
public interface VilleDao extends JpaRepository<Ville, Long> {

    /**
     * Recherche une ville par son nom.
     *
     * @param nom nom de la ville
     * @return la ville ou {@code null} si non trouvée
     */
    Optional<Ville> findByNom(String nom);

    /**
     * Recherche une ville par son nom (ignore la casse).
     * @param nom nom de la ville
     * @return Optional de la ville
     */
    Optional<Ville> findByNomIgnoreCase(String nom);

    /**
     * Recherche toutes les villes dont le nom contient une chaîne (insensible à la casse).
     *
     * @param nom morceau de nom
     * @return liste de villes correspondantes
     */
    List<Ville> findByNomContainingIgnoreCase(String nom);
}