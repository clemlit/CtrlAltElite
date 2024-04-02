import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class UI implements ActionListener {
    ButtonGroup echelle = new ButtonGroup();
    JPanel panelFiltres;
    JPanel panelResults;
    JPanel mapPanel;

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

        panelResults = new JPanel();
        Border borderResults = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                "Résultats", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
        panelResults.setBorder(borderResults);
        panelResults.setPreferredSize(new Dimension(100, 200));

        panelFiltres = new JPanel();
        panelFiltres.setLayout(new BoxLayout(panelFiltres, BoxLayout.PAGE_AXIS));
        Border borderFiltres = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                "Filtres", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
        panelFiltres.setBorder(borderFiltres);
        panelFiltres.setPreferredSize(new Dimension(300, f.getHeight()));

        JPanel mapPanel = new JPanel();
        Border borderMap = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Carte",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
        mapPanel.setBorder(borderMap);
        mapPanel.setMaximumSize(new Dimension(f.getWidth(), f.getHeight() - 500));

        JCheckBox bouton1 = new JCheckBox("Wi-fi");
        panelFiltres.add(bouton1);
        JCheckBox bouton2 = new JCheckBox("Boutique alimentaire");
        panelFiltres.add(bouton2);
        JCheckBox bouton3 = new JCheckBox("Station de gonflage");
        panelFiltres.add(bouton3);
        JCheckBox bouton4 = new JCheckBox("Lavage automatique");
        panelFiltres.add(bouton4);
        JCheckBox bouton5 = new JCheckBox("Bornes éléctrique");
        panelFiltres.add(bouton5);
        JCheckBox bouton6 = new JCheckBox("Automate CB 24/24");
        panelFiltres.add(bouton6);
        JCheckBox bouton7 = new JCheckBox("DAB (Distributeur automatique de billets)");
        panelFiltres.add(bouton7);
        JCheckBox bouton8 = new JCheckBox("Espace bébé");
        panelFiltres.add(bouton8);
        JCheckBox bouton10 = new JCheckBox("Toilettes publiques");
        panelFiltres.add(bouton10);

        // Crée les ComboBox multi-sélection pour les régions, départements et carburants
        multiBox<String> comboRegion = new multiBox<>();
        multiBox<String> comboDepart = new multiBox<>();
        multiBox<String> comboCarbu = new multiBox<>();        

        String[] regions = {
            "Auvergne-Rhône-Alpes", "Bourgogne-Franche-Comté", "Bretagne", "Centre-Val de Loire", "Corse",
            "Grand Est", "Hauts-de-France", "Île-de-France", "Normandie", "Nouvelle-Aquitaine",
            "Occitanie", "Pays de la Loire", "Provence-Alpes-Côte d'Azur"
        };

        for (String region : regions) {
            comboRegion.addItem(region);
        }
        
        String[] departements = {
            "01 - Ain", "02 - Aisne", "03 - Allier", "04 - Alpes-de-Haute-Provence", "05 - Hautes-Alpes",
            "06 - Alpes-Maritimes", "07 - Ardèche", "08 - Ardennes", "09 - Ariège", "10 - Aube",
            "11 - Aude", "12 - Aveyron", "13 - Bouches-du-Rhône", "14 - Calvados", "15 - Cantal",
            "16 - Charente", "17 - Charente-Maritime", "18 - Cher", "19 - Corrèze", "20 - Corse",
            "21 - Côte-d'Or", "22 - Côtes-d'Armor", "23 - Creuse", "24 - Dordogne", "25 - Doubs",
            "26 - Drôme", "27 - Eure", "28 - Eure-et-Loir", "29 - Finistère", "2A - Corse-du-Sud",
            "2B - Haute-Corse", "30 - Gard", "31 - Haute-Garonne", "32 - Gers", "33 - Gironde",
            "34 - Hérault", "35 - Ille-et-Vilaine", "36 - Indre", "37 - Indre-et-Loire", "38 - Isère",
            "39 - Jura", "40 - Landes", "41 - Loir-et-Cher", "42 - Loire", "43 - Haute-Loire",
            "44 - Loire-Atlantique", "45 - Loiret", "46 - Lot", "47 - Lot-et-Garonne", "48 - Lozère",
            "49 - Maine-et-Loire", "50 - Manche", "51 - Marne", "52 - Haute-Marne", "53 - Mayenne",
            "54 - Meurthe-et-Moselle", "55 - Meuse", "56 - Morbihan", "57 - Moselle", "58 - Nièvre",
            "59 - Nord", "60 - Oise", "61 - Orne", "62 - Pas-de-Calais", "63 - Puy-de-Dôme",
            "64 - Pyrénées-Atlantiques", "65 - Hautes-Pyrénées", "66 - Pyrénées-Orientales",
            "67 - Bas-Rhin", "68 - Haut-Rhin", "69 - Rhône", "70 - Haute-Saône", "71 - Saône-et-Loire",
            "72 - Sarthe", "73 - Savoie", "74 - Haute-Savoie", "75 - Paris", "76 - Seine-Maritime",
            "77 - Seine-et-Marne", "78 - Yvelines", "79 - Deux-Sèvres", "80 - Somme", "81 - Tarn",
            "82 - Tarn-et-Garonne", "83 - Var", "84 - Vaucluse", "85 - Vendée", "86 - Vienne",
            "87 - Haute-Vienne", "88 - Vosges", "89 - Yonne", "90 - Territoire de Belfort",
            "91 - Essonne", "92 - Hauts-de-Seine", "93 - Seine-Saint-Denis", "94 - Val-de-Marne",
            "95 - Val-d'Oise", "971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion",
            "975 - Saint-Pierre-et-Miquelon", "976 - Mayotte", "977 - Saint-Barthélemy", "978 - Saint-Martin"
        };

        for (String departement : departements) {
            comboDepart.addItem(departement);
        }
        
        String[] carburants = { "Gazole", "SP95", "SP98", "E10", "E85", "GPLc"};
        for (String carburant : carburants) {
            comboCarbu.addItem(carburant);
        }       

        // Définit la taille préférée des ComboBox
        comboRegion.setPreferredSize(new Dimension(200, 50));
        comboDepart.setPreferredSize(new Dimension(200, 50));
        comboCarbu.setPreferredSize(new Dimension(200, 50));

        JLabel labelRegions = new JLabel("Sélectionnez une région ");
        JLabel labelDepartements = new JLabel("Sélectionnez un département ");
        JLabel labelCarburants = new JLabel("Sélectionnez un carburant ");

        // Pour l'alignement
        Box boxCombos = Box.createVerticalBox();
        boxCombos.add(labelRegions);
        boxCombos.add(comboRegion);
        boxCombos.add(labelDepartements);
        boxCombos.add(comboDepart);
        boxCombos.add(labelCarburants);
        boxCombos.add(comboCarbu);
        panelFiltres.add(boxCombos);
        panelFiltres.setLayout(new FlowLayout(FlowLayout.CENTER));
        

        f.setLayout(new BorderLayout());

        f.add(panelFiltres, BorderLayout.WEST);
        f.add(panelResults, BorderLayout.SOUTH);
        f.add(mapPanel, BorderLayout.CENTER);

        f.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 50 / 100;
        int height = screenSize.height * 50 / 100;
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        panelFiltres.revalidate(); 
        panelFiltres.repaint();
    }
}
