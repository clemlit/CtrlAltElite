import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class UI implements ActionListener {
    JComboBox departementARA;
    JComboBox departementBFC;
    JComboBox departementB;
    JComboBox departementCVL;
    JComboBox departementC;
    JComboBox departementGE;
    JComboBox departementHDF;
    JComboBox departementIDF;
    JComboBox departementN;
    JComboBox departementNA;
    JComboBox departementO;
    JComboBox departementOM;
    JComboBox departementPDL;
    JComboBox departementPACA;
    JPanel panelFiltres; // Déclaration de panelFiltres en tant que champ de classe

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

        JPanel panelResults = new JPanel();
        panelResults.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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

        JPanel mapPanel = new JPanel();
        Border borderMap = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Carte",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
        mapPanel.setBorder(borderMap);
        mapPanel.setMaximumSize(new Dimension(f.getWidth(), f.getHeight() - 500));

        String[] listeregion = { "Auvergne-Rhône-Alpes", "Bourgogne-Franche-Comté", "Bretagne", "Centre-Val de Loire",
                "Corse", "Grand Est", "Hauts-de-France", "Île-de-France", "Normandie", "Nouvelle-Aquitaine",
                "Occitanie", "Outre-Mer", "Pays de la Loire", "Provence-Alpes-Côte d'Azur" };

        String[] listedepartementARA = {
            "Ain", "Allier", "Ardèche", "Cantal", "Drôme", "Haute-Loire", "Haute-Savoie", "Isère", 
            "Loire", "Puy-de-Dôme", "Rhône", "Savoie"
        };
        
        String[] listedepartementBFC = {
            "Côte-d'Or", "Doubs", "Haute-Saône", "Jura", "Nièvre", "Saône-et-Loire", "Territoire de Belfort", "Yonne"
        };    

        String[] listedepartementB = {"Côtes-d'Armor", "Finistère", "Ille-et-Vilaine", "Morbihan"};

        String[] listedepartementCVL = {
            "Cher", "Eure-et-Loir", "Indre", "Indre-et-Loire", "Loir-et-Cher", "Loiret"
        };
          
        String[] listedepartementC = { "Corse-du-Sud", "Haute-Corse" };

        String[] listedepartementGE = {
            "Ardennes", "Aube", "Marne", "Haute-Marne", "Meurthe-et-Moselle", "Meuse", "Moselle", "Bas-Rhin", "Haut-Rhin", "Vosges"
        };

        String[] listedepartementHDF = {
            "Aisne", "Nord", "Oise", "Pas-de-Calais", "Somme"
        };

        String[] listedepartementIDF = {
            "Paris", "Seine-et-Marne", "Yvelines", "Essonne", "Hauts-de-Seine", "Seine-Saint-Denis", "Val-de-Marne", "Val-d'Oise"
        };

        String[] listedepartementN = { "Calvados", "Eure", "Manche", "Orne", "Seine-Maritime" };

        String[] listedepartementNA = {
            "Charente", "Charente-Maritime", "Corrèze", "Creuse", "Deux-Sèvres",
            "Dordogne", "Gironde", "Haute-Vienne", "Landes", "Lot-et-Garonne", "Pyrénées-Atlantiques", "Vienne"
        };

        String[] listedepartementO = {
            "Ariège", "Aude", "Aveyron", "Gard", "Haute-Garonne",
            "Gers", "Hérault", "Lot", "Lozère", "Hautes-Pyrénées",
            "Pyrénées-Orientales", "Tarn", "Tarn-et-Garonne"
        };

        String[] listedepartementOM = { 
            "Guadeloupe", "Martinique", "Guyane", "La Réunion", "Mayotte", 
            "Saint-Barthélemy", "Saint-Martin", "Saint-Pierre-et-Miquelon"
        };
        

        String[] listedepartementPDL = {
            "Loire-Atlantique", "Maine-et-Loire", "Mayenne", "Sarthe", "Vendée"
        };

        String[] listedepartementPACA = {
            "Alpes-de-Haute-Provence", "Hautes-Alpes", "Alpes-Maritimes", "Bouches-du-Rhône", "Var", "Vaucluse"
        };

        String[] Carburant = { "Gazole", "SP95", "SP98", "E10", "E85", "GPLc" };

        JComboBox jCombo = new JComboBox(Carburant);
        jCombo.setBackground(Color.WHITE);
        jCombo.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        JComboBox region = new JComboBox(listeregion);
        region.setBackground(Color.WHITE);
        region.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementARA = new JComboBox(listedepartementARA);
        departementARA.setBackground(Color.WHITE);
        departementARA.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementBFC = new JComboBox(listedepartementBFC);
        departementBFC.setBackground(Color.WHITE);
        departementBFC.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementB = new JComboBox(listedepartementB);
        departementB.setBackground(Color.WHITE);
        departementB.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementCVL = new JComboBox(listedepartementCVL);
        departementCVL.setBackground(Color.WHITE);
        departementCVL.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementC = new JComboBox(listedepartementC);
        departementC.setBackground(Color.WHITE);
        departementC.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementGE = new JComboBox(listedepartementGE);
        departementGE.setBackground(Color.WHITE);
        departementGE.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementHDF = new JComboBox(listedepartementHDF);
        departementHDF.setBackground(Color.WHITE);
        departementHDF.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementIDF = new JComboBox(listedepartementIDF);
        departementIDF.setBackground(Color.WHITE);
        departementIDF.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementN = new JComboBox(listedepartementN);
        departementN.setBackground(Color.WHITE);
        departementN.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementNA = new JComboBox(listedepartementNA);
        departementNA.setBackground(Color.WHITE);
        departementNA.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementO = new JComboBox(listedepartementO);
        departementO.setBackground(Color.WHITE);
        departementO.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementOM = new JComboBox(listedepartementOM);
        departementOM.setBackground(Color.WHITE);
        departementOM.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementPDL = new JComboBox(listedepartementPDL);
        departementPDL.setBackground(Color.WHITE);
        departementPDL.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        departementPACA = new JComboBox(listedepartementPACA);
        departementPACA.setBackground(Color.WHITE);
        departementPACA.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height));
        

        panelFiltres.add(jCombo);
        panelFiltres.add(region);
        region.addActionListener(this);

        f.setLayout(new BorderLayout());

        f.add(panelFiltres, BorderLayout.WEST);
        f.add(mapPanel, BorderLayout.CENTER);
        f.add(panelResults, BorderLayout.SOUTH);

        f.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 50 / 100;
        int height = screenSize.height * 50 / 100;
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        System.out.println("Bouton " + event.getSource() + " cliqué !");
        if (event.getSource() instanceof JComboBox) { // Vérifie si l'événement provient d'un JComboBox
            JComboBox source = (JComboBox) event.getSource();
            if (source.getSelectedItem().equals("Auvergne-Rhône-Alpes")){
                panelFiltres.add(departementARA);
            } else {
                panelFiltres.remove(departementARA); 
            } if (source.getSelectedItem().equals("Bourgogne-Franche-Comté")){
                panelFiltres.add(departementBFC);
            } else {
                panelFiltres.remove(departementBFC); 
            } if (source.getSelectedItem().equals("Bretagne")) { 
                panelFiltres.add(departementB); 
            } else {
                panelFiltres.remove(departementB); 
            }  if (source.getSelectedItem().equals("Centre-Val de Loire")){
                panelFiltres.add(departementCVL);
            } else {
                panelFiltres.remove(departementCVL); 
            }
            if (source.getSelectedItem().equals("Corse")) { 
                panelFiltres.add(departementC); 
            } else {
                panelFiltres.remove(departementC); 
            } if (source.getSelectedItem().equals("Grand Est")){
                panelFiltres.add(departementGE);
            } else {
                panelFiltres.remove(departementGE); 
            } if (source.getSelectedItem().equals("Hauts-de-France")){
                panelFiltres.add(departementHDF);
            } else {
                panelFiltres.remove(departementHDF); 
            } if (source.getSelectedItem().equals("Île-de-France")){
                panelFiltres.add(departementIDF);
            } else {
                panelFiltres.remove(departementIDF); 
            } if (source.getSelectedItem().equals("Normandie")) { 
                panelFiltres.add(departementN); 
            } else {
                panelFiltres.remove(departementN); 
            }  if (source.getSelectedItem().equals("Nouvelle-Aquitaine")){
                panelFiltres.add(departementNA);
            } else {
                panelFiltres.remove(departementNA); 
            }
            if (source.getSelectedItem().equals("Occitanie")) { 
                panelFiltres.add(departementO); 
            } else {
                panelFiltres.remove(departementO); 
            } if (source.getSelectedItem().equals("Outre-Mer")){
                panelFiltres.add(departementOM);
            } else {
                panelFiltres.remove(departementOM); 
            } if (source.getSelectedItem().equals("Pays de la Loire")) { 
                panelFiltres.add(departementPDL); 
            } else {
                panelFiltres.remove(departementPDL); 
            } if (source.getSelectedItem().equals("Provence-Alpes-Côte d'Azur")){
                panelFiltres.add(departementPACA);
            } else {
                panelFiltres.remove(departementPACA); 
            }
            
            
            panelFiltres.revalidate(); // Actualise le panel pour refléter les changements
            panelFiltres.repaint();
        }
    }
}
