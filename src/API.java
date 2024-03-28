import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class API {
    private static final String BASE_URL = "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Veuillez choisir l'option (ville/departement/region/France) :");
            String type = scanner.nextLine();
            String location;
            if (type.equals("France")) {
                location = "";
            } else {
                System.out.println("Veuillez entrer le nom de la " + type + " :");
                location = scanner.nextLine();
            }
            System.out.println("Vouillez choisir les services que vous souhaite avoir :");
            String[] services = {
                    "Vente de gaz domestique (Butane, Propane)",
                    "Station de gonflage",
                    "DAB (Distributeur automatique de billets)",
                    "Automate CB 24/24",
                    "Boutique alimentaire",
                    "Piste poids lourds",
                    "Boutique non alimentaire",
                    "Carburant additivé",
                    "Lavage automatique",
                    "Toilettes publiques",
                    "Lavage manuel",
                    "Restauration à emporter",
                    "Location de véhicule",
                    "Relais colis",
                    "Laverie",
                    "Vente d'additifs carburants",
                    "Services réparation / entretien",
                    "Restauration sur place",
                    "Wifi",
                    "Vente de fioul domestique",
                    "Vente de pétrole lampant",
                    "Bornes électriques",
                    "Bar",
                    "Espace bébé",
                    "Douches",
                    "Aire de camping-cars",
                    "GNV"
            };

            // Liste des services choisis
            ArrayList<String> servicesChoisis = new ArrayList<>();

            // Demande à l'utilisateur quels services il souhaite avoir
            System.out.println("Quels services souhaitez-vous avoir ? (Entrez les numéros séparés par des virgules)");
            for (int i = 0; i < services.length; i++) {
                System.out.println((i+1) + ". " + services[i]);
            }
            System.out.print("Votre choix : ");

            // Lecture de la saisie utilisateur
            String choixUtilisateur = scanner.nextLine();

            // Traitement de la saisie utilisateur
            String[] numerosServices = choixUtilisateur.split(",");
            for (String numero : numerosServices) {
                int index = Integer.parseInt(numero.trim()) - 1;
                if (index >= 0 && index < services.length) {
                    servicesChoisis.add(services[index]);
                }
            }

            // Affichage des services choisis
            System.out.println("Vous avez choisi les services suivants :");
            for (String service : servicesChoisis) {
                System.out.println("- " + service);
            }



            System.out.println("Choisissez le ou les carburants séparés par des virgules (gazole/gplc/e10/sp95/sp98/e85/tous) :");
            String carburant = scanner.nextLine();
            List<String> carburants = Arrays.asList(carburant.split(","));
            if (carburants.contains("tous")) {
                carburants = Arrays.asList("gazole", "gplc", "e10", "sp95", "sp98", "e85");
            }
            System.out.println("Choisissez le ou les types de calcul séparés par des virgules (avg/min/median/count/tous) :");
            String cal = scanner.nextLine();
            List<String> calList = Arrays.asList(cal.split(","));
            if (calList.contains("tous")) {
                calList = Arrays.asList("avg", "min", "median", "count");
            }
            boolean showAdresseMin = false;
            if (calList.contains("min")) {
                System.out.println("Voulez-vous connaître l'adresse de la station avec le prix minimum ? (oui/non)");
                String adresseMin = scanner.nextLine();
                showAdresseMin = adresseMin.equalsIgnoreCase("oui");
            }

            JSONObject jsonData = requeteHttp(type, location, carburants, calList, showAdresseMin,servicesChoisis);
            extractFuelPrices(jsonData, carburants, calList);
        }
    }
    public static String buildURL(String type, String location, String carburant, List<String> calList, int x,List<String> servicesChoisis) {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("select=adresse,ville");
        if (x == 1) {
            for (String cal : calList) {
                urlBuilder.append(",");
                urlBuilder.append(cal).append("(").append(carburant).append("_prix)");
            }
        } else {
            urlBuilder.append(",");
            urlBuilder.append(carburant).append("_prix");
        }
        if (servicesChoisis != null && !servicesChoisis.isEmpty() && !type.equals("France") ) {
            urlBuilder.append("&where=");
        }
        if (!type.equals("France")) {
            urlBuilder.append(type).append("%3D%22").append(location).append("%22");
        }
        if (servicesChoisis != null && !servicesChoisis.isEmpty()) {
            for (String service : servicesChoisis) {
                urlBuilder.append("and%20").append("services_service%3D%22").append(service.replace(" ", "%20")).append("%22");
            }
        }
        urlBuilder.append("&order_by=").append(carburant).append("_prix%20asc");
        urlBuilder.append("&limit=1");
        return urlBuilder.toString();
    }
    public static void extractFuelPrices(JSONObject json, List<String> carburants, List<String> calList) {
        for (String carburant : carburants) {
            System.out.println();
            System.out.println("Carburant : " + carburant.split("_")[0].toUpperCase());
            extractFuelPrice(json, carburant + "_avec_adresse_min", calList,true);
            extractFuelPrice(json, carburant + "_sans_adresse_min", calList,false);
        }
    }
    private static void extractFuelPrice(JSONObject json, String key, List<String> calList,boolean affiche) {
        if (json.has(key)) {
            JSONObject carburantData = json.getJSONObject(key);
            JSONArray results = carburantData.getJSONArray("results");
            if (affiche) {
                if (!results.isEmpty()) {
                    JSONObject record = results.getJSONObject(0);
                    String adresse = record.optString("adresse", "Adresse non disponible");
                    String ville = record.optString("ville","ville non disponible");
                    System.out.println("L'adresse de la station avec le prix minimum est " + adresse + " dans la ville de " + ville);
                }
            }
            else {
                for (String cal : calList) {
                    if (!cal.equals("count")) {
                        String fieldName = cal + "(" + key.split("_")[0] + "_prix)";
                        if (!results.isEmpty() && results.getJSONObject(0).has(fieldName) ) {
                            double prix = results.getJSONObject(0).getDouble(fieldName);
                            Object prixObj = results.getJSONObject(0).get(fieldName);
                            if (prixObj != null && prixObj instanceof Number) {
                                double prix2 = ((Number) prixObj).doubleValue();
                                System.out.println("Prix " + cal + " : " + String.format("%.2f", prix2));
                            } else {
                                System.out.println("Prix " + cal + " non disponible");
                            }
                        } else {
                            System.out.println("Prix " + cal + " non disponible");
                        }
                    } else {
                        if (!results.isEmpty() && results.getJSONObject(0).has("count(" + key.split("_")[0] + "_prix)")) {
                            double nb = results.getJSONObject(0).getDouble("count(" + key.split("_")[0] + "_prix)");
                            System.out.println("Nombre de stations : " + String.format("%.0f", nb));
                        } else {
                            System.out.println("Nombre de stations non disponible");
                        }
                    }
                }
            }
        }
    }
    public static JSONObject requeteHttp(String type, String location, List<String> carburants, List<String>calList, boolean showAdresseMin,List<String> serviceChoisis) {
        ArrayList<String> m = new ArrayList<>(calList);
        HttpClient client = HttpClient.newHttpClient();
        JSONObject result = new JSONObject();

        for (String carburant : carburants) {
            for (int x = showAdresseMin ? 0 : 1; x < 2; x++) {
                List<String> currentCalList = (x == 0) ? new ArrayList<>() : m;
                String url = buildURL(type, location, carburant, currentCalList, x,serviceChoisis);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                try {
                    String jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
                    JSONObject json = new JSONObject(jsonResponse);
                    String key = x==0 ? carburant + "_avec_adresse_min" : carburant + "_sans_adresse_min";
                    result.put(key, json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Files.writeString(Paths.get("prixEssence.json"), result.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}