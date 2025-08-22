package app.controllers;

import app.dto.VilleDto;
import app.dto.VilleMapper;
import app.entities.Ville;
import app.entities.Departement;
import app.services.VilleService;
import app.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour les villes.
 */
@RestController
@RequestMapping("/villes")
public class VilleController {

    @Autowired
    private VilleService villeService;

    @Autowired
    private DepartementService departementService;

    /** Liste toutes les villes */
    @GetMapping
    public ResponseEntity<List<VilleDto>> getAllVilles() {
        List<VilleDto> dtos = villeService.extractVilles().stream()
                .map(VilleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /** Retourne une ville par ID */
    @GetMapping("/{id}")
    public ResponseEntity<VilleDto> getVilleById(@PathVariable Long id) {
        Ville ville = villeService.extractVilleById(id);
        if (ville == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(VilleMapper.toDto(ville));
    }

    /** Retourne une ville par nom */
    @GetMapping("/nom/{nom}")
    public ResponseEntity<VilleDto> getVilleByNom(@PathVariable String nom) {
        Ville ville = villeService.extractVilleByNom(nom);
        if (ville == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(VilleMapper.toDto(ville));
    }

    /** Crée une nouvelle ville */
    @PostMapping
    public ResponseEntity<VilleDto> createVille(@RequestBody VilleDto dto) {
        Departement dep = departementService.extractDepartement(dto.getCodeDepartement());
        if (dep == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Ville ville = VilleMapper.toEntity(dto, dep);
        Ville saved = villeService.insertVille(ville);
        return ResponseEntity.ok(VilleMapper.toDto(saved));
    }

    /** Modifie une ville existante */
    @PutMapping("/{id}")
    public ResponseEntity<VilleDto> updateVille(@PathVariable Long id, @RequestBody VilleDto dto) {
        Ville existingVille = villeService.extractVilleById(id);
        if (existingVille == null) return ResponseEntity.notFound().build();

        Departement dep = departementService.extractDepartement(dto.getCodeDepartement());
        if (dep == null) return ResponseEntity.badRequest().build();

        Ville updatedVille = VilleMapper.toEntity(dto, dep);
        updatedVille.setId(id);
        villeService.updateVille(id, updatedVille);

        return ResponseEntity.ok(VilleMapper.toDto(updatedVille));
    }

    /** Supprime une ville */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVille(@PathVariable Long id) {
        boolean deleted = villeService.deleteVille(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}