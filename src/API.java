import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;

import fr.univrennes.istic.l2gen.visustats.DiagCamemberts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Cette classe permet de télécharger des données sur les prix des carburants en
 * France depuis une API externe
 * Elle utilise HttpURLConnection pour effectuer une requête HTTP GET et
 * récupérer les données au format JSON
 */

public class API extends UI{

    //ATTRIBUTS DE LA CLASSE 

    private static List<Double> averagePrices = new ArrayList<>();
    private static List<Double> medianPrices = new ArrayList<>();
    private static List<Double> minPrices = new ArrayList<>();
    private static Integer totalCountStations;
    private static int nombreTotalStationServices = 0;
    private static int nombreTotalStations = 0;

    

    //SETTERS AND GETTERS

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

    private static final String API_URL = "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?";

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

                // Vérifier si les prix moyens sont demandés
                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix moyen")) {
                // Analyser la réponse JSON
                String jsonString = response.toString();

                // Récupérer les prix moyens des carburants sélectionnés
                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix moyen")) {
                    extractAndPrintAveragePrices(jsonString, criteria);
                }
            }

                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix median")) {
                    String jsonString = response.toString();
                    // Extraire et arrondir les prix médians
                    extractAndPrintMedianPrices(jsonString, criteria);

                }

                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix minimum")) {
                    String jsonString = response.toString();
                    extractAndPrintMinPrices(jsonString, criteria);
                }

                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Nombre de stations qui proposent chaque type de carburant")){
                    int totalCount = countTotalStations(response.toString());
                    System.out.println("Nombre total de stations ayant tous les carburants: " + totalCount);
                }

                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Nombre de stations qui proposent des services spécifiques")){
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
            isNbreStationsCarburantSelected = filtres.contains("Nombre de stations qui proposent chaque type de carburant");
            isNbreStationsServiceSelected = filtres.contains("Nombre de stations qui proposent des services spécifiques");

        }
        if (isPrixSelected) {
            apiUrlBuilder.append("select=");
            List<String> selectedCarburants = criteria.get("carburant");
            if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                for (String carburant : selectedCarburants) {
                    String encodedCarburant = URLEncoder.encode(carburant.trim().toLowerCase(), StandardCharsets.UTF_8);
                    apiUrlBuilder.append("avg(").append(encodedCarburant).append("_prix)%2C");
                }
                    // Supprime le dernier caractère ", "
                apiUrlBuilder.delete(apiUrlBuilder.length() - 3, apiUrlBuilder.length());
            }
        }
        if (isMedianPrixSelected){
            apiUrlBuilder.append("select=");
            List<String> selectedCarburants = criteria.get("carburant");
            if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                for (String carburant : selectedCarburants) {
                    String encodedCarburant = URLEncoder.encode(carburant.trim().toLowerCase(), StandardCharsets.UTF_8);
                    apiUrlBuilder.append("median(").append(encodedCarburant).append("_prix)%2C");
                }
                // Supprime le dernier caractère ", "
                apiUrlBuilder.delete(apiUrlBuilder.length() - 3, apiUrlBuilder.length());
            }
        }if (isMinPrixSelected){
            apiUrlBuilder.append("select=");
            List<String> selectedCarburants = criteria.get("carburant");
            if (selectedCarburants != null && !selectedCarburants.isEmpty()) {
                for (String carburant : selectedCarburants) {
                    String encodedCarburant = URLEncoder.encode(carburant.trim().toLowerCase(), StandardCharsets.UTF_8);
                    apiUrlBuilder.append("min(").append(encodedCarburant).append("_prix)%2C");
                }
                // Supprime le dernier caractère ", "
                apiUrlBuilder.delete(apiUrlBuilder.length() - 3, apiUrlBuilder.length());
            }
        }     

        if (isNbreStationsCarburantSelected){
            apiUrlBuilder.append("select=carburants_disponibles&refine=carburants_disponibles%3A%22Gazole%22&refine=carburants_disponibles%3A%22SP98%22&refine=carburants_disponibles%3A%22E10%22&refine=carburants_disponibles%3A%22E85%22&refine=carburants_disponibles%3A%22GPLc%22&refine=carburants_disponibles%3A%22SP95%22");
        }

        if (isNbreStationsServiceSelected){
            apiUrlBuilder.append("where=services%20IS%20NOT%20NULL");
        }

        // Ajouter les autres critères de filtrage (refine)
        for (Map.Entry<String, List<String>> entry : criteria.entrySet()) {
            String type = entry.getKey();
            List<String> locations = entry.getValue();

            // Supprimez les espaces inutiles en utilisant trim()
            String encodedLocation = URLEncoder.encode(locations.get(0).trim(), StandardCharsets.UTF_8);

            // Traiter le type "carburant"
            if (type.equals("carburant") && locations.size() > 0 && !isPrixSelected  && !isMinPrixSelected  && !isMedianPrixSelected) {
                for (String location : locations) {
                    encodedLocation = URLEncoder.encode(location.trim(), StandardCharsets.UTF_8);
                    apiUrlBuilder.append("&refine=carburants_disponibles%3A%22").append(encodedLocation).append("%22");
                }
            } 
            else if (type.equals("option")) {
                for (String option : locations) {
                    String encodedOption = URLEncoder.encode("\"" + option.trim() + "\"", StandardCharsets.UTF_8);
                    apiUrlBuilder.append("&refine=services_service%3A").append(encodedOption);
                }
            } else if (!type.equals("filtre") && !type.equals("carburant")) {
                // Ajouter les autres critères de filtrage (ex: departement, region)
                String refineType = URLEncoder.encode(type.trim(), StandardCharsets.UTF_8);
                apiUrlBuilder.append("&refine=").append(refineType).append("%3A%22").append(encodedLocation)
                        .append("%22");
            }
        }

        //URL SANS LA CONDITION IS NOT NULL
        String apiUrlWithoutNotNull = apiUrlBuilder.toString().replace("where=services%20IS%20NOT%20NULL", "");
        // Compter le nombre total de stations sans la condition "IS NOT NULL"
        nombreTotalStations = countTotalStationsWithoutNotNull(apiUrlWithoutNotNull);
        System.out.println(nombreTotalStations);
        return apiUrlBuilder.toString();
    }

    private static void extractAndPrintAveragePrices(String jsonString, Map<String, List<String>> criteria) {
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
                    String key = "avg(" + carburant.trim().toLowerCase() + "_prix)";
                    if (result.has(key)) {
                        // Extraire et arrondir le prix moyen du carburant spécifié
                        double avgPrice = extractAndRoundPrice(result, key);
                        getAveragePrices().add(avgPrice);
                        // Afficher le prix moyen du carburant
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

private static double extractAndRoundPrice(JSONObject jsonObject, String key) {
    // Extraire la valeur associée à la clé spécifiée
    double price = jsonObject.getDouble(key);
    
    // Arrondir à deux chiffres après la virgule
    price = Math.round(price * 1000.0) / 1000.0;
    
    return price;
}

    private static void extractAndPrintMedianPrices(String jsonString, Map<String, List<String>> criteria) {
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
                    String key = "median(" + carburant.trim().toLowerCase() + "_prix)";
                    if (result.has(key)) {
                        // Extraire et arrondir le prix médian du carburant spécifié
                        double medianPrice = extractAndRoundPrice(result, key);
                        // Afficher le prix médian du carburant
                        getMedianPrices().add(medianPrice);
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
                        getMinPrices().add(minPrice);
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

    private static int countTotalStations(String jsonString) {
    // Convertir la réponse JSON en un objet JSON
    JSONObject jsonObject = new JSONObject(jsonString);
    
    // Extraire la valeur du champ "total_count"
    
    int totalCount = jsonObject.getInt("total_count");
    totalCountStations = totalCount;

    return totalCount;
}

    private static void camembertService() throws IOException {
        int sum = nombreTotalStations - nombreTotalStationServices;
        DiagCamemberts camembertsSer = new DiagCamemberts("Nombre de stations ayant un service", 2);
        camembertsSer.ajouterDonnees(" ", sum, nombreTotalStations);
        System.out.println(" ABC " +sum + " "+ nombreTotalStationServices + " " + nombreTotalStations);
        camembertsSer.legender("Station n'ayant aucun service ","Station avec au moins un service");
        camembertsSer.colorier("Blue", "Red");
        String chemin = "src/page_Web/DiagrammeCammembertServices.svg";
        FileWriter writer0 = new FileWriter(chemin);
        writer0.write(camembertsSer.agencer().enSVG());
        writer0.close();

    }

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


