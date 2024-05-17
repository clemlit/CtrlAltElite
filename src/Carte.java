import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;

/**
 * Cette classe représente une carte interactive affichée dans une interface
 * graphique Swing
 * Elle permet de détecter les régions cliquées par l'utilisateur
 */

public class Carte {
/* 
 Code utilisé et modifié from:
 https://stackoverflow.com/q/7218309/418556
 */
    private JComponent ui = null;
    JLabel output = new JLabel();
    public static final int SIZE = 700;
    BufferedImage image;
    Area area;
    ArrayList<Shape> shapeList;
    HashMap<Integer, String> regionMap;
    public static String num_departement = "0";

    /**
     * Constructeur par défaut de la classe Map
     * Initialise l'interface utilisateur et charge l'image de la carte
     */
    public Carte(String num_dep) {
        try {
            initRegionMap(); // Initialise the region map
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initRegionMap() {
        regionMap = new HashMap<>();
        regionMap.put(53, "29");
        regionMap.put(45, "22");
        regionMap.put(63, "56");
        regionMap.put(57, "35");
        regionMap.put(30, "50");
        regionMap.put(23, "14");
        regionMap.put(61, "44");
        regionMap.put(25, "27");
        regionMap.put(76, "10");
        regionMap.put(58, "53");
        regionMap.put(61, "72");
        regionMap.put(75, "49");
        regionMap.put(76, "44");
        regionMap.put(87, "85");
        regionMap.put(99, "79");
        regionMap.put(98, "86");
        regionMap.put(113, "16");
        regionMap.put(115, "17");
        regionMap.put(132, "33");
        regionMap.put(145, "40");
        regionMap.put(159, "64");
        regionMap.put(47, "136");
        regionMap.put(125, "24");
        regionMap.put(109, "87");
        regionMap.put(105, "23");
        regionMap.put(116, "19");

        regionMap.put(162, "65");
        regionMap.put(151, "32");
        regionMap.put(163, "31");
        regionMap.put(138, "82");
        regionMap.put(133, "46");
        regionMap.put(165, "09");
        regionMap.put(150, "81");
        regionMap.put(140, "12");
        regionMap.put(164, "11");
        regionMap.put(174, "66");
        regionMap.put(153, "34");
        regionMap.put(146, "30");
        regionMap.put(135, "48");

        regionMap.put(154, "13");
        regionMap.put(156, "83");
        regionMap.put(144, "06");
        regionMap.put(139, "04");
        regionMap.put(141, "84");
        regionMap.put(131, "05");

        regionMap.put(166, "2B");
        regionMap.put(181, "2A");

        regionMap.put(124, "15");
        regionMap.put(120, "43");
        regionMap.put(130, "07");
        regionMap.put(134, "26");
        regionMap.put(121, "38");
        regionMap.put(114, "73");
        regionMap.put(104, "74");
        regionMap.put(106, "01");
        regionMap.put(108, "69");
        regionMap.put(112, "42");
        regionMap.put(111, "63");
        regionMap.put(101, "03");

        regionMap.put(193, "71");
        regionMap.put(80, "58");
        regionMap.put(88, "39");
        regionMap.put(81, "25");
        regionMap.put(72, "70");
        regionMap.put(64, "90");
        regionMap.put(77, "21");
        regionMap.put(69, "89");

        regionMap.put(65, "68");
        regionMap.put(43, "67");
        regionMap.put(27, "57");
        regionMap.put(39, "54");
        regionMap.put(54, "88");
        regionMap.put(60, "52");
        regionMap.put(52, "10");
        regionMap.put(31, "51");
        regionMap.put(33, "55");
        regionMap.put(11, "08");

        regionMap.put(0, "59");
        regionMap.put(2, "62");
        regionMap.put(6, "80");
        regionMap.put(20, "02");
        regionMap.put(14, "60");

        regionMap.put(51, "28");
        regionMap.put(66, "45");
        regionMap.put(73, "41");
        regionMap.put(79, "37");
        regionMap.put(86, "36");
        regionMap.put(84, "18");

        regionMap.put(48, "77");
        regionMap.put(42, "91");
        regionMap.put(32, "78");
        regionMap.put(17, "95");
        regionMap.put(24, "Région parisienne");
        regionMap.put(26, "Région parisienne");
    }
    /**
     * Initialise l'interface utilisateur avec la carte chargée
     * 
     * @throws Exception Si une erreur se produit lors du chargement de l'image
     */
    public final void initUI() throws Exception {
        if (ui != null) {
            return;
        }
        String filePath = "lib" + File.separator + "carte_france.jpg";
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);
        area = getOutline(Color.WHITE, image, 12);
        shapeList = separateShapeIntoRegions(area);
        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(4, 4, 4, 4));
        output.addMouseListener(new MouseClickListener());
        output.addMouseMotionListener(new MouseListen());
        ui.add(output);
        refresh();
    }

    public void updateUI() {

    }

    /**
     * Obtient le contour de la carte sous forme d'une zone délimitée par une
     * couleur spécifiée.
     * 
     * @param target    La couleur cible du contour de la carte
     * @param bi        L'image de la carte
     * @param tolerance La tolérance pour la détection de couleur
     * @return La zone délimitée par le contour de la carte
     */
    public Area getOutline(Color target, BufferedImage bi, int tolerance) {
        // construct the GeneralPath
        GeneralPath gp = new GeneralPath();
        boolean cont = false;
        for (int xx = 0; xx < bi.getWidth(); xx++) {
            for (int yy = 0; yy < bi.getHeight(); yy++) {
                if (isIncluded(new Color(bi.getRGB(xx, yy)), target, tolerance)) {
                    // if (bi.getRGB(xx,yy)==targetRGB) {
                    if (cont) {
                        gp.lineTo(xx, yy);
                        gp.lineTo(xx, yy + 1);
                        gp.lineTo(xx + 1, yy + 1);
                        gp.lineTo(xx + 1, yy);
                        gp.lineTo(xx, yy);
                    } else {
                        gp.moveTo(xx, yy);
                    }
                    cont = true;
                } else {
                    cont = false;
                }
            }
            cont = false;
        }
        gp.closePath();

        // construct the Area from the GP & return it
        return new Area(gp);
    }

