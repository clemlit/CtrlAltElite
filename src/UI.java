import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class UI {

  public static void main(String argv[]) {

    

    JFrame f = new JFrame("ma fenetre");
    f.setSize(300,100);

    JPanel pannelCarburant = new JPanel();
    Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Carburant", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
    pannelCarburant.setBorder(border);

    String[] Carburant = { "Gazole", "SP95", "SP98", "E10","E85", "GPLc"}; 
    JComboBox jCombo = new JComboBox(Carburant);
    jCombo.setBackground(Color.WHITE);

    pannelCarburant.add(jCombo);

    JPanel pannelechelle = new JPanel();
    Border border2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "échelle", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
    pannelechelle.setBorder(border2);
    ButtonGroup group = new ButtonGroup();
    JRadioButton radio1 = new JRadioButton("Région", true);
    JRadioButton radio2 = new JRadioButton("Département");
    group.add(radio1);
    pannelechelle.add(radio1);
    group.add(radio2);
    pannelechelle.add(radio2);

    JPanel pannelservice = new JPanel();
    Border border3 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Service Spécifique", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
    pannelservice.setBorder(border3);
    JCheckBox bouton1 = new JCheckBox("Wifi");
    pannelservice.add(bouton1);
    JCheckBox bouton2 = new JCheckBox("Boutique alimentaire");
    pannelservice.add(bouton2);
    JCheckBox bouton3 = new JCheckBox("station de gonflage");
    pannelservice.add(bouton3);
    JCheckBox bouton4 = new JCheckBox("Lavage automatique");
    pannelservice.add(bouton4);
    JCheckBox bouton5 = new JCheckBox("Bornes éléctrique");
    pannelservice.add(bouton5);
    JCheckBox bouton6 = new JCheckBox("Automate CB 24/24");
    pannelservice.add(bouton6);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) (screenSize.width* 0.5 +100);
    int height = (int) (screenSize.height * 0.5);
    f.setMinimumSize(new Dimension(width, height));

    f.add(pannelCarburant);
    f.add(pannelechelle,BorderLayout.NORTH);
    f.add(pannelservice,BorderLayout.EAST);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
}