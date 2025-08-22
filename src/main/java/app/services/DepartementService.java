package app.services;

import app.dao.DepartementDao;
import app.dto.DepartementDto;
import app.dto.DepartementMapper;
import app.entities.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des départements.
 */
@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    /** Extrait tous les départements */
    public List<DepartementDto> getAllDepartements() {
        return departementDao.findAll().stream()
                .map(DepartementMapper::toDto)
                .collect(Collectors.toList());
    }

    /** Extrait un département par son identifiant */
    public Departement extractDepartement(Long id) {
        Optional<Departement> opt = departementDao.findById(id);
        return opt.orElse(null);
    }

    /** Extrait un département par son code */
    public Departement extractDepartementByCode(String code) {
        return departementDao.findByCode(code).orElse(null);
    }

    /** Crée un nouveau département */
    public Departement insertDepartement(Departement dep) {
        return departementDao.save(dep);
    }

    /** Modifie un département existant */
    public Departement modifierDepartement(Long id, Departement depModifie) {
        Optional<Departement> opt = departementDao.findById(id);
        if (opt.isPresent()) {
            Departement dep = opt.get();
            dep.setCode(depModifie.getCode());
            dep.setNom(depModifie.getNom());
            return departementDao.save(dep);
        }
        return null;
    }

    /** Supprime un département par ID */
    public boolean deleteDepartement(Long id) {
        if (departementDao.existsById(id)) {
            departementDao.deleteById(id);
            return true;
        }
        return false;
    }
}