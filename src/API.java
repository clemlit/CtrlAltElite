import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

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

    private static final String API_URL = "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?limit=20";

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

                // Convertir la réponse JSON en objet JSONObject
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Extraire les résultats
                JSONArray results = jsonResponse.getJSONArray("results");

                // Créer un Map pour stocker les prix de chaque type de carburant
                Map<String, List<Double>> fuelPrices = new HashMap<>();

                // Parcourir les résultats
                for (int i = 0; i < results.length(); i++) {
                    JSONObject station = results.getJSONObject(i);

                    // Extraire le champ "prix"
                    Object prixObj = station.get("prix");

                    if (prixObj instanceof JSONArray) {
                        JSONArray prices = (JSONArray) prixObj;

                        // Parcourir les prix
                        for (int j = 0; j < prices.length(); j++) {
                            JSONObject price = prices.getJSONObject(j);
                            String fuelType = price.getString("@nom");
                            double fuelPrice = price.getDouble("@valeur");

                            // Ajouter le prix à la liste des prix pour ce type de carburant
                            fuelPrices.computeIfAbsent(fuelType, k -> new ArrayList<>()).add(fuelPrice);
                        }
                    } else if (prixObj instanceof String) {
                        // Traitement spécial si le champ "prix" est une chaîne JSON
                        JSONArray prices = new JSONArray((String) prixObj);

                        // Parcourir les prix
                        for (int j = 0; j < prices.length(); j++) {
                            JSONObject price = prices.getJSONObject(j);
                            String fuelType = price.getString("@nom");
                            double fuelPrice = price.getDouble("@valeur");

                            // Ajouter le prix à la liste des prix pour ce type de carburant
                            fuelPrices.computeIfAbsent(fuelType, k -> new ArrayList<>()).add(fuelPrice);
                        }
                    }
                }

                // Afficher le prix moyen, médian et minimal pour chaque type de carburant
                DecimalFormat df = new DecimalFormat("#.##");
                for (String fuelType : fuelPrices.keySet()) {
                    List<Double> prices = fuelPrices.get(fuelType);
                    Collections.sort(prices); // Trier les prix

                    // Calculer le prix moyen
                    double totalPrice = 0;
                    for (double price : prices) {
                        totalPrice += price;
                    }
                    double averagePrice = totalPrice / prices.size();

                    // Calculer le prix médian
                    double medianPrice;
                    int size = prices.size();
                    if (size % 2 == 0) {
                        medianPrice = (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
                    } else {
                        medianPrice = prices.get(size / 2);
                    }

                    // Obtenir le prix minimal
                    double minPrice = prices.get(0);

                    System.out.println("Type de carburant : " + fuelType);
                    System.out.println("Prix moyen : " + df.format(averagePrice));
                    System.out.println("Prix médian : " + df.format(medianPrice));
                    System.out.println("Prix minimal : " + df.format(minPrice));
                }
            } else {
                System.out.println("La requête a échoué avec le code : " + responseCode);
            }

            conn.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private static String buildApiUrl(Map<String, List<String>> criteria) {
        StringBuilder apiUrlBuilder = new StringBuilder(API_URL);

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
                // Parcourir la liste des options
                for (String option : locations) {
                    // Encoder l'option avec les guillemets autour du nom et les espaces encodés en
                    // "%20"
                    String encodedOption = URLEncoder.encode("\"" + option.trim() + "\"", StandardCharsets.UTF_8);
                    // Ajouter l'option à l'URL de l'API
                    apiUrlBuilder.append("&refine=services_service%3A").append(encodedOption);
                    System.out.println(apiUrlBuilder);
                }
            } else {
                apiUrlBuilder.append("&refine=").append(type).append("%3A%22").append(encodedLocation).append("%22");
            }
        }
        return apiUrlBuilder.toString();
    }

}
