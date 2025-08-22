package app.controllers;

import app.dto.DepartementDto;
import app.dto.DepartementMapper;
import app.dto.VilleDto;
import app.dto.VilleMapper;
import app.entities.Departement;
import app.entities.Ville;
import app.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour les départements.
 */
@RestController
@RequestMapping("/departements")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    /** Liste tous les départements */
    @GetMapping
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        List<DepartementDto> dtos = departementService.getAllDepartements();
        return ResponseEntity.ok(dtos);
    }

    /** Retourne un département par ID */
    @GetMapping("/{id}")
    public ResponseEntity<DepartementDto> getDepartementById(@PathVariable Long id) {
        Departement dep = departementService.extractDepartement(id);
        if (dep == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DepartementMapper.toDto(dep));
    }

    /** Retourne un département par code */
    @GetMapping("/code/{code}")
    public ResponseEntity<DepartementDto> getDepartementByCode(@PathVariable String code) {
        Departement dep = departementService.extractDepartementByCode(code);
        if (dep == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DepartementMapper.toDto(dep));
    }

    /** Crée un département */
    @PostMapping
    public ResponseEntity<DepartementDto> createDepartement(@RequestBody DepartementDto dto) {
        Departement dep = DepartementMapper.toEntity(dto);
        Departement saved = departementService.insertDepartement(dep);
        return ResponseEntity.ok(DepartementMapper.toDto(saved));
    }

    /** Modifie un département */
    @PutMapping("/{id}")
    public ResponseEntity<DepartementDto> updateDepartement(@PathVariable Long id, @RequestBody DepartementDto dto) {
        Departement depModifie = DepartementMapper.toEntity(dto);
        Departement updated = departementService.modifierDepartement(id, depModifie);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DepartementMapper.toDto(updated));
    }

    /** Supprime un département */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable Long id) {
        boolean deleted = departementService.deleteDepartement(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Top N villes d’un département (par populationTotale décroissante).
     * Ex: GET /departements/01/villes/top/5
     */
    @GetMapping("/{code}/villes/top/{n}")
    public ResponseEntity<List<VilleDto>> getTopNVilles(
            @PathVariable String code,
            @PathVariable int n) {

        Departement dep = departementService.extractDepartementByCode(code);
        if (dep == null) return ResponseEntity.notFound().build();

        List<Ville> villes = departementService.getTopNVilles(code, n);
        List<VilleDto> dtos = villes.stream().map(VilleMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Villes d’un département dont la populationTotale est comprise entre min et max.
     * Ex: GET /departements/01/villes/population?min=1000&max=5000
     */
    @GetMapping("/{code}/villes/population")
    public ResponseEntity<List<VilleDto>> getVillesByPopulation(
            @PathVariable String code,
            @RequestParam int min,
            @RequestParam int max) {

        Departement dep = departementService.extractDepartementByCode(code);
        if (dep == null) return ResponseEntity.notFound().build();

        List<Ville> villes = departementService.getVillesByPopulation(code, min, max);
        List<VilleDto> dtos = villes.stream().map(VilleMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }
}