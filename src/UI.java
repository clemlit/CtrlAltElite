import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

import com.formdev.flatlaf.FlatLightLaf;

import fr.univrennes.istic.l2gen.geometrie.Point;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;


public class UI implements ActionListener {
    ButtonGroup echelle = new ButtonGroup();
    JRadioButton eregion;
    JRadioButton edepart;
    final JPopupMenu menuCarbu = new JPopupMenu();
    final JPopupMenu menuregion = new JPopupMenu();
    final JPopupMenu menudepart = new JPopupMenu();
    final JButton buttonCarbu;
    final JButton buttonregion;
    final JButton buttondepart;
    JPanel panelFiltres; // Déclaration de panelFiltres en tant que champ de classe
    JPanel panelResults;

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

        eregion = new JRadioButton("Région");
        edepart = new JRadioButton("Département");
        echelle.add(eregion);
        echelle.add(edepart);
        panelFiltres.add(eregion);
        panelFiltres.add(edepart);

        JPanel mapPanel = new JPanel();
        Border borderMap = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Carte",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
        mapPanel.setBorder(borderMap);
        mapPanel.setMaximumSize(new Dimension(f.getWidth(), f.getHeight() - 500));

        String[] Carburants = { "Gazole", "SP95", "SP98", "E10", "E85", "GPLc" };

        for (String Carburant : Carburants) {
            menuCarbu.add(new JCheckBoxMenuItem(Carburant));
        }


        eregion.addActionListener(this);
        edepart.addActionListener(this);

        String[] regions = { "Auvergne-Rhône-Alpes", "Bourgogne-Franche-Comté", "Bretagne", "Centre-Val de Loire",
                "Corse", "Grand Est", "Hauts-de-France", "Île-de-France", "Normandie", "Nouvelle-Aquitaine",
                "Occitanie",
                "Outre-Mer", "Pays de la Loire", "Provence-Alpes-Côte d'Azur" };

        for (String region : regions) {
            menuregion.add(new JCheckBoxMenuItem(region));
        }

