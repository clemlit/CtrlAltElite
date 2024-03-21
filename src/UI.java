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

public class UI implements ActionListener{

  public static  void main(String argv[]) {
    new UI();
}

  public UI()  {

    FlatLightLaf.setup();

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    JFrame f = new JFrame("ma fenetre");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel topPanel = new JPanel(); 
    topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
    topPanel.setPreferredSize(new Dimension(f.getWidth(), 200)); 

    JPanel pannelCarburant = new JPanel();
    pannelCarburant.setLayout(new BoxLayout(pannelCarburant, BoxLayout.Y_AXIS));
    Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Carburant", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
    pannelCarburant.setBorder(border);

    String[] listeregion = { "Auvergne-Rhône-Alpes", "Bourgogne-Franche-Comté", "Bretagne", "Centre-Val de Loire", "Corse", "Grand Est", "Hauts-de-France", "Île-de-France", "Normandie", "Nouvelle-Aquitaine", "Occitanie", "Pays de la Loire", "Provence-Alpes-Côte d'Azur" };
    


    String[] listedepartement = {
      "Ain", "Aisne", "Allier", "Alpes-de-Haute-Provence", "Alpes-Maritimes", "Ardèche", "Ardennes", "Ariège", "Aube", "Aude",
      "Aveyron", "Bas-Rhin", "Bouches-du-Rhône", "Calvados", "Cantal", "Charente", "Charente-Maritime", "Cher", "Corrèze", "Corse-du-Sud",
      "Côte-d'Or", "Côtes-d'Armor", "Creuse", "Deux-Sèvres", "Dordogne", "Doubs", "Drôme", "Essonne", "Eure", "Eure-et-Loir",
      "Finistère", "Gard", "Gers", "Gironde", "Guadeloupe", "Guyane", "Haut-Rhin", "Haute-Corse", "Haute-Garonne", "Haute-Loire",
      "Haute-Marne", "Haute-Saône", "Haute-Savoie", "Haute-Vienne", "Hautes-Alpes", "Hautes-Pyrénées", "Hauts-de-Seine", "Hérault", "Ille-et-Vilaine",
      "Indre", "Indre-et-Loire", "Isère", "Jura", "La Réunion", "Landes", "Loir-et-Cher", "Loire", "Loire-Atlantique",
      "Loiret", "Lot", "Lot-et-Garonne", "Lozère", "Maine-et-Loire", "Manche", "Marne", "Martinique", "Mayenne", "Mayotte",
      "Meurthe-et-Moselle", "Meuse", "Morbihan", "Moselle", "Nièvre", "Nord", "Oise", "Orne", "Paris", "Pas-de-Calais",
      "Puy-de-Dôme", "Pyrénées-Atlantiques", "Pyrénées-Orientales", "Rhône", "Saint-Barthélemy", "Saint-Martin", "Saint-Pierre-et-Miquelon", "Saône-et-Loire", "Sarthe", "Savoie",
      "Seine-et-Marne", "Seine-Maritime", "Seine-Saint-Denis", "Somme", "Tarn", "Tarn-et-Garonne", "Territoire de Belfort", "Val-d'Oise", "Val-de-Marne", "Var",
      "Vaucluse", "Vendée", "Vienne", "Vosges", "Yonne", "Yvelines"
    };
  
    

    String[] Carburant = { "Gazole", "SP95", "SP98", "E10","E85", "GPLc"}; 
    JComboBox jCombo = new JComboBox(Carburant);
    jCombo.setBackground(Color.WHITE);
    jCombo.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height)); 
    JComboBox region = new JComboBox(listeregion);
    region.setBackground(Color.WHITE);
    region.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height)); 
    JComboBox departement = new JComboBox(listedepartement);
    departement.setBackground(Color.WHITE);
    departement.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height)); 

    pannelCarburant.add(jCombo);
    pannelCarburant.add(region);
    pannelCarburant.add(departement);
    region.addActionListener(this);
    departement.addActionListener(this);

    String selectedValue = (String) region.getSelectedItem();
      if (selectedValue != null && selectedValue.equals("selectedItemReminder")) {
        System.out.println(selectedValue);
    }
    

    JCheckBox bouton1 = new JCheckBox("Wifi");
    pannelCarburant.add(bouton1);
    JCheckBox bouton2 = new JCheckBox("Boutique alimentaire");
    pannelCarburant.add(bouton2);
    JCheckBox bouton3 = new JCheckBox("Station de gonflage");
    pannelCarburant.add(bouton3);
    JCheckBox bouton4 = new JCheckBox("Lavage automatique");
    pannelCarburant.add(bouton4);
    JCheckBox bouton5 = new JCheckBox("Bornes éléctrique");
    pannelCarburant.add(bouton5);
    JCheckBox bouton6 = new JCheckBox("Automate CB 24/24");
    pannelCarburant.add(bouton6);

    JPanel rightPanel = new JPanel(); 
    rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

    f.setLayout(new BorderLayout());
    f.add(topPanel, BorderLayout.NORTH); 
    f.add(pannelCarburant, BorderLayout.WEST); 
    f.add(rightPanel, BorderLayout.EAST); 

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
    
  }

}