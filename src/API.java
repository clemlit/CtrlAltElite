import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    double medianGazole = extractAndRoundMedianPrice(jsonString, "median(gazole_prix)");
                    double medianSP98 = extractAndRoundMedianPrice(jsonString, "median(sp98_prix)");
                    double medianSP95 = extractAndRoundMedianPrice(jsonString, "median(sp95_prix)");
                    double medianGPLc = extractAndRoundMedianPrice(jsonString, "median(gplc_prix)");
                    double medianE85 = extractAndRoundMedianPrice(jsonString, "median(e85_prix)");
                    double medianE10 = extractAndRoundMedianPrice(jsonString, "median(e10_prix)");

                    // Afficher les prix médians
                    System.out.println("Prix médian Gazole : " + medianGazole);
                    System.out.println("Prix médian SP98 : " + medianSP98);
                    System.out.println("Prix médian SP95 : " + medianSP95);
                    System.out.println("Prix médian GPLc : " + medianGPLc);
                    System.out.println("Prix médian E85 : " + medianE85);
                    System.out.println("Prix médian E10 : " + medianE10);
                }

                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Prix minimum")) {
                    String jsonString = response.toString();
                    // Extraire et arrondir les prix minimums
                    double minGazole = extractAndRoundMinPrice(jsonString, "min(gazole_prix)");
                    double minSP98 = extractAndRoundMinPrice(jsonString, "min(sp98_prix)");
                    double minSP95 = extractAndRoundMinPrice(jsonString, "min(sp95_prix)");
                    double minGPLc = extractAndRoundMinPrice(jsonString, "min(gplc_prix)");
                    double minE85 = extractAndRoundMinPrice(jsonString, "min(e85_prix)");
                    double minE10 = extractAndRoundMinPrice(jsonString, "min(e10_prix)");

                    // Afficher les prix minimums
                    System.out.println("Prix minimum Gazole : " + minGazole);
                    System.out.println("Prix minimum SP98 : " + minSP98);
                    System.out.println("Prix minimum SP95 : " + minSP95);
                    System.out.println("Prix minimum GPLc : " + minGPLc);
                    System.out.println("Prix minimum E85 : " + minE85);
                    System.out.println("Prix minimum E10 : " + minE10);
                }

                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Nombre de stations qui proposent chaque type de carburant")){
                    int totalCount = countTotalStations(response.toString());
                    System.out.println("Nombre total de stations ayant tous les carburants: " + totalCount);
                }

                if (criteria.containsKey("filtre") && criteria.get("filtre").contains("Nombre de stations qui proposent des services spécifiques")){
                    int totalCount = countTotalStations(response.toString());
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
                    // Supprimer le dernier caractère ", "
                apiUrlBuilder.delete(apiUrlBuilder.length() - 3, apiUrlBuilder.length());
            }
        }
        if (isMedianPrixSelected){
            apiUrlBuilder.append("select=median(gazole_prix)%2Cmedian(sp98_prix)%2Cmedian(gplc_prix)%2Cmedian(sp95_prix)%2Cmedian(e85_prix)%2Cmedian(e10_prix)");

        }if (isMinPrixSelected){
            apiUrlBuilder.append("select=min(gazole_prix)%2Cmin(sp98_prix)%2Cmin(gplc_prix)%2Cmin(sp95_prix)%2Cmin(e85_prix)%2Cmin(e10_prix)");
        }     

        if (isNbreStationsCarburantSelected){
            apiUrlBuilder.append("select=carburants_disponibles&limit=20&refine=carburants_disponibles%3A%22Gazole%22&refine=carburants_disponibles%3A%22SP98%22&refine=carburants_disponibles%3A%22E10%22&refine=carburants_disponibles%3A%22E85%22&refine=carburants_disponibles%3A%22GPLc%22&refine=carburants_disponibles%3A%22SP95%22");
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
            if (type.equals("carburant") && locations.size() > 0) {
                for (String location : locations) {
                    encodedLocation = URLEncoder.encode(location.trim(), StandardCharsets.UTF_8);
                    apiUrlBuilder.append("&refine=carburants_disponibles%3A%22").append(encodedLocation).append("%22");
                }
            } else if (type.equals("option")) {
                for (String option : locations) {
                    String encodedOption = URLEncoder.encode("\"" + option.trim() + "\"", StandardCharsets.UTF_8);
                    apiUrlBuilder.append("&refine=services_service%3A").append(encodedOption);
                }
            } else if (!type.equals("filtre")) {
                // Ajouter les autres critères de filtrage (ex: departement, region)
                String refineType = URLEncoder.encode(type.trim(), StandardCharsets.UTF_8);
                apiUrlBuilder.append("&refine=").append(refineType).append("%3A%22").append(encodedLocation)
                        .append("%22");
            }
        }
        System.out.println("Requête HTTP : " + apiUrlBuilder.toString());

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
    price = Math.round(price * 100.0) / 100.0;
    
    return price;
}

    private static double extractAndRoundMedianPrice(String jsonString, String priceType) {
        // Trouver l'indice de début et de fin de la valeur du prix médian
        int startIndex = jsonString.indexOf(priceType) + priceType.length() + 3;
        int endIndex = jsonString.indexOf("}", startIndex);

        // Extraire la valeur du prix médian
        String priceValue = jsonString.substring(startIndex, endIndex);

        // Nettoyer la chaîne en supprimant les caractères non numériques sauf le point
        // décimal
        priceValue = priceValue.replaceAll("[^0-9.]", "");

        // Si la chaîne contient plus d'un point décimal, supprimer les occurrences
        // supplémentaires
        int dotIndex = priceValue.indexOf(".");
        if (dotIndex != -1) {
            priceValue = priceValue.substring(0, dotIndex + 1) + priceValue.substring(dotIndex + 1).replace(".", "");
        }

        // Convertir la valeur du prix médian en double et arrondir à deux chiffres
        // après
        // la virgule
        double roundedPrice = Double.parseDouble(priceValue);
        roundedPrice = Math.round(roundedPrice * 100.0) / 100.0; // Arrondir à deux chiffres après la virgule

        return roundedPrice;
    }

    private static double extractAndRoundMinPrice(String jsonString, String priceType) {
        // Trouver l'indice de début et de fin de la valeur du prix minimum
        int startIndex = jsonString.indexOf(priceType) + priceType.length() + 3;
        int endIndex = jsonString.indexOf("}", startIndex);

        // Extraire la valeur du prix minimum
        String priceValue = jsonString.substring(startIndex, endIndex);

        // Nettoyer la chaîne en supprimant les caractères non numériques sauf le point
        // décimal
        priceValue = priceValue.replaceAll("[^0-9.]", "");

        // Si la chaîne contient plus d'un point décimal, supprimer les occurrences
        // supplémentaires
        int dotIndex = priceValue.indexOf(".");
        if (dotIndex != -1) {
            priceValue = priceValue.substring(0, dotIndex + 1) + priceValue.substring(dotIndex + 1).replace(".", "");
        }

        // Convertir la valeur du prix minimum en double et arrondir à deux chiffres
        // après
        // la virgule
        double roundedPrice = Double.parseDouble(priceValue);
        roundedPrice = Math.round(roundedPrice * 100.0) / 100.0; // Arrondir à deux chiffres après la virgule

        return roundedPrice;
    }

    private static int countTotalStations(String jsonString) {
    // Convertir la réponse JSON en un objet JSON
    JSONObject jsonObject = new JSONObject(jsonString);
    
    // Extraire la valeur du champ "total_count"
    int totalCount = jsonObject.getInt("total_count");
    
    return totalCount;
}

}


