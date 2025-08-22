package app.dto;

import app.entities.Ville;
import app.entities.Departement;

/**
 * Mapper pour convertir entre Ville et VilleDto.
 */
public class VilleMapper {

    /**
     * Convertit une entité Ville en VilleDto.
     * @param ville la ville à convertir
     * @return le DTO correspondant
     */
    public static VilleDto toDto(Ville ville) {
        if (ville == null) return null;
        Long codeDep = ville.getDepartement() != null ? ville.getDepartement().getId() : null;
        return new VilleDto(
                ville.getId(),
                ville.getNom(),
                ville.getPopulationMunicipale(),
                ville.getPopulationTotale(),
                codeDep
        );
    }

    /**
     * Convertit un VilleDto en entité Ville.
     * @param dto le DTO
     * @param departement le département associé à la ville
     * @return l'entité Ville
     */
    public static Ville toEntity(VilleDto dto, Departement departement) {
        if (dto == null) return null;
        Ville ville = new Ville();
        ville.setId(dto.getId());
        ville.setNom(dto.getNom());
        ville.setPopulationMunicipale(dto.getPopulationMunicipale());
        ville.setPopulationTotale(dto.getPopulationTotale());
        ville.setDepartement(departement);
        return ville;
    }
}