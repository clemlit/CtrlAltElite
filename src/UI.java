import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Color;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.border.LineBorder;

public class UI implements ActionListener {
    private ArrayList<String> choix_carburants;
    ButtonGroup echelle = new ButtonGroup();
    JPanel panelBanniere;
    JPanel panelFiltres;
    JPanel mapPanel;
    Carte map;
    private static List<Double> averagePrices = new ArrayList<>();
    private static List<Double> medianPrices = new ArrayList<>();
    private static List<Double> minPrices = new ArrayList<>();


    public static void main(String argv[]) {
        new UI();
    }

    /**
     * Constructeur de la classe UI qui initialise l'interface utilisateur
     * Initialise les composants Swing, définit les paramètres visuels et les
     * interactions
     */
    public UI() {
        
        FlatLightLaf.setup();
        choix_carburants = new ArrayList<>();

        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacLightLaf");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame f = new JFrame("ma fenetre");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(new Color(223, 226, 232));
        

        panelFiltres = new JPanel();
        panelFiltres.setLayout(new BoxLayout(panelFiltres, BoxLayout.PAGE_AXIS));
        panelFiltres.setBorder(new LineBorder(new Color(223, 226, 232))); // Ajout de la bordure grise
        panelFiltres.setBackground(new Color(245, 247, 250));

        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());

        mapPanel.setBackground(new Color(245, 247, 250));
        mapPanel.setBorder(new LineBorder(new Color(223, 226, 232))); // Ajout de la bordure grise

        JPanel panelBanniere = new JPanel();
        panelBanniere.setBackground(new Color(255, 255, 255));
        panelBanniere.setBorder(new LineBorder(new Color(223, 226, 232))); // Ajout de la bordure grise
        panelBanniere.setPreferredSize(new Dimension(f.getWidth() - 500, 100));

        // Crée les ComboBox multi-sélection pour les régions, départements et
        // carburants
        multiBox<String> comboFiltres = new multiBox<>();
        multiBox<String> comboRegion = new multiBox<>();
        multiBox<String> comboDepart = new multiBox<>();
        multiBox<String> comboCarbu = new multiBox<>();
        multiBox<String> comboOptions = new multiBox<>();

        String[] filtres = { "Prix moyen", "Prix median", "Prix minimum",
                "Nombre de stations qui proposent chaque type de carburant",
                "Nombre de stations qui proposent des services spécifiques" };

        for (String filtre : filtres) {
            comboFiltres.addItem(filtre);
        }

        String[] regions = {
                "Auvergne-Rhône-Alpes ", "Bourgogne-Franche-Comté ", "Bretagne ", "Centre-Val de Loire ", "Corse ",
                "Grand Est ", "Hauts-de-France ", "Île-de-France ", "Normandie ", "Nouvelle-Aquitaine ",
                "Occitanie ", "Pays de la Loire ", "Provence-Alpes-Côte d'Azur"
        };

