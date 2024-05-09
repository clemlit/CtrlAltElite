import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map;

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

                    // Extraire et arrondir les prix moyens
                    double avgGazole = extractAndRoundAveragePrice(jsonString, "avg(gazole_prix)");
                    double avgSP98 = extractAndRoundAveragePrice(jsonString, "avg(sp98_prix)");
                    double avgSP95 = extractAndRoundAveragePrice(jsonString, "avg(sp95_prix)");
                    double avgGPLc = extractAndRoundAveragePrice(jsonString, "avg(gplc_prix)");
                    double avgE85 = extractAndRoundAveragePrice(jsonString, "avg(e85_prix)");
                    double avgE10 = extractAndRoundAveragePrice(jsonString, "avg(e10_prix)");

                    // Afficher les prix moyens
                    System.out.println("Prix moyen Gazole : " + avgGazole);
                    System.out.println("Prix moyen SP98 : " + avgSP98);
                    System.out.println("Prix moyen SP95 : " + avgSP95);
                    System.out.println("Prix moyen GPLc : " + avgGPLc);
                    System.out.println("Prix moyen E85 : " + avgE85);
                    System.out.println("Prix moyen E10 : " + avgE10);
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
        if (criteria.containsKey("filtre")) {
            List<String> filtres = criteria.get("filtre");
            isPrixSelected = filtres.contains("Prix moyen");
        }
        if (isPrixSelected) {
            apiUrlBuilder.append("select=avg(gazole_prix)%2Cavg(sp98_prix)%2Cavg(gplc_prix)%2Cavg(sp95_prix)%2Cavg(e85_prix)%2Cavg(e10_prix)");
        }

        // Ajouter le paramètre "limit"
        apiUrlBuilder.append("&limit=20");

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

        return apiUrlBuilder.toString();
    }

    private static double extractAndRoundAveragePrice(String jsonString, String priceType) {
        // Trouver l'indice de début et de fin de la valeur du prix moyen
        int startIndex = jsonString.indexOf(priceType) + priceType.length() + 3;
        int endIndex = jsonString.indexOf("}", startIndex);

        // Extraire la valeur du prix moyen
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

        // Convertir la valeur du prix moyen en double et arrondir à deux chiffres après
        // la virgule
        double roundedPrice = Double.parseDouble(priceValue);
        roundedPrice = Math.round(roundedPrice * 100.0) / 100.0; // Arrondir à deux chiffres après la virgule

        return roundedPrice;
    }

}
