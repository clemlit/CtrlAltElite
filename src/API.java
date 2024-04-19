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
                System.out.println("Veuillez choisir l'option (ville/departement/region/France/quitter) :");
                String option = scanner.nextLine().toLowerCase();

                switch (option) {
                    case "ville":
                        System.out.println("Veuillez saisir le nom de la ville :");
                        String ville = scanner.nextLine();
                        retrieveFuelDataByLocation("ville", ville);
                        break;
                    case "departement":
                        System.out.println("Veuillez saisir le département (ex : Gironde) :");
                        String departement = scanner.nextLine();
                        retrieveFuelDataByLocation("departement", departement);
                        break;
                    case "region":
                        System.out.println("Veuillez saisir le nom de la région (ex: Nouvelle-Aquitaine) :");
                        String region = scanner.nextLine();
                        retrieveFuelDataByLocation("region", region);
                        break;
                    case "france":
                        retrieveFuelDataByLocation("France", "France");
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

    public static void retrieveFuelDataByLocation(String type, String location) {
        // Supprimez les espaces inutiles en utilisant trim()
        String cleanedLocation = location.trim();
        String apiUrl = buildApiUrl(type, cleanedLocation);
        System.out.println(apiUrl);

        try {
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

    private static String buildApiUrl(String type, String location) {
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
        return API_URL + "&refine=" + type + "%3A%22" + encodedLocation + "%22";
    }
}
