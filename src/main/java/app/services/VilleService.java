package app.services;

import app.dao.VilleDao;
import app.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des villes.
 */
@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;

    /**
     * Extrait toutes les villes.
     * @return liste de toutes les villes
     */
    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    /**
     * Extrait une ville par son identifiant.
     * @param id identifiant de la ville
     * @return la ville ou {@code null} si non trouvée
     */
    public Ville extractVilleById(Long id) {
        Optional<Ville> opt = villeDao.findById(id);
        return opt.orElse(null);
    }

    /**
     * Retourne une ville par son nom.
     * @param nom nom de la ville
     * @return Ville ou null si non trouvée
     */
    public Ville extractVilleByNom(String nom) {
        return villeDao.findByNomIgnoreCase(nom).orElse(null);
    }

    /**
     * Insère une ville en base.
     * @param ville Ville à insérer
     * @return Ville sauvegardée
     */
    public Ville insertVille(Ville ville) {
        return villeDao.save(ville);
    }

    /**
     * Modifie une ville existante.
     * @param id identifiant de la ville à modifier
     * @param villeModifiee données modifiées
     * @return la ville mise à jour ou {@code null} si non trouvée
     */
    public Ville updateVille(Long id, Ville villeModifiee) {
        Optional<Ville> opt = villeDao.findById(id);
        if (opt.isPresent()) {
            Ville ville = opt.get();
            ville.setNom(villeModifiee.getNom());
            ville.setPopulationMunicipale(villeModifiee.getPopulationMunicipale());
            ville.setPopulationTotale(villeModifiee.getPopulationTotale());
            ville.setDepartement(villeModifiee.getDepartement());
            return villeDao.save(ville);
        }
        return null;
    }

    /**
     * Supprime une ville par son identifiant.
     * @param id identifiant de la ville à supprimer
     * @return true si suppression effectuée, false sinon
     */
    public boolean deleteVille(Long id) {
        if (villeDao.existsById(id)) {
            villeDao.deleteById(id);
            return true;
        }
        return false;
    }
}