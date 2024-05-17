import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;

import fr.univrennes.istic.l2gen.visustats.DiagCamemberts;
import fr.univrennes.istic.l2gen.visustats.DiagColonnes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Cette classe permet de télécharger des données sur les prix des carburants en
 * France depuis une API externe
 * Elle utilise HttpURLConnection pour effectuer une requête HTTP GET et
 * récupérer les données au format JSON
 */

public class API extends UI {

    // ATTRIBUTS DE LA CLASSE

    private static List<Double> averagePrices = new ArrayList<>();
    private static List<Double> medianPrices = new ArrayList<>();
    private static List<Double> minPrices = new ArrayList<>();
    private static Integer totalCountStations;
    private static int nombreTotalStationServices = 0;
    private static int nombreTotalStations = 0;
    private static int nombreTotalStationCarburants = 0;

    public static void setNombreTotalStationCarburants(int nombreTotalStationCarburants) {
        API.nombreTotalStationCarburants = nombreTotalStationCarburants;
    }

    private static int nombreTotalStations0Carbs = 0;
    private static List<String> departements = new ArrayList<String>();
    private static ArrayList<Integer> nombreTotalStationsCabrs = new ArrayList<>();
    private static final String API_URL = "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?";

    // SETTERS AND GETTERS

    public static int getNombreTotalStationServices() {
        return nombreTotalStationServices;
    }

    public static int getNombreTotalStationCarburants() {
        return nombreTotalStationCarburants;
    }

    public static List<Double> getAveragePrices() {
        return averagePrices;
    }

    public static void setAveragePrices(List<Double> averagePrices) {
        API.averagePrices = averagePrices;
    }

    public static List<Double> getMedianPrices() {
        return medianPrices;
    }

    public static void setMedianPrices(List<Double> medianPrices) {
        API.medianPrices = medianPrices;
    }

    public static List<Double> getMinPrices() {
        return minPrices;
    }

    public static void setMinPrices(List<Double> minPrices) {
        API.minPrices = minPrices;
    }

    public static int getTotalCountStations() {
        return totalCountStations;
    }

    public static void setTotalCountStations(int totalCountStations) {
        API.totalCountStations = totalCountStations;
    }

    // METHODES

    /**
     * Récupère les données sur le carburant en fonction des critères de
     * localisation spécifiés.
     *
     * @param criteria les critères de sélection basés sur la localisation
     */
    public static void retrieveFuelDataByLocation(Map<String, List<String>> criteria) {
        try {
            String apiUrl = buildApiUrl(criteria);
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                if (criteria.containsKey("departement")) {

                    if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix moyen")
                            && criteria.get("departement").size() == 1) {
                        // Analyser la réponse JSON
                        String jsonString = response.toString();
                        extractAndPrintAveragePrices(jsonString, criteria);
                    } else if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix moyen")
                            && criteria.get("departement").size() > 1) {
                        // Itérer sur chaque département sélectionné
                        departements = new ArrayList<>(criteria.get("departement"));
                        for (String departement : departements) {
                            departement = departement.trim(); // Supprimer les espaces en début et fin de chaîne
                            // Créer une copie des critères avec un seul département
                            Map<String, List<String>> singleDepartementCriteria = new HashMap<>();
                            // Copier les autres clés et valeurs de criteria
                            for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
                                if (!entry.getKey().equals("departement")) {
                                    singleDepartementCriteria.put(entry.getKey(), entry.getValue());
                                }
                            }
                            singleDepartementCriteria.put("departement", Collections.singletonList(departement));
                            retrieveFuelDataByLocation(singleDepartementCriteria);
                            diagBarreAvgPrice();
                        }
                    }