    /**
     * Vérifie si une couleur spécifique est incluse dans une autre avec une
     * tolérance donnée.
     * 
     * @param target    La couleur cible à vérifier
     * @param pixel     La couleur du pixel à comparer
     * @param tolerance La tolérance pour la comparaison des couleurs
     * @return true si la couleur cible est incluse dans la couleur du pixel avec la
     *         tolérance spécifiée, sinon false
     */
    public static boolean isIncluded(Color target, Color pixel, int tolerance) {
        int rT = target.getRed();
        int gT = target.getGreen();
        int bT = target.getBlue();
        int rP = pixel.getRed();
        int gP = pixel.getGreen();
        int bP = pixel.getBlue();
        return ((rP - tolerance <= rT) && (rT <= rP + tolerance)
                && (gP - tolerance <= gT) && (gT <= gP + tolerance)
                && (bP - tolerance <= bT) && (bT <= bP + tolerance));
    }
    /**
     * Sépare la forme principale de la carte en régions distinctes
     * 
     * @param shape La forme principale de la carte
     * @return Une liste de formes représentant les régions distinctes de la carte
     */
    public static ArrayList<Shape> separateShapeIntoRegions(Shape shape) {
        ArrayList<Shape> regions = new ArrayList<>();
        PathIterator pi = shape.getPathIterator(null);
        GeneralPath gp = new GeneralPath();
        while (!pi.isDone()) {
            double[] coords = new double[6];
            int pathSegmentType = pi.currentSegment(coords);
            int windingRule = pi.getWindingRule();
            gp.setWindingRule(windingRule);
            if (pathSegmentType == PathIterator.SEG_MOVETO) {
                gp = new GeneralPath();
                gp.setWindingRule(windingRule);
                gp.moveTo(coords[0], coords[1]);
            } else if (pathSegmentType == PathIterator.SEG_LINETO) {
                gp.lineTo(coords[0], coords[1]);
            } else if (pathSegmentType == PathIterator.SEG_QUADTO) {
                gp.quadTo(coords[0], coords[1], coords[2], coords[3]);
            } else if (pathSegmentType == PathIterator.SEG_CUBICTO) {
                gp.curveTo(
                        coords[0], coords[1],
                        coords[2], coords[3],
                        coords[4], coords[5]);
            } else if (pathSegmentType == PathIterator.SEG_CLOSE) {
                gp.closePath();
                regions.add(new Area(gp));
            } else {
                System.err.println("Unexpected value! " + pathSegmentType);
            }

            pi.next();
        }

        return regions;
    }

    /**
     * Classe interne pour gérer les événements de clic de souris sur la carte
     */
    class MouseListen implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // Code à exécuter lorsque la souris est déplacée en maintenant un bouton
            // enfoncé
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int width = output.getWidth();
            int height = output.getHeight();
            boolean valid = true;
            if (x >= 0 && x < width && y >= 0 && y < height && valid) {
                refresh();
            } else {
            }
        }

    }

    class MouseClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX(); // Coordonnée X du clic de la souris
            int y = e.getY(); // Coordonnée Y du clic de la souris
            int i = 0;
            for (Shape s : shapeList) {

                if (s.contains(x, y)) {
                    if (regionMap.containsKey(i)) {
                        System.out.println(regionMap.get(i));
                        num_departement=regionMap.get(i);
                        System.out.println(num_departement);
                        
                    }
                }
                i++;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Code à exécuter lorsque le bouton de la souris est enfoncé
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Code à exécuter lorsque le bouton de la souris est relâché
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Code à exécuter lorsque la souris entre dans la zone du composant
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Code à exécuter lorsque la souris quitte la zone du composant
        }

    }


    private void refresh() {
        output.setIcon(new ImageIcon(getImage()));
    }

    private BufferedImage getImage() {
        BufferedImage bi = new BufferedImage(
                2 * SIZE, SIZE, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bi.createGraphics();
        g.drawImage(image, 0, 0, output);
        g.setColor(Color.WHITE);
        g.fill(area);
        g.setColor(Color.BLACK);
        g.draw(area);
        try {
            Point p = MouseInfo.getPointerInfo().getLocation();
            Point p1 = output.getLocationOnScreen();
            int x = p.x - p1.x;
            int y = p.y-100 - p1.y;
            Point pointOnImage = new Point(x, y);
            int i = 0;
            for (Shape shape : shapeList) {
                if (shape.contains(pointOnImage)
                        && ((i < 175 && i != 129 && i != 149 && i != 56 && i != 70 && i != 102) || i == 181)) {
                    g.setColor(Color.BLUE);
                    g.fill(shape);
                    i++;
                    break;
                }
                i++;
            }
        } catch (Exception doNothing) {
        }

        g.dispose();

        return bi;
    }

    public JComponent getUI() {
        return ui;
    }

    /**
     * Méthode principale pour tester la classe Map
     */
    public static void main(String[] args) {
        Runnable r = () -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Carte o = new Carte(num_departement);
            JFrame f = new JFrame(o.getClass().getSimpleName());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);
            f.setContentPane(o.getUI());
            f.setResizable(false);
            f.pack();
            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }
}
