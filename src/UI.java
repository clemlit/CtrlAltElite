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

    f.add(pannelCarburant);
    
    f.setVisible(true);
  }
}