        for (String region : regions) {
            comboRegion.addItem(region);
        }
        String[] departements = {
                "01 - Ain ", "02 - Aisne ", "03 - Allier ", "04 - Alpes-de-Haute-Provence ", "05 - Hautes-Alpes ",
                "06 - Alpes-Maritimes ", "07 - Ardèche ", "08 - Ardennes ", "09 - Ariège ", "10 - Aube ",
                "11 - Aude ", "12 - Aveyron ", "13 - Bouches-du-Rhône ", "14 - Calvados ", "15 - Cantal ",
                "16 - Charente ", "17 - Charente-Maritime ", "18 - Cher ", "19 - Corrèze ", "20 - Corse ",
                "21 - Côte-d'Or ", "22 - Côtes-d'Armor ", "23 - Creuse ", "24 - Dordogne ", "25 - Doubs ",
                "26 - Drôme ", "27 - Eure ", "28 - Eure-et-Loir ", "29 - Finistère ", "2A - Corse-du-Sud ",
                "2B - Haute-Corse ", "30 - Gard ", "31 - Haute-Garonne ", "32 - Gers ", "33 - Gironde ",
                "34 - Hérault ", "35 - Ille-et-Vilaine ", "36 - Indre ", "37 - Indre-et-Loire ", "38 - Isère ",
                "39 - Jura ", "40 - Landes ", "41 - Loir-et-Cher ", "42 - Loire ", "43 - Haute-Loire ",
                "44 - Loire-Atlantique ", "45 - Loiret ", "46 - Lot ", "47 - Lot-et-Garonne ", "48 - Lozère ",
                "49 - Maine-et-Loire ", "50 - Manche ", "51 - Marne ", "52 - Haute-Marne ", "53 - Mayenne ",
                "54 - Meurthe-et-Moselle ", "55 - Meuse ", "56 - Morbihan ", "57 - Moselle ", "58 - Nièvre ",
                "59 - Nord ", "60 - Oise ", "61 - Orne ", "62 - Pas-de-Calais ", "63 - Puy-de-Dôme ",
                "64 - Pyrénées-Atlantiques ", "65 - Hautes-Pyrénées ", "66 - Pyrénées-Orientales ",
                "67 - Bas-Rhin ", "68 - Haut-Rhin ", "69 - Rhône ", "70 - Haute-Saône ", "71 - Saône-et-Loire ",
                "72 - Sarthe ", "73 - Savoie ", "74 - Haute-Savoie ", "75 - Paris ", "76 - Seine-Maritime ",
                "77 - Seine-et-Marne ", "78 - Yvelines ", "79 - Deux-Sèvres ", "80 - Somme ", "81 - Tarn ",
                "82 - Tarn-et-Garonne ", "83 - Var ", "84 - Vaucluse ", "85 - Vendée ", "86 - Vienne ",
                "87 - Haute-Vienne ", "88 - Vosges ", "89 - Yonne ", "90 - Territoire de Belfort ",
                "91 - Essonne ", "92 - Hauts-de-Seine ", "93 - Seine-Saint-Denis ", "94 - Val-de-Marne ",
                "95 - Val-d'Oise "
        };

        for (String departement : departements) {
            comboDepart.addItem(departement);
        }

        String[] carburants = { "Gazole ", "SP95 ", "SP98 ", "E10 ", "E85 ", "GPLc" };
        for (String carburant : carburants) {
            comboCarbu.addItem(carburant);
        }

        String[] options = { "Wifi", "Boutique alimentaire ", "Station de gonflage ", "Lavage automatique ",
                "Bornes électrique ", "Automate CB 24/24 ",
                "DAB (Distributeur automatique de billets) ", "Espace bébé ", "Toilettes publiques " };

        for (String option : options) {
            comboOptions.addItem(option);
        }

        // Définit la taille préférée des ComboBox
        comboFiltres.setPreferredSize(new Dimension(350, 40));
        comboRegion.setPreferredSize(new Dimension(250, 40));
        comboDepart.setPreferredSize(new Dimension(250, 40));
        comboCarbu.setPreferredSize(new Dimension(250, 40));
        comboOptions.setPreferredSize(new Dimension(250, 40));

