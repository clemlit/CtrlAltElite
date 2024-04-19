import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
// RAJOUTER SI LE POINT EST DANS REGION COLORER SINON NON
/**
 * Cette classe représente une carte interactive affichée dans une interface
 * graphique Swing
 * Elle permet de détecter les régions cliquées par l'utilisateur
 */

public class Carte {

    private JComponent ui = null;
    JLabel output = new JLabel();
    public static final int SIZE = 750;
    BufferedImage image;
    Area area;
    ArrayList<Shape> shapeList;
    public static int numeroMap = 1;
    public static int numeroActuel = 1;



    /**
     * Constructeur par défaut de la classe Map
     * Initialise l'interface utilisateur et charge l'image de la carte
     */
    public Carte(int numeroMap) {
        try {
            initUI(numeroMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initialise l'interface utilisateur avec la carte chargée
     * 
     * @throws Exception Si une erreur se produit lors du chargement de l'image
     */
    public final void initUI(int numeroMap) throws Exception {
        if (ui != null) {
            return;
        }
        String urlString="";
        switch (numeroMap) {
            case 1:
            urlString = "https://img.freepik.com/premium-vector/france-map-background-with-states-france-map-isolated-white-background-vector-illustration_1003415-27.jpg";
                break;
            case 2:
            urlString = "https://d-maps.com/m/europa/france/bretagne/bretagne50.gif";
                break;
        }        
        URL url = new URL(urlString);
        image = ImageIO.read(url);
        area = getOutline(Color.WHITE, image, 12);
        shapeList = separateShapeIntoRegions(area);
        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(4, 4, 4, 4));
        output.addMouseListener(new MouseClickListener());
        output.addMouseMotionListener(new MouseListen());
        output.addMouseMotionListener(new MouseClickListener());
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
            boolean valid=true;
            //int i=0;
            if (x >= 0 && x < width && y >= 0 && y < height && valid) {
                refresh();
            } else {
            }
            /*for (Shape s : shapeList) {
                if (s.contains(x, y) && i!=122&&i!=73&&i) {
                    System.out.println(i);
                }i++;
            }*/

        }
    }
    

    class MouseClickListener implements MouseMotionListener, MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX(); // Coordonnée X du clic de la souris
            int y = e.getY(); // Coordonnée Y du clic de la souris
            int i = 0;
            for (Shape s : shapeList) {
                if (numeroMap == 1) {
                    if (s.contains(x, y)) {
                        switch (i) {
                            case 9:
                                System.out.println("Hauts-de-France");
                                break;
                            case 14:
                                System.out.println("Normandie");
                                break;
                            case 17:
                                System.out.println("Île-de-France");
                                numeroMap = 2;
                                break;
                            case 22:
                                System.out.println("Bretagne");
                                numeroMap = 2;
                                break;
                            case 24:
                                System.out.println("Grand Est");
                                break;
                            case 28:
                                System.out.println("Pays de la Loire");
                                break;
                            case 29:
                                System.out.println("Centre Val de Loire");
                                break;
                            case 31:
                                System.out.println("Bourgogne Franche-Comté");
                                break;
                            case 43:
                                System.out.println("Auvergne Rhône Alpes");
                                break;
                            case 51:
                                System.out.println("Nouvelle Aquitaine");
                                break;
                            case 52:
                                System.out.println("PACA");
                                break;
                            case 54:
                                System.out.println("Occitanie");
                                break;
                            case 58:
                                System.out.println("Corse");
                                break;

                        }
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

        @Override
        public void mouseDragged(MouseEvent e) {
            // Code à exécuter lorsque la souris est déplacée en maintenant un bouton
            // enfoncé
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // Code à exécuter lorsque la souris est déplacée sans maintenir de bouton
            // enfoncé
        }

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
        g.draw(area);
        try {
            Point p = MouseInfo.getPointerInfo().getLocation();
            Point p1 = output.getLocationOnScreen();
            int x = p.x - p1.x;
            int y = p.y - p1.y;
            Point pointOnImage = new Point(x, y);
            for (Shape shape : shapeList) {
                if (shape.contains(pointOnImage)&&shape!=shapeList.get(60)&&shape!=shapeList.get(55)&&shape!=shapeList.get(59)) {
                    g.setColor(Color.GREEN.darker());
                    g.fill(shape);
                    break;
                }
            }
        } catch (Exception doNothing) {
        }

        g.dispose();

        return bi;
    }

    public JComponent getUI(int numeroMap) {
        return ui;
    }


    public static void creationMap(int numeroMap,Carte o, JFrame f, boolean modifMap){
            if (!modifMap){
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);
            f.setLocation(10, 10);
            f.setContentPane(o.getUI(numeroMap));
            f.setResizable(true);
            f.pack();
            f.setVisible(true);
            }
            else {
                f.setContentPane(o.getUI(numeroMap));
                f.revalidate();
            }
    }
    /**
     * Méthode principale pour tester la classe Map.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        //boolean isOver = false;
        Runnable r = () -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Carte o = new Carte(1);
            JFrame f = new JFrame(o.getClass().getSimpleName());
            creationMap(numeroMap,o,f,false);
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (numeroMap != numeroActuel) {
                        System.out.print(numeroMap);
                        System.out.print(numeroActuel);

                        numeroActuel = numeroMap;
                        Carte n= new Carte(numeroMap);
                        creationMap(numeroMap, n, f, true);
                    }
                }
            });
            timer.start();
        };

        SwingUtilities.invokeLater(r);
    }
}
