import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class API {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir l'option (ville/departement/region/France) :");
        String type = scanner.nextLine();
        String location;
        if (type.equals("France")) {
            location = "";
        } else {
            System.out.println("Veuillez entrer le nom de la " + type + " :");
            location = scanner.nextLine();
        }

        System.out.println("Choisissez le ou less carburants séparés par des virgules (gazole/gplc/e10/sp95/sp98/e85/tous) :");
        String carburantsInput = scanner.nextLine();
        String[] carburants = carburantsInput.split(",");
        System.out.println("Choisissez le ou les type de calcul séparés par des virgules (avg/min/median/tous) :");
        String cal = scanner.nextLine();
        List<String> calList = Arrays.asList(cal.split(","));
        if (calList.contains("tous")) {
            calList = Arrays.asList("avg", "min", "median");
        }




        HttpClient client = HttpClient.newHttpClient();
        System.out.println(type + " : " + location);
        for (String calType : calList) {
            
        
        String url = buildURL(type, location, calType);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(JSONObject::new) // Convert the response to JSONObject
                .thenAccept(json -> {
                    try {
                        // Write the JSONObject to a file
                        Files.writeString(Paths.get("prixEssence.json"), json.toString(4));

                        // Read the file
                        String content = new String(Files.readAllBytes(Paths.get("prixEssence.json")));
                        JSONObject jsonFromFile = new JSONObject(content);

                        // Extract and print fuel prices
                        extractFuelPrices(jsonFromFile, location, type, carburants, calType);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .join();
    }
}

    public static String buildURL(String type, String location, String cal) {
        String locationEncoded = URLEncoder.encode(location, StandardCharsets.UTF_8);
        locationEncoded = locationEncoded.replace("%C5%A0", "%C3%A8");
        String typeEncoded = URLEncoder.encode(type, StandardCharsets.UTF_8);
        typeEncoded = typeEncoded.replace("%C5%A0", "%C3%A8");

        String baseURL = "https://data.economie.gouv.fr/api/explore/v2.1/catalog/datasets/prix-des-carburants-en-france-flux-instantane-v2/records?";
        String select = "";
        String filter = "&where="+ typeEncoded +"%3D%22" + locationEncoded + "%22";
        String groupBy = "";
        String limit = "";


        if(cal.equals("avg")){
            select = "select=avg(gazole_prix),avg(sp95_prix),avg(sp98_prix),avg(e85_prix),avg(gplc_prix),avg(e10_prix)";
        }
        else if(cal.equals("min")){
            select = "select=min(gazole_prix),min(sp95_prix),min(sp98_prix),min(e85_prix),min(gplc_prix),min(e10_prix)";
        }
        else if(cal.equals("median")){
            select = "select=median(gazole_prix),median(sp95_prix),median(sp98_prix),median(e85_prix),median(gplc_prix),median(e10_prix)";
        }
        else{
            System.out.println("Type de calcul non reconnu");
            System.exit(0);
        }
        if (type.equals("France")) {
            filter = "";
            limit = "&limit=1";
        }
        else if(type.equals("region")){
            groupBy = "&group_By=region";
        }
        else if(type.equals("departement")){
            groupBy = "&group_By=departement";
        }
        else{
            groupBy = "&group_By=ville";
        }
        return baseURL + select + filter + groupBy + limit;
    }

    public static void extractFuelPrices(JSONObject json, String location, String type, String[] carburants, String cal) {
        if (json.has("results")) {
            JSONArray records = json.getJSONArray("results");
            for (int i = 0; i < records.length(); i++) {
                JSONObject record = records.getJSONObject(i);
                System.out.println();
                for (String carburant : carburants) {
                    switch (carburant.trim().toLowerCase()) {
                        case "gazole":
                            System.out.println("Prix " + cal + " du gazole : " + String.format("%.2f", record.getDouble(cal+"(gazole_prix)")));
                            break;
                        case "sp95":
                            System.out.println("Prix " + cal + " du SP95 : " + String.format("%.2f", record.getDouble(cal+"(sp95_prix)")));
                            break;
                        case "sp98":
                            System.out.println("Prix " + cal + " du SP98 : " + String.format("%.2f", record.getDouble(cal+"(sp98_prix)")));
                            break;
                        case "e85":
                            System.out.println("Prix " + cal + " du E85 : " + String.format("%.2f", record.getDouble(cal+"(e85_prix)")));
                            break;
                        case "gplc":
                            System.out.println("Prix " + cal + " du GPLC : " + String.format("%.2f", record.getDouble(cal+"(gplc_prix)")));
                            break;
                        case "e10":
                            System.out.println("Prix " + cal + " du E10 : " + String.format("%.2f", record.getDouble(cal+"(e10_prix)")));
                            break;
                        default:
                            System.out.println("Carburant non reconnu : " + carburant.trim());
                            break;
                        case "tous":
                            System.out.println("Prix " + cal + " du gazole : " + String.format("%.2f", record.getDouble(cal+"(gazole_prix)")));
                            System.out.println("Prix " + cal + " du SP95 : " + String.format("%.2f", record.getDouble(cal+"(sp95_prix)")));
                            System.out.println("Prix " + cal + " du SP98 : " + String.format("%.2f", record.getDouble(cal+"(sp98_prix)")));
                            System.out.println("Prix " + cal + " du E85 : " + String.format("%.2f", record.getDouble(cal+"(e85_prix)")));
                            System.out.println("Prix " + cal + " du GPLC : " + String.format("%.2f", record.getDouble(cal+"(gplc_prix)")));
                            System.out.println("Prix " + cal + " du E10 : " + String.format("%.2f", record.getDouble(cal+"(e10_prix)")));
                            break;
                    }
                }
            }
        } else {
            System.out.println("Aucun résultat trouvé.");
        }
    }
    
}