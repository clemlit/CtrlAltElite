import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.icons.FlatCheckBoxIcon;
import com.formdev.flatlaf.ui.FlatComboBoxUI;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicComboBoxEditor; 
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.ComboPopup;

import net.miginfocom.swing.MigLayout;

// Cette classe est presque entièrement faite par DJ-Raven. 
// Source: https://github.com/DJ-Raven/raven-java-swing-tutorial-project/tree/main/combobox-multiple-selection


public class multiBox<E> extends JComboBox<E> {

    private final List<Object> selectedItems = new ArrayList<>();
    private final ComboBoxMultiCellEditor comboBoxMultiCellEditor;
    private Component comboList;
    private Timer checkTimer;
    private boolean lastCheckState;
    private boolean check; // Add this field



    /**
     * Obtient les éléments sélectionnés dans la liste déroulante
     * 
     * @return Une liste d'objets représentant les éléments sélectionnés
     */
    public List<Object> getSelectedItems() {
        return selectedItems;
    }

    /**
     * Définit les éléments sélectionnés dans la liste déroulante
     * 
     * @param selectedItems La liste d'objets à sélectionner
     */
    public void setSelectedItems(List<Object> selectedItems) {
        List<Object> comboItem = new ArrayList<>();
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            comboItem.add(getItemAt(i));
        }
        for (Object obj : selectedItems) {
            if (comboItem.contains(obj)) {
                addItemObject(obj);
            }
        }
        comboItem.clear();
    }

    /**
     * Efface tous les éléments sélectionnés dans la liste déroulante
     */
    public void clearSelectedItems() {
        selectedItems.clear();
        Component editorCom = getEditor().getEditorComponent();
        if (editorCom instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) editorCom;
            JPanel panel = (JPanel) scroll.getViewport().getComponent(0);
            panel.removeAll();
            revalidate();
            repaint();
            comboList.repaint();
        }
    }

    // Méthodes privées pour gérer l'ajout et la suppression d'éléments

    private void removeItemObject(Object obj) {
        selectedItems.remove(obj);
        comboBoxMultiCellEditor.removeItem(obj);
        if (comboList != null) {
            comboList.repaint();
        }
    }

    private void addItemObject(Object obj) {
        selectedItems.add(obj);
        comboBoxMultiCellEditor.addItem(obj);
        if (comboList != null) {
            comboList.repaint();
        }
    }

    @SuppressWarnings("rawtypes") // Suppression des avertissements du compilateur 
    /**
     * Constructeur par défaut de la classe multiBox
     */
    public multiBox(String nb_dep,boolean check) {
        setUI(new ComboBoxMultiUI());
        comboBoxMultiCellEditor = new ComboBoxMultiCellEditor();
        setRenderer(new ComboBoxMultiCellRenderer());
        setEditor(comboBoxMultiCellEditor);
        setEditable(true);
        addActionListener((e) -> {
            if (e.getModifiers() == ActionEvent.MOUSE_EVENT_MASK) {
                JComboBox combo = (JComboBox) e.getSource();
                Object obj = combo.getSelectedItem();
                if (selectedItems.contains(obj)) {
                    removeItemObject(obj);
                } else {
                    addItemObject(obj);
                }
            }
        });
        
    }

    public void addDepartement(String nb_dep){
        if (selectedItems.contains(nb_dep)) {
            removeItemObject(nb_dep);
        } else {
            addItemObject(nb_dep);
        }
    }

    @Override
    public void setPopupVisible(boolean v) {

    }

    // Classes internes pour personnaliser l'interface utilisateur de la JComboBox
    private class ComboBoxMultiUI extends FlatComboBoxUI {

        @Override
        protected ComboPopup createPopup() {
            return new MultiComboPopup(comboBox);
        }

        private class MultiComboPopup extends FlatComboPopup {

            @SuppressWarnings("rawtypes") // Suppression des avertissements du compilateur
            public MultiComboPopup(JComboBox combo) {
                super(combo);
            }
        }

        @Override
        protected Dimension getDisplaySize() {
            Dimension size = super.getDefaultSize();
            return new Dimension(0, size.height);
        }
    }

    private class ComboBoxMultiCellRenderer extends BasicComboBoxRenderer {

        @SuppressWarnings("rawtypes") // Suppression des avertissements du compilateur
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (comboList != list) {
                comboList = list;
            }
            setIcon(new CheckBoxIcon(selectedItems.contains(value)));
            return this;
        }
    }

    private class ComboBoxMultiCellEditor extends BasicComboBoxEditor {

        protected final JScrollPane scroll;
        protected final JPanel panel;

        protected void addItem(Object obj) {
            Item item = new Item(obj);
            panel.add(item);
            panel.repaint();
            panel.revalidate();
        }

        @SuppressWarnings("unchecked") // Suppression des avertissements du compilateur
        protected void removeItem(Object obj) {
            int count = panel.getComponentCount();
            for (int i = 0; i < count; i++) {
                Item item = (Item) panel.getComponent(i);
                if (item.getItem() == obj) {
                    panel.remove(i);
                    panel.revalidate();
                    panel.repaint();
                    break;
                }
            }
        }

        public ComboBoxMultiCellEditor() {
            this.panel = new JPanel(new MigLayout("insets 0,filly,gapx 2", "", "fill"));
            this.scroll = new JScrollPane(panel);
            scroll.putClientProperty(FlatClientProperties.STYLE, ""
                    + "border:2,2,2,2;"
                    + "background:$ComboBox.editableBackground");
            panel.putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:$ComboBox.editableBackground");
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            JScrollBar scrollBar = scroll.getHorizontalScrollBar();
            scrollBar.putClientProperty(FlatClientProperties.STYLE, ""
                    + "width:3;"
                    + "thumbInsets:0,0,0,1;"
                    + "hoverTrackColor:null");
            scrollBar.setUnitIncrement(10);

        }

        @Override
        public Component getEditorComponent() {
            return scroll;
        }

    }

    private class CheckBoxIcon extends FlatCheckBoxIcon {

        private final boolean selected;

        public CheckBoxIcon(boolean selected) {
            this.selected = selected;
        }

        @Override
        protected boolean isSelected(Component c) {
            return selected;
        }
    }

    private class Item extends JButton {

        public Object getItem() {
            return item;
        }

        private final Object item;

        public Item(Object item) {
            super(item.toString() + "  x"); 
            this.item = item;
            init();
        }

        private void init() {
            addActionListener(e -> removeItemObject(item));
        }
    }


        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            FlatUIUtils.setRenderingHints(g2);
            int arc = UIScale.scale(10);
            g2.setColor(getBackground());
            FlatUIUtils.paintComponentBackground(g2, 0, 0, getWidth(), getHeight(), 0, arc);
            g2.dispose();
            super.paintComponent(g);
        }
        
}