        JLabel labeltitre1 = new JLabel("<html><b><h1>FILTRES</h1></b></html>");
        labeltitre1.setBorder(BorderFactory.createEmptyBorder(10, 120, 100, 10));
        JLabel labelFiltres = new JLabel("<html><b>Sélectionnez des filtres</b></html>");
        labelFiltres.setBorder(BorderFactory.createEmptyBorder(10, 95, 20, 10));
        JLabel labelRegions = new JLabel("<html><b>Sélectionnez des régions</b></html>");
        labelRegions.setBorder(BorderFactory.createEmptyBorder(20, 95, 20, 10));
        JLabel labelDepartements = new JLabel("<html><b>Sélectionnez des départements</b></html>");
        labelDepartements.setBorder(BorderFactory.createEmptyBorder(20, 85, 20, 10));
        JLabel labelCarburants = new JLabel("<html><b>Sélectionnez des carburants</b></html>");
        labelCarburants.setBorder(BorderFactory.createEmptyBorder(20, 87, 20, 10));
        JLabel labelOptions = new JLabel("<html><b>Sélectionnez des options</b></html>");
        labelOptions.setBorder(BorderFactory.createEmptyBorder(20, 91, 20, 10));
        JLabel empty = new JLabel("");
        empty.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Pour l'alignement
        Box boxCombos = Box.createVerticalBox();
        boxCombos.add(labeltitre1);
        boxCombos.add(labelFiltres);
        boxCombos.add(comboFiltres);
        boxCombos.add(labelRegions);
        boxCombos.add(comboRegion);
        boxCombos.add(labelDepartements);
        boxCombos.add(comboDepart);
        boxCombos.add(labelCarburants);
        boxCombos.add(comboCarbu);
        boxCombos.add(labelOptions);
        boxCombos.add(comboOptions);
        boxCombos.add(empty);

        panelFiltres.add(boxCombos);
        boxCombos.setPreferredSize(new Dimension(450, boxCombos.getPreferredSize().height)); // Adjust width as needed

        panelFiltres.setLayout(new FlowLayout(FlowLayout.CENTER));

        BorderLayout layout = new BorderLayout();
        layout.setVgap(5); // Ajuste la marge verticale entre les composants
        f.setLayout(layout);
        f.add(panelBanniere, BorderLayout.NORTH);
        f.add(panelFiltres, BorderLayout.WEST);
        f.add(mapPanel, BorderLayout.CENTER);

        panelBanniere.setSize(new Dimension(500, 100));

