import javax.swing.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;

public class UI {

  public static void main(String argv[]) {

    JFrame f = new JFrame("ma fenetre");
    f.setSize(300,100);
    JPanel pannelCarburant = new JPanel();
    Border border = BorderFactory.createTitledBorder("Carburant");
    pannelCarburant.setBorder(border);

    String[] Carburant = { "Gazole", "SP95", "SP98", "E10","E85", "GPLc"}; 
    JComboBox jCombo = new JComboBox(Carburant);

    pannelCarburant.add(jCombo);
    JPanel pannelechelle = new JPanel();
    Border border2 = BorderFactory.createTitledBorder("échelle");
    pannelechelle.setBorder(border2);
    ButtonGroup group = new ButtonGroup();
    JRadioButton radio1 = new JRadioButton("Région", true);
    JRadioButton radio2 = new JRadioButton("Département");
    group.add(radio1);
    pannelechelle.add(radio1);
    group.add(radio2);
    pannelechelle.add(radio2);


    f.add(pannelCarburant);
    f.add(pannelechelle,BorderLayout.NORTH);
    f.setVisible(true);
  }
}