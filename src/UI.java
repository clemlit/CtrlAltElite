import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class UI {

  public static void main(String argv[]) {
    
    FlatLightLaf.setup();

    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      }
      JFrame.setDefaultLookAndFeelDecorated(true);

    JFrame f = new JFrame("ma fenetre");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel topPanel = new JPanel(); 
    topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
    topPanel.setPreferredSize(new Dimension(f.getWidth(), 200)); 

    JPanel pannelCarburant = new JPanel();
    pannelCarburant.setLayout(new BoxLayout(pannelCarburant, BoxLayout.Y_AXIS));
    Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Carburant", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLACK);
    pannelCarburant.setBorder(border);

    String[] Carburant = { "Gazole", "SP95", "SP98", "E10","E85", "GPLc"}; 
    JComboBox jCombo = new JComboBox(Carburant);
    jCombo.setBackground(Color.WHITE);
    jCombo.setMaximumSize(new Dimension(200, jCombo.getPreferredSize().height)); 

    pannelCarburant.add(jCombo);

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


    JCheckBox boutonDep = new JCheckBox("Département");
    topPanel.add(boutonDep);
    JCheckBox boutonReg = new JCheckBox("Région");
    topPanel.add(boutonReg);


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
}