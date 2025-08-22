package app.utils;

import app.dao.DepartementDao;
import app.dao.VilleDao;
import app.entities.Departement;
import app.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Charge les données depuis le CSV pour peupler les tables Ville et Departement.
 * <p>
 * Le CSV doit être placé dans resources et être accessible via getResourceAsStream.
 */
@Component
public class CsvLoader {

    @Autowired
    private VilleDao villeDao;

    @Autowired
    private DepartementDao departementDao;

    /**
     * Méthode appelée après l'initialisation du bean Spring.
     * Elle charge le CSV et insère les données en base.
     */
    @PostConstruct
    public void loadCsv() {
        List<Ville> villes = villeDao.findAll();
        if (villes.size() > 0) {
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream("recensement.csv"),
                        StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            // Pour ne pas créer plusieurs fois le même département
            Map<String, Departement> departementsMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                if (firstLine) { // Ignorer l'en-tête
                    firstLine = false;
                    continue;
                }

                String[] tokens = line.split(";");
                if (tokens.length < 9) continue;

                // Colonnes CSV
                String codeRegion = tokens[0].trim();
                String nomRegion = tokens[1].trim();
                String codeDepartement = tokens[2].trim();
                String nomCommune = tokens[6].trim();
                int populationMunicipale = Integer.parseInt(tokens[7].replace(" ", ""));
                int populationTotale = Integer.parseInt(tokens[9].replace(" ", ""));

                // Créer ou récupérer le département
                Departement departement = departementsMap.get(codeDepartement);
                if (departement == null) {
                    departement = new Departement();
                    departement.setCode(codeDepartement);
                    departement.setNom("Département " + codeDepartement); // ou utiliser un vrai nom si disponible
                    departementDao.save(departement);
                    departementsMap.put(codeDepartement, departement);
                }

                // Créer la ville
                Ville ville = new Ville();
                ville.setNom(nomCommune);
                ville.setPopulationMunicipale(populationMunicipale);
                ville.setPopulationTotale(populationTotale);
                ville.setDepartement(departement);

                villeDao.save(ville);
            }

            System.out.println("CSV chargé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}