        f.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height); // Taille de l'écran
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximisation de la fenêtre

        f.setVisible(true);

        JButton boutonResultat = new JButton("Resultat");
        boutonResultat.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                Map<String, List<String>> criteria = new HashMap<>();
                StringBuilder htmlContentOption = new StringBuilder();
                StringBuilder htmlContentRegion = new StringBuilder();
                StringBuilder htmlContentDep = new StringBuilder();
                StringBuilder htmlContentCarb = new StringBuilder();
                StringBuilder htmlContentFiltres = new StringBuilder();
                StringBuilder htmlContentAucuneOption = new StringBuilder(); // Je rajoute ces quatre StringBuilder au cas où l'utilisateur n'aurait pas sélectionné d'option
                StringBuilder htmlContentAucuneRegion = new StringBuilder(); // Ca contourne un problème d'esthétique et surtout si on voulait prendre les données dans les autre StringBuilder,
                StringBuilder htmlContentAucunDepartement = new StringBuilder(); // Ici, on aurait une erreur car il y aurait "Aucune ... selectionnée" en plus des données.
                StringBuilder htmlContentAucunCarburant = new StringBuilder();
                
                averagePrices.clear();
                medianPrices.clear();
                minPrices.clear();


                List<Object> selectedOptions = comboOptions.getSelectedItems();
                List<String> selectedOptionsNames = new ArrayList<>();
                for (Object option : selectedOptions) {
                    selectedOptionsNames.add(option.toString());
                }

                // Vérifier si des options ont été sélectionnées
                if (!selectedOptionsNames.isEmpty()) {
                    criteria.put("option", selectedOptionsNames);
                    for (String option : selectedOptionsNames) {
                        htmlContentOption.append("<p>").append(option).append("</p>");
                    }
                }

                else {
                    htmlContentAucuneOption.append("<p>").append("Aucune option sélectionnée").append("</p>");
                }

                List<Object> selectedRegions = comboRegion.getSelectedItems();
                List<String> selectedRegionNames = new ArrayList<>();
                for (Object region : selectedRegions) {
                    selectedRegionNames.add(region.toString());
                }
                if (!selectedRegionNames.isEmpty()) {
                    criteria.put("region", selectedRegionNames);
                    for (Object region : selectedRegionNames) {
                        htmlContentRegion.append("<p>").append(region.toString()).append("</p>");
                    }
                }
                
                else {
                    htmlContentAucuneRegion.append("<p>").append("Aucune région sélectionnée").append("</p>");
                }

                List<Object> selectedDepartements = comboDepart.getSelectedItems();
                List<String> selectedDepartementNames = new ArrayList<>();
                for (Object departement : selectedDepartements) {
                    String fullDepartementName = departement.toString();
                    String[] parts = fullDepartementName.split(" - ", 2); // Sépare la chaîne au premier " - "
                    if (parts.length > 1) {
                        String cleanedDepartementName = parts[1]; // Récupère la partie après le " - "
                        selectedDepartementNames.add(cleanedDepartementName);
                    }
                }
                if (!selectedDepartementNames.isEmpty()) {
                    criteria.put("departement", selectedDepartementNames);
                    for (Object departement : selectedDepartementNames) {
                        // Ajoute les données sélectionnées dans le multiBox "comboDepart"
                        htmlContentDep.append("<p>").append(departement.toString()).append("</p>");
                    }
                }

                else {
                    htmlContentAucunDepartement.append("<p>").append("Aucun département sélectionné").append("</p>");
                }

                List<Object> selectedCarburants = comboCarbu.getSelectedItems();
                List<String> selectedCarburantNames = new ArrayList<>();
                for (Object carburant : selectedCarburants) {
                    selectedCarburantNames.add(carburant.toString());
                }
                if (!selectedCarburantNames.isEmpty()) {
                    criteria.put("carburant", selectedCarburantNames);
                    for (Object carburant : selectedCarburantNames) {
                        // Ajoute les données sélectionnées dans le multiBox "comboCarbu"
                        htmlContentCarb.append("<p>").append(carburant.toString()).append("</p>");
                    }
                }
                    
                else {
                    htmlContentAucunCarburant.append("<p>").append("Aucun carburant sélectionné").append("</p>");
                }


                if (comboFiltres.getSelectedItems().contains("Prix moyen")) {
                    List<String> prixMoyenOption = new ArrayList<>();
                    prixMoyenOption.add("Prix moyen");
                    criteria.put("filtre", prixMoyenOption);
                }

                if (comboFiltres.getSelectedItems().contains("Prix median")) {
                    List<String> prixMedianOption = new ArrayList<>();
                    prixMedianOption.add("Prix median");
                    criteria.put("filtre", prixMedianOption);
                }

                if (comboFiltres.getSelectedItems().contains("Prix minimum")) {
                    List<String> prixMinOption = new ArrayList<>();
                    prixMinOption.add("Prix minimum");
                    criteria.put("filtre", prixMinOption);
                }

                if (comboFiltres.getSelectedItems().contains("Nombre de stations qui proposent chaque type de carburant")) {
                    List<String> NbreStationCarburants = new ArrayList<>();
                    NbreStationCarburants.add("Nombre de stations qui proposent chaque type de carburant");
                    criteria.put("filtre", NbreStationCarburants);
                }

                if (comboFiltres.getSelectedItems().contains("Nombre de stations qui proposent des services spécifiques")) {
                    List<String> NbreStationServices = new ArrayList<>();
                    NbreStationServices.add("Nombre de stations qui proposent des services spécifiques");
                    criteria.put("filtre", NbreStationServices);

                }
                
                averagePrices = API.getAveragePrices();
                medianPrices = API.getMedianPrices();
                minPrices = API.getMinPrices(); 
                
                // Vérifier si des critères ont été sélectionnés
                if (!criteria.isEmpty()) {
                    API.retrieveFuelDataByLocation(criteria);
                }
                
                choix_carburants.clear();
                List<Object> selectedItems = comboCarbu.getSelectedItems();
                for (Object item : selectedItems) {
                    if (item instanceof String) {
                        choix_carburants.add((String) item);
                    }
                }
                int nbCarburants = choix_carburants.size();
                int nbDepartements = selectedDepartementNames.size();
                int nbRegions = selectedRegionNames.size();


                if (API.getAveragePrices().size() > 0){

                    if (nbRegions > 0) {
                        htmlContentFiltres.append("<h4>Prix moyens :</h4>");
                        htmlContentFiltres.append("<ul>");

                        // Boucle sur les régions
                        for (int i = 0; i < nbRegions; i++) {
                            String region = selectedRegionNames.get(i);
                            htmlContentFiltres.append("<li>").append("Région : ").append(region).append("</li>");
                            htmlContentFiltres.append("<ul>");

                            // Boucle sur les carburants
                            for (int j = 0; j < nbCarburants; j++) {
                                String carburant = choix_carburants.get(j);
                                // Trouver l'index correspondant dans la liste des prix moyens
                                int index = i * nbCarburants + j;
                                // Vérifier si l'index est valide pour éviter les dépassements d'index
                                if (index < API.getAveragePrices().size()) {
                                    double prixMoyen = API.getAveragePrices().get(index);
                                    htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                            .append(" - Prix moyen : ").append(prixMoyen).append("€</li>");
                                } else {
                                    htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                            .append(" - Prix moyen : Non disponible</li>");
                                }
                            }

                            htmlContentFiltres.append("</ul>");
                        }

                        htmlContentFiltres.append("</ul>");
                    }

                    if (nbDepartements > 0) {
                        htmlContentFiltres.append("<h4>Prix moyens :</h4>");
                        htmlContentFiltres.append("<ul>");

                        // Boucle sur les départements
                        for (int i = 0; i < nbDepartements; i++) {
                            String departement = selectedDepartementNames.get(i);
                            htmlContentFiltres.append("<li>").append("Département : ").append(departement)
                                    .append("</li>");
                            htmlContentFiltres.append("<ul>");

                            // Boucle sur les carburants
                            for (int j = 0; j < nbCarburants; j++) {
                                String carburant = choix_carburants.get(j);
                                // Trouver l'index correspondant dans la liste des prix moyens
                                int index = i * nbCarburants + j;
                                // Vérifier si l'index est valide pour éviter les dépassements d'index
                                if (index < API.getAveragePrices().size()) {
                                    double prixMoyen = API.getAveragePrices().get(index);
                                    htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                            .append(" - Prix moyen : ").append(prixMoyen).append("€</li>");
                                } else {
                                    htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                            .append(" - Prix moyen : Non disponible</li>");
                                }
                            }

                            htmlContentFiltres.append("</ul>");
                        }

                        htmlContentFiltres.append("</ul>");
                    }

                }

                if (API.getMedianPrices().size() > 0) {

                    htmlContentFiltres.append("<h4>Prix médian :</h4>");
                    htmlContentFiltres.append("<ul>");

                    if (nbRegions > 0) {
                        // Boucle sur les régions
                        for (int i = 0; i < nbRegions; i++) {
                            String region = selectedRegionNames.get(i);
                            htmlContentFiltres.append("<li>").append("Région : ").append(region).append("</li>");
                            htmlContentFiltres.append("<ul>");

                            // Boucle sur les carburants
                            for (int j = 0; j < nbCarburants; j++) {
                                String carburant = choix_carburants.get(j);
                                double prixMedian = medianPrices.get(i * nbCarburants + j);
                                htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                        .append(" - Prix médian : ").append(prixMedian).append("€</li>");
                            }

                            htmlContentFiltres.append("</ul>");
                        }
                    }

                    if (nbDepartements > 0) {
                        // Boucle sur les départements
                        for (int i = 0; i < nbDepartements; i++) {
                            String departement = selectedDepartementNames.get(i);
                            htmlContentFiltres.append("<li>").append("Département : ").append(departement)
                                    .append("</li>");
                            htmlContentFiltres.append("<ul>");

                            // Boucle sur les carburants
                            for (int j = 0; j < nbCarburants; j++) {
                                String carburant = choix_carburants.get(j);
                                double prixMedian = medianPrices.get(i * nbCarburants + j);
                                htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                        .append(" - Prix médian : ").append(prixMedian).append("€</li>");
                            }

                            htmlContentFiltres.append("</ul>");
                        }
                    }

                    htmlContentFiltres.append("</ul>");
                }



                if (API.getMinPrices().size() > 0) {
                    htmlContentFiltres.append("<h4>Prix minimum :</h4>");
                    htmlContentFiltres.append("<ul>");

                    if (nbRegions > 0) {
                        // Boucle sur les régions
                        for (int i = 0; i < nbRegions; i++) {
                            String region = selectedRegionNames.get(i);
                            htmlContentFiltres.append("<li>").append("Région : ").append(region).append("</li>");
                            htmlContentFiltres.append("<ul>");

                            // Boucle sur les carburants
                            for (int j = 0; j < nbCarburants; j++) {
                                String carburant = choix_carburants.get(j);
                                double prixMin = minPrices.get(i * nbCarburants + j);
                                htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                        .append(" - Prix minimum : ").append(prixMin).append("€</li>");
                            }

                            htmlContentFiltres.append("</ul>");
                        }
                    }

                    if (nbDepartements > 0) {
                        // Boucle sur les départements
                        for (int i = 0; i < nbDepartements; i++) {
                            String departement = selectedDepartementNames.get(i);
                            htmlContentFiltres.append("<li>").append("Département : ").append(departement)
                                    .append("</li>");
                            htmlContentFiltres.append("<ul>");

                            // Boucle sur les carburants
                            for (int j = 0; j < nbCarburants; j++) {
                                String carburant = choix_carburants.get(j);
                                double prixMin = minPrices.get(i * nbCarburants + j);
                                htmlContentFiltres.append("<li>").append("Carburant : ").append(carburant)
                                        .append(" - Prix minimum : ").append(prixMin).append("€</li>");
                            }

                            htmlContentFiltres.append("</ul>");
                        }
                    }

                    htmlContentFiltres.append("</ul>");
                }

                // Écrire les données dans un fichier HTML
                try {
                    FileWriter writer = new FileWriter("src/page_Web/resultat.html");
                    writer.write("<html><head>");
                    writer.write("<meta charset='UTF-8'>");
                    writer.write("<title>Résultats</title>");
                    writer.write("<style>");
                    writer.write("@import url('https://fonts.googleapis.com/css2?family=Space+Mono:ital,wght@0,400;0,700;1,400;1,700&display=swap');");
                    writer.write("body {");
                    writer.write("    display:flex;");
                    writer.write("    margin:0;");
                    writer.write("    height: 100vh;"); // 100% de la hauteur de la vue (viewport)
                    writer.write("}");
                    writer.write("#liste {");
                    writer.write("    align-items: center;"); // Police de caractères
                    writer.write("    font-family: 'Space Mono', monospace;"); // Police de caractères
                    writer.write("    display: flex;");
                    writer.write("    width:500px;");
                    writer.write("    flex-direction: column;"); // Alignement vertical
                    writer.write("    padding: 32px;");
                    writer.write("    max-width: 500px;");
                    writer.write("    margin: auto;");
                    writer.write("    margin-right: -150px;");
                    writer.write("    border: 1px solid #eee;");
                    writer.write("    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.06);");
                    writer.write("}");
                    writer.write("#graphiques {");
                    writer.write("    font-family: 'Space Mono', monospace;"); // Police de caractères
                    writer.write("    flex: 1;"); // Prend tout l'espace restant
                    writer.write("    padding: 32px;");
                    writer.write("}");
                    writer.write("#graphiques h2 {");
                    writer.write("    margin-left: 100px;");
                    writer.write("    text-align: center;");
                    writer.write("}");
                    writer.write("#graphiques iframe {");
                    writer.write("    margin-right: -200px;");
                    writer.write("    margin-bottom: -150px;");
                    writer.write("    border: none;"); // Supprime la bordure de l'iframe
                    writer.write("}");
                    writer.write("* {-webkit-font-smoothing: antialiased;-moz-osx-font-smoothing: grayscale;text-rendering: optimizelegibility;letter-spacing: -0.25px;}");
                    writer.write("ol { padding-left: 50px; }");
                    writer.write("li {");
                    writer.write("color: #4F4F4F; padding-left: 16px; margin-top: 24px; position: relative; font-size: 16px; line-height: 20px; ");
                    writer.write("}");
                    writer.write("li:before {");
                    writer.write("content: ''; display: block; height: 42px; width: 42px; border-radius: 50%; border: 2px solid #ddd; position: absolute; top: -12px; left: -46px;");
                    writer.write("}");
                    writer.write("strong { color:#000000; }");

                    writer.write("#graphiques h4 {");
                    writer.write("    padding-left: 200px;");
                    writer.write("    text-align: center;");
                    writer.write("    margin: auto;");
                    writer.write("}");


                    writer.write("</style>");
                    writer.write("</head><body>");

                    writer.write("<div id=\"liste\">");
                    writer.write("<strong><u><h2>Vos choix :</h2></u></strong>");
                    writer.write("<ol> <li> <strong> <h3>Options</h3> </strong>");
                    writer.write(htmlContentOption.toString());
                    writer.write(htmlContentAucuneOption.toString());
                    writer.write("</li> <li> <strong><h3>Régions</h3> </strong>");
                    writer.write(htmlContentRegion.toString());
                    writer.write(htmlContentAucuneRegion.toString());
                    writer.write("</li> <li> <strong><h3>Départements </h3></strong>");
                    writer.write(htmlContentDep.toString());
                    writer.write(htmlContentAucunDepartement.toString());
                    writer.write("</li> <li> <strong><h3>Carburants</h3> </strong>");
                    writer.write(htmlContentCarb.toString());
                    writer.write(htmlContentAucunCarburant.toString());
                    writer.write("</li> </ol>");
                    writer.write("</div>");
                    writer.write("<div id=\"graphiques\"><b><u><h2>Graphiques et filtres</h2></u></b>");

                    if (comboFiltres.getSelectedItems().contains("Nombre de stations qui proposent des services spécifiques")){
                        writer.write(
                                "<iframe src=\"DiagrammeCammembertServices.svg\" width=\"800\" height=\"600\" sandbox></iframe>");
                    }else if (comboFiltres.getSelectedItems()
                            .contains("Nombre de stations qui proposent chaque type de carburant")){
                                writer.write(
                                "<img src=\"DiagrammeCammembertCarburants.svg\" width=\"800\" height=\"600\"></iframe>");
                                writer.write(
                                "<iframe src=\"DiagrammeCammembertAllCarburants.svg\" width=\"800\" height=\"600\"></iframe>");
                            }
                    writer.write(htmlContentFiltres.toString());
                    writer.write("</div>");
                    writer.write("</body></html>");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // Ouvrir le fichier HTML dans le navigateur
                openWebPage("src/page_Web/resultat.html");
            }
        });
        panelFiltres.add(boutonResultat);
        String i ="0";
        Carte map = new Carte(i);
        mapPanel.add(map.getUI(), BorderLayout.CENTER);


    }

    /**
     * Ouvre une page web dans le navigateur par défaut à partir de l'URL spécifiée.
     *
     * @param url L'URL de la page web à ouvrir.
     */
    private void openWebPage(String url) {
        try {
            File htmlFile = new File(url);
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void actionPerformed(ActionEvent event) {
        panelFiltres.revalidate();
        panelFiltres.repaint();
    }

}