                    if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix median")
                            && criteria.get("departement").size() == 1) {
                        String jsonString = response.toString();
                        extractAndPrintMedianPrices(jsonString, criteria);
                    } else if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix median")
                            && criteria.get("departement").size() > 1) {
                        // Itérer sur chaque département sélectionné
                        departements = new ArrayList<>(criteria.get("departement"));
                        for (String departement : departements) {
                            departement = departement.trim(); // Supprimer les espaces en début et fin de chaîne
                            // Créer une copie des critères avec un seul département
                            Map<String, List<String>> singleDepartementCriteria1 = new HashMap<>();
                            // Copier les autres clés et valeurs de criteria
                            for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
                                if (!entry.getKey().equals("departement")) {
                                    singleDepartementCriteria1.put(entry.getKey(), entry.getValue());
                                }
                            }
                            singleDepartementCriteria1.put("departement", Collections.singletonList(departement));
                            retrieveFuelDataByLocation(singleDepartementCriteria1);
                            diagBarreMedPrice();
                        }
                    }

                    if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix minimum")
                            && criteria.get("departement").size() == 1) {
                        String jsonString = response.toString();
                        extractAndPrintMinPrices(jsonString, criteria);
                    } else if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix minimum")
                            && criteria.get("departement").size() > 1) {
                        departements = new ArrayList<>(criteria.get("departement"));
                        // Itérer sur chaque département sélectionné
                        for (String departement : departements) {
                            departement = departement.trim(); // Supprimer les espaces en début et fin de chaîne
                            // Créer une copie des critères avec un seul département
                            Map<String, List<String>> singleDepartementCriteria1 = new HashMap<>();
                            // Copier les autres clés et valeurs de criteria
                            for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
                                if (!entry.getKey().equals("departement")) {
                                    singleDepartementCriteria1.put(entry.getKey(), entry.getValue());
                                }
                            }
                            singleDepartementCriteria1.put("departement", Collections.singletonList(departement));
                            retrieveFuelDataByLocation(singleDepartementCriteria1);
                            diagBarreMinPrice();
                        }
                    }
                }

                if (criteria.containsKey("region")) {

                    if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix moyen")
                            && criteria.get("region").size() == 1) {
                        // Analyser la réponse JSON
                        String jsonString = response.toString();
                        extractAndPrintAveragePrices(jsonString, criteria);
                    } else if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix moyen")
                            && criteria.get("region").size() > 1) {
                        // Itérer sur chaque département sélectionné
                        List<String> regions = new ArrayList<>(criteria.get("region"));
                        for (String region : regions) {
                            region = region.trim(); // Supprimer les espaces en début et fin de chaîne
                            // Créer une copie des critères avec un seul département
                            Map<String, List<String>> singleRegionCriteria = new HashMap<>();
                            // Copier les autres clés et valeurs de criteria
                            for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
                                if (!entry.getKey().equals("departement")) {
                                    singleRegionCriteria.put(entry.getKey(), entry.getValue());
                                }
                            }
                            singleRegionCriteria.put("region", Collections.singletonList(region));
                            retrieveFuelDataByLocation(singleRegionCriteria);
                        }
                    }

                    if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix median")
                            && criteria.get("region").size() == 1) {
                        String jsonString = response.toString();
                        extractAndPrintMedianPrices(jsonString, criteria);
                    } else if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix median")
                            && criteria.get("region").size() > 1) {
                        // Itérer sur chaque département sélectionné
                        List<String> regions = new ArrayList<>(criteria.get("region"));
                        for (String region : regions) {
                            region = region.trim(); // Supprimer les espaces en début et fin de chaîne
                            // Créer une copie des critères avec un seul département
                            Map<String, List<String>> singleRegionCriteria1 = new HashMap<>();
                            // Copier les autres clés et valeurs de criteria
                            for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
                                if (!entry.getKey().equals("departement")) {
                                    singleRegionCriteria1.put(entry.getKey(), entry.getValue());
                                }
                            }
                            singleRegionCriteria1.put("region", Collections.singletonList(region));
                            retrieveFuelDataByLocation(singleRegionCriteria1);
                        }
                    }

                    if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix minimum")
                            && criteria.get("region").size() == 1) {
                        String jsonString = response.toString();
                        extractAndPrintMinPrices(jsonString, criteria);
                    } else if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix minimum")
                            && criteria.get("region").size() > 1) {
                        List<String> regions = new ArrayList<>(criteria.get("region"));
                        // Itérer sur chaque département sélectionné
                        for (String region : regions) {
                            region = region.trim(); // Supprimer les espaces en début et fin de chaîne
                            // Créer une copie des critères avec un seul département
                            Map<String, List<String>> singleRegionCriteria1 = new HashMap<>();
                            // Copier les autres clés et valeurs de criteria
                            for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
                                if (!entry.getKey().equals("region")) {
                                    singleRegionCriteria1.put(entry.getKey(), entry.getValue());
                                }
                            }
                            singleRegionCriteria1.put("region", Collections.singletonList(region));
                            retrieveFuelDataByLocation(singleRegionCriteria1);
                        }
                    }
                }

                if (criteria.containsKey("filtre") && criteria.get("filtre")
                        .contains("Nombre de stations qui proposent chaque type de carburant")) {
                    int totalCount = countTotalStations(response.toString());
                    System.out.println("Nombre total de stations ayant tous les carburants: " + totalCount);
                    nombreTotalStationCarburants = totalCount;
                    camembertCarburants();
                    camembertAllCarburants();
                }

                if (criteria.containsKey("filtre") && criteria.get("filtre")
                        .contains("Nombre de stations qui proposent des services spécifiques")) {
                    int totalCount = countTotalStations(response.toString());
                    nombreTotalStationServices = totalCount;
                    camembertService();
                    System.out.println("Nombre total de stations ayant au moins un service : " + totalCount);
                }

            } else {
                System.out.println("La requête a échoué avec le code : " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Construit l'URL de l'API en fonction des critères spécifiés
     *
     * @param criteria les critères de sélection
     * @return l'URL de l'API construite
     */
    private static String buildApiUrl(Map<String, List<String>> criteria) {
        StringBuilder apiUrlBuilder = new StringBuilder(API_URL);

        // Ajouter le paramètre "select" si nécessaire
        boolean isPrixSelected = false;
        boolean isMedianPrixSelected = false;
        boolean isMinPrixSelected = false;
        boolean isNbreStationsCarburantSelected = false;
        boolean isNbreStationsServiceSelected = false;

        if (criteria.containsKey("filtre")) {
            List<String> filtres = criteria.get("filtre");
            isPrixSelected = filtres.contains("Prix moyen");
            isMedianPrixSelected = filtres.contains("Prix median");
            isMinPrixSelected = filtres.contains("Prix minimum");
            isNbreStationsCarburantSelected = filtres
                    .contains("Nombre de stations qui proposent chaque type de carburant");
            isNbreStationsServiceSelected = filtres
                    .contains("Nombre de stations qui proposent des services spécifiques");

        }
        try {
            if (isPrixSelected) {
                apiUrlBuilder.append("select=");
                List<String> selectedCarburants = criteria.get("carburant");
                if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                    for (String carburant : selectedCarburants) {
                        String encodedCarburant = URLEncoder.encode(carburant.trim().toLowerCase(), "UTF-8");
                        apiUrlBuilder.append("avg(").append(encodedCarburant).append("_prix)%2C");
                    }
                    // Supprime le dernier caractère ","
                    apiUrlBuilder.delete(apiUrlBuilder.length() - 3, apiUrlBuilder.length());
                }
            }

            if (isMedianPrixSelected) {
                apiUrlBuilder.append("select=");
                List<String> selectedCarburants = criteria.get("carburant");
                if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                    for (String carburant : selectedCarburants) {
                        String encodedCarburant = URLEncoder.encode(carburant.trim().toLowerCase(), "UTF-8");
                        apiUrlBuilder.append("median(").append(encodedCarburant).append("_prix)%2C");
                    }
                    // Supprime le dernier caractère ","
                    apiUrlBuilder.delete(apiUrlBuilder.length() - 3, apiUrlBuilder.length());
                }
            }

            if (isMinPrixSelected) {
                apiUrlBuilder.append("select=");
                List<String> selectedCarburants = criteria.get("carburant");
                if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                    for (String carburant : selectedCarburants) {
                        String encodedCarburant = URLEncoder.encode(carburant.trim().toLowerCase(), "UTF-8");
                        apiUrlBuilder.append("min(").append(encodedCarburant).append("_prix)%2C");
                    }
                    // Supprime le dernier caractère ","
                    apiUrlBuilder.delete(apiUrlBuilder.length() - 3, apiUrlBuilder.length());
                }
            }
        } catch (UnsupportedEncodingException e) {
            // Gérer l'exception ici, par exemple en enregistrant un message d'erreur ou en
            // lançant une exception runtime
            e.printStackTrace(); // ou logger.error("Encoding not supported", e);
        }

        if (isNbreStationsCarburantSelected) {
            apiUrlBuilder.append(
                    "select=carburants_disponibles&refine=carburants_disponibles%3A%22Gazole%22&refine=carburants_disponibles%3A%22SP98%22&refine=carburants_disponibles%3A%22E10%22&refine=carburants_disponibles%3A%22E85%22&refine=carburants_disponibles%3A%22GPLc%22&refine=carburants_disponibles%3A%22SP95%22");
        }

        if (isNbreStationsServiceSelected) {
            apiUrlBuilder.append("where=services%20IS%20NOT%20NULL");
        }

        // Ajouter les autres critères de filtrage (refine)
        for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
            String type = entry.getKey();
            List<String> locations = entry.getValue();

            try {
                // Supprimez les espaces inutiles en utilisant trim()
                String encodedLocation = URLEncoder.encode(locations.get(0).trim(), "UTF-8");

                // Traiter le type "carburant"
                if (type.equals("carburant") && locations.size() > 0 && !isPrixSelected && !isMinPrixSelected
                        && !isMedianPrixSelected) {
                    for (String location : locations) {
                        encodedLocation = URLEncoder.encode(location.trim(), "UTF-8");
                        apiUrlBuilder.append("&refine=carburants_disponibles%3A%22").append(encodedLocation)
                                .append("%22");
                    }
                } else if (type.equals("option")) {
                    for (String option : locations) {
                        String encodedOption = URLEncoder.encode("\"" + option.trim() + "\"", "UTF-8");
                        apiUrlBuilder.append("&refine=services_service%3A").append(encodedOption);
                    }
                } else if (!type.equals("filtre") && !type.equals("carburant")) {
                    // Ajouter les autres critères de filtrage (ex: departement, region)
                    String refineType = URLEncoder.encode(type.trim(), "UTF-8");
                    apiUrlBuilder.append("&refine=").append(refineType).append("%3A%22").append(encodedLocation)
                            .append("%22");
                }
            } catch (UnsupportedEncodingException e) {
                // Gérer l'exception ici, par exemple en enregistrant un message d'erreur ou en
                // lançant une exception runtime
                e.printStackTrace(); // ou logger.error("Encoding not supported", e);
            }
        }

        if (isNbreStationsCarburantSelected) {
            String apiUrl0Carbs = apiUrlBuilder.toString().replace(
                    "select=carburants_disponibles&refine=carburants_disponibles%3A%22Gazole%22&refine=carburants_disponibles%3A%22SP98%22&refine=carburants_disponibles%3A%22E10%22&refine=carburants_disponibles%3A%22E85%22&refine=carburants_disponibles%3A%22GPLc%22&refine=carburants_disponibles%3A%22SP95%22",
                    "");
            nombreTotalStations0Carbs = countTotalStationsWithoutNotNull(apiUrl0Carbs);
            System.out.println(nombreTotalStations0Carbs);

            String[] selectedCarburants = { "Gazole", "SP95", "SP98", "E10", "E85", "GPLc" };
            for (int i = 1; i <= selectedCarburants.length; i++) {
                StringBuilder apiUrlCarbs = new StringBuilder(API_URL);
                apiUrlCarbs.append("select=carburants_disponibles");
                for (int j = 0; j < i; j++) {
                    String carburant = selectedCarburants[j];
                    apiUrlCarbs.append("&refine=carburants_disponibles%3A%22").append(carburant).append("%22");
                }
                int nombreStationsCarbs = countTotalStationsWithoutNotNull(apiUrlCarbs.toString());
                nombreTotalStationsCabrs.add(nombreStationsCarbs);
            }
        }

        if (isNbreStationsServiceSelected) {
            // URL SANS LA CONDITION IS NOT NULL
            String apiUrlWithoutNotNull = apiUrlBuilder.toString().replace("where=services%20IS%20NOT%20NULL", "");
            // Compter le nombre total de stations sans la condition "IS NOT NULL"
            nombreTotalStations = countTotalStationsWithoutNotNull(apiUrlWithoutNotNull);
            System.out.println(nombreTotalStations);
        }
        return apiUrlBuilder.toString();
    }

    /**
     * Extrait et imprime les prix moyens des carburants spécifiés à partir d'une
     * chaîne JSON donnée
     *
     * @param jsonString la chaîne JSON contenant les données des prix moyens
     * @param criteria   les critères de sélection des carburants
     */
    private static void extractAndPrintAveragePrices(String jsonString, Map<String, List<String>> criteria) {
        try {
            // Convertit la réponse JSON en un objet JSON
            JSONObject jsonObject = new JSONObject(jsonString);

            // Obtient la liste des résultats
            JSONArray results = jsonObject.getJSONArray("results");

            // Vérifie s'il y a des résultats
            if (results.length() > 0) {
                JSONObject result = results.getJSONObject(0); // Prend le premier résultat

                // Vérifie les carburants sélectionnés par l'utilisateur
                List<String> selectedCarburants = criteria.get("carburant");
                if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                    for (String carburant : selectedCarburants) {
                        String key = "avg(" + carburant.trim().toLowerCase() + "_prix)";
                        if (result.has(key)) {
                            // Extrait et arrondit le prix moyen du carburant spécifié
                            double avgPrice = extractAndRoundPrice(result, key);
                            averagePrices.add(avgPrice);
                            // Affiche le prix moyen du carburant
                            System.out.println("Prix moyen de " + carburant + " : " + avgPrice);
                        } else {
                            System.out.println("Aucun résultat trouvé pour le carburant : " + carburant);
                        }
                    }
                } else {
                    System.out.println("Aucun carburant spécifié.");
                }
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extrait et arrondit un prix à deux chiffres après la virgule à partir d'un
     * objet JSON donné
     *
     * @param jsonObject l'objet JSON contenant le prix
     * @param key        la clé associée au prix dans l'objet JSON
     * @return le prix extrait et arrondi à deux chiffres après la virgule
     */
    private static double extractAndRoundPrice(JSONObject jsonObject, String key) {
        // Extrait la valeur associée à la clé spécifiée
        double price = jsonObject.getDouble(key);

        // Arrondit à deux chiffres après la virgule
        price = Math.round(price * 100.0) / 100.0;
        return price;
    }

    /**
     * Extrait et imprime les prix médians des carburants spécifiés à partir d'une
     * chaîne JSON donnée.
     *
     * @param jsonString la chaîne JSON contenant les données des prix médians
     * @param criteria   les critères de sélection des carburants
     */
    private static void extractAndPrintMedianPrices(String jsonString, Map<String, List<String>> criteria) {
        try {
            // Convertit la réponse JSON en un objet JSON
            JSONObject jsonObject = new JSONObject(jsonString);

            // Obtient la liste des résultats
            JSONArray results = jsonObject.getJSONArray("results");

            // Vérifie s'il y a des résultats
            if (results.length() > 0) {
                JSONObject result = results.getJSONObject(0); // Prend le premier résultat

                // Vérifie les carburants sélectionnés par l'utilisateur
                List<String> selectedCarburants = criteria.get("carburant");
                if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                    for (String carburant : selectedCarburants) {
                        String key = "median(" + carburant.trim().toLowerCase() + "_prix)";
                        if (result.has(key)) {
                            // Extrait et arrondit le prix médian du carburant spécifié
                            double medianPrice = extractAndRoundPrice(result, key);
                            medianPrices.add(medianPrice);
                            // Affiche le prix médian du carburant
                            System.out.println("Prix médian de " + carburant + " : " + medianPrice);
                        } else {
                            System.out.println("Aucun résultat trouvé pour le carburant : " + carburant);
                        }
                    }
                } else {
                    System.out.println("Aucun carburant spécifié.");
                }
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extrait et affiche les prix minimums des carburants spécifiés à partir d'une
     * chaîne JSON donnée
     *
     * @param jsonString la chaîne JSON contenant les données des stations
     * @param criteria   les critères de sélection des données
     */
    private static void extractAndPrintMinPrices(String jsonString, Map<String, List<String>> criteria) {
        try {
            // Convertir la réponse JSON en un objet JSON
            JSONObject jsonObject = new JSONObject(jsonString);

            // Obtenir la liste des résultats
            JSONArray results = jsonObject.getJSONArray("results");

            // Vérifier s'il y a des résultats
            if (results.length() > 0) {
                JSONObject result = results.getJSONObject(0); // Prendre le premier résultat

                // Vérifier les carburants sélectionnés par l'utilisateur
                List<String> selectedCarburants = criteria.get("carburant");
                if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                    for (String carburant : selectedCarburants) {
                        String key = "min(" + carburant.trim().toLowerCase() + "_prix)";
                        if (result.has(key)) {
                            // Extraire et arrondir le prix minimum du carburant spécifié
                            double minPrice = extractAndRoundPrice(result, key);
                            // Afficher le prix minimum du carburant
                            minPrices.add(minPrice);
                            System.out.println("Prix minimum de " + carburant + " : " + minPrice);
                        } else {
                            System.out.println("Aucun résultat trouvé pour le carburant : " + carburant);
                        }
                    }
                } else {
                    System.out.println("Aucun carburant spécifié.");
                }
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compte le nombre total de stations à partir d'une chaîne JSON donnée
     *
     * @param jsonString la chaîne JSON contenant les données des stations
     * @return le nombre total de stations extrait de la chaîne JSON
     */
    private static int countTotalStations(String jsonString) {
        // Converti la réponse JSON en un objet JSON
        JSONObject jsonObject = new JSONObject(jsonString);

        // Extraire la valeur du champ "total_count"

        int totalCount = jsonObject.getInt("total_count");
        totalCountStations = totalCount;

        return totalCount;
    }

    /**
     * Crée un diagramme camembert représentant le nombre de stations ayant tous les
     * services
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la
     *                     création du diagramme
     */
    private static void camembertService() throws IOException {
        int sum = nombreTotalStations - nombreTotalStationServices;
        DiagCamemberts camembertsSer = new DiagCamemberts("Nombre de stations ayant un service", 2);
        camembertsSer.ajouterDonnees(" ", sum, nombreTotalStationServices);
        camembertsSer.legender("Station n'ayant aucun service ", "Station avec au moins un service");
        camembertsSer.colorier("Blue", "Red");
        String chemin = "src/page_Web/DiagrammeCammembertServices.svg";
        FileWriter writer0 = new FileWriter(chemin);
        writer0.write(camembertsSer.agencer().enSVG());
        writer0.close();

    }

    /**
     * Crée un diagramme camembert représentant le nombre de stations ayant tous les
     * carburants
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la
     *                     création du diagramme
     */
    private static void camembertCarburants() throws IOException {
        int diff = nombreTotalStations0Carbs - nombreTotalStationCarburants;
        System.out.println(diff + " " + nombreTotalStationCarburants);
        if (diff != 0 && nombreTotalStationCarburants != 0) {
            DiagCamemberts camembertsCarburants = new DiagCamemberts("Nombre de stations ayant tous les carburants", 2);
            camembertsCarburants.ajouterDonnees(" ", nombreTotalStationCarburants, diff);
            camembertsCarburants.legender("Stations ayant tous les carburants ",
                    "Station n'ayant pas tous les carburants");
            camembertsCarburants.colorier("Green", "Orange");
            String chemin = "src/page_Web/DiagrammeCammembertCarburants.svg";
            FileWriter writer0 = new FileWriter(chemin);
            writer0.write(camembertsCarburants.agencer().enSVG());
            writer0.close();
        }
    }

    /**
     * Crée un diagramme camembert représentant le nombre de stations ayant tous les
     * carburants
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la
     *                     création du diagramme
     */
    private static void camembertAllCarburants() throws IOException {

        DiagCamemberts camembertsCarburants = new DiagCamemberts("Nombre de stations ayant tous les carburants", 6);

        // Convertie la liste en tableau d'entiers pour les données
        int[] donnees = nombreTotalStationsCabrs.stream().mapToInt(Integer::intValue).toArray();

        // Convertie les valeurs entières en doubles
        double[] donneesDoubles = new double[donnees.length];
        for (int i = 0; i < donnees.length; i++) {
            donneesDoubles[i] = (double) donnees[i];
            System.out.println(donneesDoubles[i]);
        }

        // Ajoute les données à objet DiagCamemberts
        camembertsCarburants.ajouterDonnees("Stations avec n carburants", donneesDoubles);

        // Définie les légendes pour les sections
        camembertsCarburants.legender("1c", "2c", "3c", "4c", "5c", "6c");
        // Gazole", "Gazole + SP95", "Gazole + SP95 + SP98", "Gazole + SP95 + SP98 +
        // E10", "Gazole + SP95 + SP98 + E10 + E85", "Gazole + SP95 + SP98 + E10 + E85 +
        // GPLc

        // Définie les couleurs pour les sections
        camembertsCarburants.colorier("Green", "Orange", "Blue", "Red", "Yellow", "Purple");
        String chemin = "src/page_Web/DiagrammeCammembertAllCarburants.svg";
        FileWriter writer0 = new FileWriter(chemin);
        writer0.write(camembertsCarburants.agencer().enSVG());
        writer0.close();

    }

    private static void diagBarreAvgPrice() throws IOException {
        List<Double> listePrix = getAveragePrices();
        List<String> carburants = getSelectedCarburantNames();
        int nbre = listePrix.size() / carburants.size();

        // Créez le diagramme avec le titre approprié
        DiagColonnes diagPrixMoyen = new DiagColonnes("Prix moyen en fonction de plusieurs departements", nbre);

        // Organisez les prix par type de carburant
        HashMap<String, List<Double>> prixParCarburant = new HashMap<>();
        for (int i = 0; i < listePrix.size(); i++) {
            String carburant = carburants.get(i % carburants.size());
            prixParCarburant.putIfAbsent(carburant, new ArrayList<>());
            prixParCarburant.get(carburant).add(listePrix.get(i));
        }

        // Affiche les prix par carburant pour vérification
        for (String carburant : prixParCarburant.keySet()) {
            List<Double> prixList = prixParCarburant.get(carburant);
            // Trouver l'indice de la valeur maximale
            int maxIndex = 0;
            for (int i = 1; i < prixList.size(); i++) {
                if (prixList.get(i) > prixList.get(maxIndex)) {
                    maxIndex = i;
                }
            }
            // Mettre la valeur maximale en première position
            double maxValue = prixList.get(maxIndex);
            prixList.remove(maxIndex);
            prixList.add(0, maxValue);

            // Convertir la liste en tableau
            double[] prixArray = new double[prixList.size()];
            for (int i = 0; i < prixList.size(); i++) {
                prixArray[i] = prixList.get(i);
            }

            // Appelez la méthode ajouterDonnees() pour chaque carburant
            diagPrixMoyen.ajouterDonnees(carburant, prixArray);
            diagPrixMoyen.deplacer(200, 0);
            diagPrixMoyen.agencer();
        }

        // Construire les noms des départements
        String[] departementArray = new String[selectedDepartementNames.size()];
        for (int i = 0; i < selectedDepartementNames.size(); i++) {
            String cleanedName = selectedDepartementNames.get(i)
                    .replaceAll("[éè]", "e") // Remplace é et è par e
                    .replaceAll("ô", "o"); // Remplace ô par o
            departementArray[i] = cleanedName;
        }
        diagPrixMoyen.legender(departementArray);

        String[] colors = { "Blue", "Green", "Red", "Orange", "Purple", "Yellow", "Cyan", "Magenta" };
        String[] coloriage = new String[departementArray.length];

        // Attribution cyclique des couleurs à chaque département
        for (int i = 0; i < departementArray.length; i++) {
            coloriage[i] = colors[i % colors.length];
        }

        diagPrixMoyen.colorier(coloriage);
        FileWriter writer01 = new FileWriter("DiagrammeBarresPrixMoyen.svg");
        writer01.write(diagPrixMoyen.agencer().enSVG());
        writer01.close();
    }

    private static void diagBarreMedPrice() throws IOException {
        List<Double> listePrix = getMedianPrices();
        int nbre = medianPrices.size();

        // Créez le diagramme avec le titre approprié
        DiagColonnes diagPrixMoyen = new DiagColonnes("Prix median en fonction de plusieurs departements", nbre);

        // Construisez dynamiquement la liste des prix
        double[] prixArray = new double[listePrix.size()];
        for (int i = 0; i < listePrix.size(); i++) {
            prixArray[i] = listePrix.get(i); // Ajoutez chaque prix à la liste des prix
        }

        int maxIndex = 0;
        for (int i = 0; i < prixArray.length; i++) {
            if (prixArray[i] > prixArray[maxIndex]) {
                maxIndex = i;
            }
        }

        double temp = prixArray[0];
        prixArray[0] = prixArray[maxIndex];
        prixArray[maxIndex] = temp;

        // Appelez la méthode ajouterDonnees() avec la liste de prix construite
        // dynamiquement
        diagPrixMoyen.ajouterDonnees("", prixArray);

        String[] departementArray = new String[selectedDepartementNames.size()];
        for (int i = 0; i < selectedDepartementNames.size(); i++) {
            String cleanedName = selectedDepartementNames.get(i)
                    .replaceAll("[éè]", "e") // Remplace é et è par e
                    .replaceAll("ô", "o"); // Remplace ô par o
            departementArray[i] = cleanedName;
        }
        diagPrixMoyen.legender(departementArray);

        String[] colors = { "Blue", "Green", "Red", "Orange", "Purple", "Yellow", "Cyan", "Magenta" };
        String[] coloriage = new String[departementArray.length];

        // Attribution cyclique des couleurs à chaque département
        for (int i = 0; i < departementArray.length; i++) {
            coloriage[i] = colors[i % colors.length];
        }

        diagPrixMoyen.colorier(coloriage);
        FileWriter writer01 = new FileWriter("DiagrammeBarresPrixMedian.svg");
        writer01.write(diagPrixMoyen.agencer().enSVG());
        writer01.close();
    }

    private static void diagBarreMinPrice() throws IOException {
        List<Double> listePrix = getMinPrices();
        int nbre = minPrices.size();

        // Créez le diagramme avec le titre approprié
        DiagColonnes diagPrixMoyen = new DiagColonnes("Prix minimum en fonction de plusieurs departements", nbre);

        // Construisez dynamiquement la liste des prix
        double[] prixArray = new double[listePrix.size()];
        for (int i = 0; i < listePrix.size(); i++) {
            prixArray[i] = listePrix.get(i); // Ajoutez chaque prix à la liste des prix
        }

        int maxIndex = 0;
        for (int i = 0; i < prixArray.length; i++) {
            if (prixArray[i] > prixArray[maxIndex]) {
                maxIndex = i;
            }
        }

        double temp = prixArray[0];
        prixArray[0] = prixArray[maxIndex];
        prixArray[maxIndex] = temp;

        // Appelez la méthode ajouterDonnees() avec la liste de prix construite
        // dynamiquement
        diagPrixMoyen.ajouterDonnees("", prixArray);

        String[] departementArray = new String[selectedDepartementNames.size()];
        for (int i = 0; i < selectedDepartementNames.size(); i++) {
            String cleanedName = selectedDepartementNames.get(i)
                    .replaceAll("[éè]", "e") // Remplace é et è par e
                    .replaceAll("ô", "o"); // Remplace ô par o
            departementArray[i] = cleanedName;
        }
        diagPrixMoyen.legender(departementArray);

        String[] colors = { "Blue", "Green", "Red", "Orange", "Purple", "Yellow", "Cyan", "Magenta" };
        String[] coloriage = new String[departementArray.length];

        // Attribution cyclique des couleurs à chaque département
        for (int i = 0; i < departementArray.length; i++) {
            coloriage[i] = colors[i % colors.length];
        }

        diagPrixMoyen.colorier(coloriage);
        FileWriter writer01 = new FileWriter("DiagrammeBarresPrixMin.svg");
        writer01.write(diagPrixMoyen.agencer().enSVG());
        writer01.close();
    }

    /**
     * Compte le nombre total de stations sans valeurs nulles à partir de l'URL de
     * l'API donnée
     *
     * @param apiUrlWithoutNotNull l'URL de l'API sans valeurs nulles
     * @return le nombre total de stations sans valeurs nulles
     */
    private static int countTotalStationsWithoutNotNull(String apiUrlWithoutNotNull) {
        try {
            URL url = new URL(apiUrlWithoutNotNull);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Convertir la réponse JSON en un objet JSON
                JSONObject jsonObject = new JSONObject(response.toString());

                // Extraire la valeur du champ "total_count"
                int totalCount = jsonObject.getInt("total_count");

                return totalCount;
            } else {
                System.out.println("La requête a échoué avec le code : " + responseCode);
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}