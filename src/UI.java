import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.border.LineBorder;

public class UI implements ActionListener {
    ButtonGroup echelle = new ButtonGroup();
    JPanel panelBanniere;
    JPanel panelFiltres;
    JPanel mapPanel;
    JButton themeButton;

    public static void main(String argv[]) {
        new UI();
    }

    public UI() {
        FlatLightLaf.setup();

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
        panelFiltres.setPreferredSize(new Dimension(300, f.getHeight()));

        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(new Color(245, 247, 250));
        mapPanel.setBorder(new LineBorder(new Color(223, 226, 232))); // Ajout de la bordure grise
        mapPanel.setMaximumSize(new Dimension(f.getWidth(), f.getHeight()));

        JPanel panelBanniere = new JPanel();
        panelBanniere.setBackground(new Color(255,255,255));
        panelBanniere.setBorder(new LineBorder(new Color(223, 226, 232))); // Ajout de la bordure grise
        panelBanniere.setPreferredSize(new Dimension(f.getWidth()-500, 100));

        // Ajout du bouton pour changer de thème
        themeButton = new JButton("Changer de thème");
        themeButton.addActionListener(this); // Ajout de l'action listener
        panelBanniere.add(themeButton); // Ajout du bouton au panneau bannière

        // Crée les ComboBox multi-sélection pour les régions, départements et carburants
        multiBox<String> comboFiltres = new multiBox<>();
        multiBox<String> comboRegion = new multiBox<>();
        multiBox<String> comboDepart = new multiBox<>();
        multiBox<String> comboCarbu = new multiBox<>();        
        multiBox<String> comboOptions = new multiBox<>();  

        String[] filtres = { "Prix moyen", "Prix médian  ", "Prix minimum  ", "Nombre de stations qui proposent chaque type de carburant  ", 
        "Nombre de stations qui proposent des services spécifiques  "};

        for (String filtre : filtres) {
            comboFiltres.addItem(filtre);
        }

        String[] regions = {
            "Auvergne-Rhône-Alpes  ", "Bourgogne-Franche-Comté  ", "Bretagne  ", "Centre-Val de Loire  ", "Corse  ",
            "Grand Est  ", "Hauts-de-France  ", "Île-de-France  ", "Normandie  ", "Nouvelle-Aquitaine  ",
            "Occitanie  ", "Pays de la Loire  ", "Provence-Alpes-Côte d'Azur"
        };

        for (String region : regions) {
            comboRegion.addItem(region);
        }
        String[] departements = {
            "01 - Ain  ", "02 - Aisne  ", "03 - Allier  ", "04 - Alpes-de-Haute-Provence  ", "05 - Hautes-Alpes  ",
            "06 - Alpes-Maritimes  ", "07 - Ardèche  ", "08 - Ardennes  ", "09 - Ariège  ", "10 - Aube  ",
            "11 - Aude  ", "12 - Aveyron  ", "13 - Bouches-du-Rhône  ", "14 - Calvados  ", "15 - Cantal  ",
            "16 - Charente  ", "17 - Charente-Maritime  ", "18 - Cher  ", "19 - Corrèze  ", "20 - Corse  ",
            "21 - Côte-d'Or  ", "22 - Côtes-d'Armor  ", "23 - Creuse  ", "24 - Dordogne  ", "25 - Doubs  ",
            "26 - Drôme  ", "27 - Eure  ", "28 - Eure-et-Loir  ", "29 - Finistère  ", "2A - Corse-du-Sud  ",
            "2B - Haute-Corse  ", "30 - Gard  ", "31 - Haute-Garonne  ", "32 - Gers  ", "33 - Gironde  ",
            "34 - Hérault  ", "35 - Ille-et-Vilaine  ", "36 - Indre  ", "37 - Indre-et-Loire  ", "38 - Isère  ",
            "39 - Jura  ", "40 - Landes  ", "41 - Loir-et-Cher  ", "42 - Loire  ", "43 - Haute-Loire  ",
            "44 - Loire-Atlantique  ", "45 - Loiret  ", "46 - Lot  ", "47 - Lot-et-Garonne  ", "48 - Lozère  ",
            "49 - Maine-et-Loire  ", "50 - Manche  ", "51 - Marne  ", "52 - Haute-Marne  ", "53 - Mayenne  ",
            "54 - Meurthe-et-Moselle  ", "55 - Meuse  ", "56 - Morbihan  ", "57 - Moselle  ", "58 - Nièvre  ",
            "59 - Nord  ", "60 - Oise  ", "61 - Orne  ", "62 - Pas-de-Calais  ", "63 - Puy-de-Dôme  ",
            "64 - Pyrénées-Atlantiques  ", "65 - Hautes-Pyrénées  ", "66 - Pyrénées-Orientales  ",
            "67 - Bas-Rhin  ", "68 - Haut-Rhin  ", "69 - Rhône  ", "70 - Haute-Saône  ", "71 - Saône-et-Loire  ",
            "72 - Sarthe  ", "73 - Savoie  ", "74 - Haute-Savoie  ", "75 - Paris  ", "76 - Seine-Maritime  ",
            "77 - Seine-et-Marne  ", "78 - Yvelines  ", "79 - Deux-Sèvres  ", "80 - Somme  ", "81 - Tarn  ",
            "82 - Tarn-et-Garonne  ", "83 - Var  ", "84 - Vaucluse  ", "85 - Vendée  ", "86 - Vienne  ",
            "87 - Haute-Vienne  ", "88 - Vosges  ", "89 - Yonne  ", "90 - Territoire de Belfort  ",
            "91 - Essonne  ", "92 - Hauts-de-Seine  ", "93 - Seine-Saint-Denis  ", "94 - Val-de-Marne  ",
            "95 - Val-d'Oise  "
        };

        for (String departement : departements) {
            comboDepart.addItem(departement);
        }
        
        String[] carburants = { "Gazole  ", "SP95  ", "SP98  ", "E10  ", "E85  ", "GPLc"};
        for (String carburant : carburants) {
            comboCarbu.addItem(carburant);
        }       

        String[] options = { "Wi-fi  ", "Boutique alimentaire  ", "Station de gonflage  ", "Lavage automatique  ", "Bornes éléctrique  ", "Automate CB 24/24  ", 
        "DAB (Distributeur automatique de billets)  ", "Espace bébé  ", "Toilettes publiques  "};

        for (String option : options) {
            comboOptions.addItem(option);
        }

        // Définit la taille préférée des ComboBox
        comboFiltres.setPreferredSize(new Dimension(180, 40));
        comboRegion.setPreferredSize(new Dimension(180, 40));
        comboDepart.setPreferredSize(new Dimension(180, 40));
        comboCarbu.setPreferredSize(new Dimension(180, 40));
        comboOptions.setPreferredSize(new Dimension(180, 40));

        JLabel labeltitre1 = new JLabel("<html><b><h1>FILTRES</h1></b></html>");
        JLabel empty1 = new JLabel(" ");
        JLabel empty2 = new JLabel(" ");
        JLabel empty3 = new JLabel(" ");
        JLabel empty4 = new JLabel(" ");
        JLabel empty5 = new JLabel(" ");
        JLabel labelFiltres = new JLabel("<html><i>Sélectionnez des filtres</i></html>");
        JLabel labelRegions = new JLabel("<html><i>Sélectionnez des régions</i></html>");
        JLabel labelDepartements = new JLabel("<html><i>Sélectionnez des départements</i></html>");
        JLabel labelCarburants = new JLabel("<html><i>Sélectionnez des carburants</i></html>");
        JLabel labelOptions = new JLabel("<html><i>Sélectionnez des options</i></html>");

        // Pour l'alignement
        Box boxCombos = Box.createVerticalBox();
        boxCombos.add(labeltitre1);
        boxCombos.add(empty1);
        boxCombos.add(labelFiltres);
        boxCombos.add(comboFiltres);
        boxCombos.add(empty2);
        boxCombos.add(labelRegions);
        boxCombos.add(comboRegion);
        boxCombos.add(empty3);
        boxCombos.add(labelDepartements);
        boxCombos.add(comboDepart);
        boxCombos.add(empty4);
        boxCombos.add(labelCarburants);
        boxCombos.add(comboCarbu);
        boxCombos.add(empty5);
        boxCombos.add(labelOptions);
        boxCombos.add(comboOptions);

        panelFiltres.add(boxCombos);
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
        int width = screenSize.width * 50 / 100;
        int height = screenSize.height * 50 / 100;
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        JButton boutonResultat = new JButton("Resultat");
        boutonResultat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder htmlContent = new StringBuilder();
                for (java.awt.Component component : panelFiltres.getComponents()) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        if (checkBox.isSelected()) {
                            htmlContent.append("<li>").append(checkBox.getText()).append("</li>");
                        }
                    }
                }

                List<Object> selectedRegions = comboRegion.getSelectedItems();
                for (Object region : selectedRegions) {
                    // Ajoute les données sélectionnées dans le multiBox "comboRegion"
                    htmlContent.append("<h2> Région selectionné :</h2>");
                    htmlContent.append("<p>").append(region.toString()).append("</p>");
                }

                List<Object> selectedDepartements = comboDepart.getSelectedItems();
                for (Object departement : selectedDepartements) {
                    // Ajoute les données sélectionnées dans le multiBox "comboDepart"
                    htmlContent.append("<h2>Département selectionné :</h2>");
                    htmlContent.append("<p>").append(departement.toString()).append("</p>");
                }

                // Ajoute les données sélectionnées dans le multiBox "comboCarbu"
                List<Object> selectedCarbus = comboCarbu.getSelectedItems();
                for (Object carbu : selectedCarbus) {
                    htmlContent.append("<h2>Carburant selectionné : </h2>");
                    htmlContent.append("<p>").append(carbu.toString()).append("</p>");
                }

                // Écrire les données dans un fichier HTML
                try {
                    FileWriter writer = new FileWriter("src/page_Web/resultat.html");
                    writer.write("<html><head><title>Résultats</title>");
                    writer.write("<style>");
                    writer.write("body {");
                    writer.write("    display: flex;");
                    writer.write("    justify-content: center;"); // Centrer horizontalement
                    writer.write("    align-items: center;"); // Centrer verticalement
                    writer.write("    height: 100vh;"); // 100% de la hauteur de la vue (viewport)
                    writer.write("}");
                    writer.write("iframe {");
                    writer.write("    border: none;"); // Supprime la bordure de l'iframe
                    writer.write("}");
                    writer.write("</style>");
                    writer.write("</head><body>");

                    // Écrire l'iframe avec les styles pour centrer
                    writer.write("<div style=\"text-align: center;\">");
                    writer.write(
                            "<iframe src=\"DiagrammeCammembert.svg\" width=\"800\" height=\"600\" sandbox></iframe>");
                    writer.write("</div>");

                    writer.write(htmlContent.toString());
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

    }

    // Méthode pour ouvrir la page HTML dans le navigateur
    private void openWebPage(String url) {
        try {
            File htmlFile = new File(url);
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isLightTheme = true;
    public void actionPerformed(ActionEvent event) {
        if (isLightTheme) {
            
            // Changement vers le thème sombre
            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
            isLightTheme = false;
        } else {
            // Changement vers le thème clair
            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacLightLaf");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            isLightTheme = true;
        }

        // Rafraîchir l'apparence de tous les composants
        SwingUtilities.updateComponentTreeUI(themeButton.getRootPane());
    
        panelFiltres.revalidate(); 
        panelFiltres.repaint();
    }
}

