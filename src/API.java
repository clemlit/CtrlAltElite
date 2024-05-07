import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("Veuillez choisir l'option (ville/departement/region/carburant/France/option/quitter) :");
                String option = scanner.nextLine().toLowerCase();

                switch (option) {
                    case "ville":
                        System.out.println("Veuillez saisir le nom de la ville :");
                        String ville = scanner.nextLine();
                        retrieveFuelDataByLocation("ville", Arrays.asList(ville));
                        break;
                    case "departement":
                        System.out.println("Veuillez saisir le département (ex : Gironde) :");
                        String departement = scanner.nextLine();
                        retrieveFuelDataByLocation("departement", Arrays.asList(departement));
                        break;
                    case "region":
                        System.out.println("Veuillez saisir le nom de la région (ex: Nouvelle-Aquitaine) :");
                        String region = scanner.nextLine();
                        retrieveFuelDataByLocation("region", Arrays.asList(region));
                        break;
                    case "carburant":
                        System.out.println("Veuillez saisir le nom du carburant :");
                        String carburant = scanner.nextLine();
                        retrieveFuelDataByLocation("carburant", Arrays.asList(carburant));
                        break;
                    case "option":
                        System.out.println("Veuillez choisir l'option (Wifi /boutique alimentaire /station de gonflage /lavage automatique/ borne electrique /automate / distributeur /espace bebe/toilette publique) :");
                        String Option = scanner.nextLine(); 
                        retrieveFuelDataByLocation("option", Arrays.asList(Option));
                        break;
                    case "france":
                        retrieveFuelDataByLocation("France", Arrays.asList("France"));
                        break;
                    case "quitter":
                        running = false;
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                        break;
                }
            }

            System.out.println("Programme terminé.");
        }
    }


    public static void retrieveFuelDataByLocation(String type, List<String> locations) {
        // Supprimez les espaces inutiles en utilisant trim()

        try {
            String apiUrl = buildApiUrl(type, locations);

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

                System.out.println("Données récupérées :");
                System.out.println(response.toString());
            } else {
                System.out.println("La requête a échoué avec le code : " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildApiUrl(String type, List<String> locations) {
        StringBuilder apiUrlBuilder = new StringBuilder(API_URL);

        // Supprimez les espaces inutiles en utilisant trim()
        String encodedLocation = URLEncoder.encode(locations.get(0).trim(), StandardCharsets.UTF_8);

        // Traiter le type "carburant"
        if (type.equals("carburant") && locations.size() > 0) {
            apiUrlBuilder.append("&refine=carburants_disponibles%3A%22").append(encodedLocation).append("%22");
            for (int i = 1; i < locations.size(); i++) {
                encodedLocation = URLEncoder.encode(locations.get(i).trim(), StandardCharsets.UTF_8);
                apiUrlBuilder.append("&refine=carburants_disponibles%3A%22").append(encodedLocation).append("%22");
            }
        }
        else if (type.equals("option")) {
            // Parcourir la liste des options
            for (String option : locations) {
                // Encoder l'option avec les guillemets autour du nom et les espaces encodés en
                // "%20"
                String encodedOption = URLEncoder.encode("\"" + option.trim() + "\"", StandardCharsets.UTF_8);
                // Ajouter l'option à l'URL de l'API
                apiUrlBuilder.append("&refine=services_service%3A").append(encodedOption);
                System.out.println(apiUrlBuilder);
            }
        }
        else {
            apiUrlBuilder.append("&refine=").append(type).append("%3A%22").append(encodedLocation).append("%22");
        }
        return apiUrlBuilder.toString();
    }
}