        // Ajout des départements
        JMenuItem[] departements = {
                new JCheckBoxMenuItem("01 - Ain"), new JCheckBoxMenuItem("02 - Aisne"),
                new JCheckBoxMenuItem("03 - Allier"),
                new JCheckBoxMenuItem("04 - Alpes-de-Haute-Provence"), new JCheckBoxMenuItem("05 - Hautes-Alpes"),
                new JCheckBoxMenuItem("06 - Alpes-Maritimes"),
                new JCheckBoxMenuItem("07 - Ardèche"), new JCheckBoxMenuItem("08 - Ardennes"),
                new JCheckBoxMenuItem("09 - Ariège"),
                new JCheckBoxMenuItem("10 - Aube"), new JCheckBoxMenuItem("11 - Aude"),
                new JCheckBoxMenuItem("12 - Aveyron"),
                new JCheckBoxMenuItem("13 - Bouches-du-Rhône"), new JCheckBoxMenuItem("14 - Calvados"),
                new JCheckBoxMenuItem("15 - Cantal"),
                new JCheckBoxMenuItem("16 - Charente"), new JCheckBoxMenuItem("17 - Charente-Maritime"),
                new JCheckBoxMenuItem("18 - Cher"),
                new JCheckBoxMenuItem("19 - Corrèze"), new JCheckBoxMenuItem("21 - Côte-d'Or"),
                new JCheckBoxMenuItem("22 - Côtes-d'Armor"),
                new JCheckBoxMenuItem("23 - Creuse"), new JCheckBoxMenuItem("24 - Dordogne"),
                new JCheckBoxMenuItem("25 - Doubs"),
                new JCheckBoxMenuItem("26 - Drôme"), new JCheckBoxMenuItem("27 - Eure"),
                new JCheckBoxMenuItem("28 - Eure-et-Loir"),
                new JCheckBoxMenuItem("29 - Finistère"), new JCheckBoxMenuItem("2A - Corse-du-Sud"),
                new JCheckBoxMenuItem("2B - Haute-Corse"),
                new JCheckBoxMenuItem("30 - Gard"), new JCheckBoxMenuItem("31 - Haute-Garonne"),
                new JCheckBoxMenuItem("32 - Gers"),
                new JCheckBoxMenuItem("33 - Gironde"), new JCheckBoxMenuItem("34 - Hérault"),
                new JCheckBoxMenuItem("35 - Ille-et-Vilaine"),
                new JCheckBoxMenuItem("36 - Indre"), new JCheckBoxMenuItem("37 - Indre-et-Loire"),
                new JCheckBoxMenuItem("38 - Isère"),
                new JCheckBoxMenuItem("39 - Jura"), new JCheckBoxMenuItem("40 - Landes"),
                new JCheckBoxMenuItem("41 - Loir-et-Cher"),
                new JCheckBoxMenuItem("42 - Loire"), new JCheckBoxMenuItem("43 - Haute-Loire"),
                new JCheckBoxMenuItem("44 - Loire-Atlantique"),
                new JCheckBoxMenuItem("45 - Loiret"), new JCheckBoxMenuItem("46 - Lot"),
                new JCheckBoxMenuItem("47 - Lot-et-Garonne"),
                new JCheckBoxMenuItem("48 - Lozère"), new JCheckBoxMenuItem("49 - Maine-et-Loire"),
                new JCheckBoxMenuItem("50 - Manche"),
                new JCheckBoxMenuItem("51 - Marne"), new JCheckBoxMenuItem("52 - Haute-Marne"),
                new JCheckBoxMenuItem("53 - Mayenne"),
                new JCheckBoxMenuItem("54 - Meurthe-et-Moselle"), new JCheckBoxMenuItem("55 - Meuse"),
                new JCheckBoxMenuItem("56 - Morbihan"),
                new JCheckBoxMenuItem("57 - Moselle"), new JCheckBoxMenuItem("58 - Nièvre"),
                new JCheckBoxMenuItem("59 - Nord"),
                new JCheckBoxMenuItem("60 - Oise"), new JCheckBoxMenuItem("61 - Orne"),
                new JCheckBoxMenuItem("62 - Pas-de-Calais"),
                new JCheckBoxMenuItem("63 - Puy-de-Dôme"), new JCheckBoxMenuItem("64 - Pyrénées-Atlantiques"),
                new JCheckBoxMenuItem("65 - Hautes-Pyrénées"),
                new JCheckBoxMenuItem("66 - Pyrénées-Orientales"), new JCheckBoxMenuItem("67 - Bas-Rhin"),
                new JCheckBoxMenuItem("68 - Haut-Rhin"),
                new JCheckBoxMenuItem("69 - Rhône"), new JCheckBoxMenuItem("70 - Haute-Saône"),
                new JCheckBoxMenuItem("71 - Saône-et-Loire"),
                new JCheckBoxMenuItem("72 - Sarthe"), new JCheckBoxMenuItem("73 - Savoie"),
                new JCheckBoxMenuItem("74 - Haute-Savoie"),
                new JCheckBoxMenuItem("75 - Paris"), new JCheckBoxMenuItem("76 - Seine-Maritime"),
                new JCheckBoxMenuItem("77 - Seine-et-Marne"),
                new JCheckBoxMenuItem("78 - Yvelines"), new JCheckBoxMenuItem("79 - Deux-Sèvres"),
                new JCheckBoxMenuItem("80 - Somme"),
                new JCheckBoxMenuItem("81 - Tarn"), new JCheckBoxMenuItem("82 - Tarn-et-Garonne"),
                new JCheckBoxMenuItem("83 - Var"),
                new JCheckBoxMenuItem("84 - Vaucluse"), new JCheckBoxMenuItem("85 - Vendée"),
                new JCheckBoxMenuItem("86 - Vienne"),
                new JCheckBoxMenuItem("87 - Haute-Vienne"), new JCheckBoxMenuItem("88 - Vosges"),
                new JCheckBoxMenuItem("89 - Yonne"),
                new JCheckBoxMenuItem("90 - Territoire de Belfort"), new JCheckBoxMenuItem("91 - Essonne"),
                new JCheckBoxMenuItem("92 - Hauts-de-Seine"),
                new JCheckBoxMenuItem("93 - Seine-Saint-Denis"), new JCheckBoxMenuItem("94 - Val-de-Marne"),
                new JCheckBoxMenuItem("95 - Val-d'Oise"),
                new JCheckBoxMenuItem("971 - Guadeloupe"), new JCheckBoxMenuItem("972 - Martinique"),
                new JCheckBoxMenuItem("973 - Guyane"),
                new JCheckBoxMenuItem("974 - La Réunion"), new JCheckBoxMenuItem("975 - Saint-Pierre-et-Miquelon"),
                new JCheckBoxMenuItem("976 - Mayotte"),
                new JCheckBoxMenuItem("977 - Saint-Barthélemy"), new JCheckBoxMenuItem("978 - Saint-Martin")
        };

        // Ajout des départements au menu
        for (JMenuItem departement : departements) {
            menudepart.add(departement);
        }

        buttonregion = new JButton("Choississez une ou plusieurs région(s)");
        buttonregion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!menuregion.isVisible()) {
                    menuregion.setVisible(true);
                } else {
                    menuregion.setVisible(false);
                }
            }
        });

        buttondepart = new JButton("Choississez un ou plusieurs département(s)");
        buttondepart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!menudepart.isVisible()) {
                    menudepart.setVisible(true);
                } else {
                    menudepart.setVisible(false);
                }
            }
        });

        buttonCarbu = new JButton("Choississez un ou plusieurs Carburant(s)");
        buttonCarbu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!menuCarbu.isVisible()) {
                    menuCarbu.setVisible(true);
                } else {
                    menuCarbu.setVisible(false);
                }
            }
        });

        f.setLayout(new BorderLayout());

        f.add(panelFiltres, BorderLayout.WEST);
        f.add(mapPanel, BorderLayout.CENTER);
        f.add(panelResults, BorderLayout.SOUTH);

        f.pack();
        panelFiltres.add(buttonCarbu);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * 50 / 100;
        int height = screenSize.height * 50 / 100;
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        panelFiltres.revalidate(); // Actualise le panel pour refléter les changements
        panelFiltres.repaint();

        if (event.getSource() instanceof JRadioButton) {
            JRadioButton sourceradio = (JRadioButton) event.getSource();
            if (sourceradio == eregion) {
                if (eregion.isSelected()) {
                    panelFiltres.add(buttonregion);
                    panelFiltres.remove(buttondepart);
                }
            }
            if (sourceradio == edepart) {
                if (edepart.isSelected()) {
                    panelFiltres.add(buttondepart);
                    panelFiltres.remove(buttonregion);
                }
            }
            panelFiltres.revalidate();
            panelFiltres.repaint();
        }
    }
}
