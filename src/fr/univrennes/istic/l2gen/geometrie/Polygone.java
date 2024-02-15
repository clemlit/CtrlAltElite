package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

public class Polygone implements IForme {
    List<Point> liste;

    public Polygone(double ... l) {
        this.liste=new ArrayList<Point>();
        for (int i=0;i<l.length;i=i+2){
            Point p = new Point(l[i], l[i+1]);
            liste.add(p);
        }
    }
    /**
     * @return 0 car pas de hauteur que l'on peut définir pour un polygone quelconque
     */
    public double hauteur(){
        return 0;
    }
    /**
     * @return le point 0,0 car pas de centre que l'on peut définir pour un polygone quelconque
     */
    public Point centre() {
        if (liste.isEmpty()) {
            // Si la liste des sommets est vide, retourner le point (0, 0) par défaut
            return new Point(0, 0);
        }

        double sommeX = 0;
        double sommeY = 0;

        for (Point sommet : liste) {
            sommeX += sommet.x();
            sommeY += sommet.y();
        }

        double centreX = sommeX / liste.size();
        double centreY = sommeY / liste.size();

        return new Point(centreX, centreY);
    }

    /**
     * @return 0 car pas de largeur que l'on peut définir pour un polygone quelconque
     */
    public double largeur(){
        return 0;
    }
    /**
     * @return rien car jsp comment redimensionner un polygone quelconque
     */
    public IForme redimensionner(double dx, double dy) {
        Point centre = centre();

        for (Point sommet : liste) {
            double distanceX = sommet.x() - centre.x();
            double distanceY = sommet.y() - centre.y();

            double nouvelleDistanceX = distanceX * dx;
            double nouvelleDistanceY = distanceY * dy;

            sommet.setX(centre.x() + nouvelleDistanceX);
            sommet.setY(centre.y() + nouvelleDistanceY);
        }
        return ???;
    }

    /**
     * @return rien car jsp comment déplacer un polygone quelconque
     */
    public IForme deplacer(double dx, double dy){
        for (Point sommet : liste) {
            sommet.plus(dx, dy);
        }
        return this;
    }

    /**
     * @return Une liste de tous les sommets
     */
    public ArrayList<Point> getSommets() {
        ArrayList<Point> list = new ArrayList<Point>();
        for (int i = 0; i < liste.size(); i++) {
            list.add(liste.get(i));
        }
        return list;
    }
    /**
     * @param i entre les points
     * @return un String décrivant les coordonnées de tous les points du polygone
     */
    @Override
    public String description(int i) {
        StringBuilder txt = new StringBuilder("Polygone");
        for (int e = 0; e < liste.size(); e++) {
            txt.append(" ");
            for (int f = 0; f < i; f++) {
                txt.append(" ");
            }
            int x = (int) liste.get(e).x();
            int y = (int) liste.get(e).y();
            txt.append(x).append(",").append(y);
        }
        return txt.toString();
    }

    /**
     * @param p point quelconque avec coordonnées quelconques
     * @return ajoute un point au polygone
     */
    public void ajouterSommet(Point p) {
        liste.add(p);
    }
    
    /**
     * @param x coordonnée x du point que l'on veut ajouter
     * @param x coordonnée y du point que l'on veut ajouter
     * @return ajoute le point avec les coordonnées correspondantes au polygone
     */
    public void ajouterSommet(double x, double y) {
        Point p = new Point(x, y);
        liste.add(p);
    }
    
    /**
     * @return le fichier SVG créant le polygone
     */
    @Override
    public String enSVG() {
        StringBuilder svgBuilder = new StringBuilder("<g>\n<polygon points=\"");

        for (Point sommet : liste) {
            svgBuilder.append(sommet.x()).append(" ").append(sommet.y()).append(" ");
        }

        svgBuilder.append("\" fill=\"white\" stroke=\"black\" />\n</g>\n");

        return svgBuilder.toString();
    }

    public double[] getListe() {
        double[] listeDouble = new double[liste.size() * 2];
        int e = 0;
        for (int i = 0; i < liste.size(); i++) {
            listeDouble[e] = liste.get(i).x();
            listeDouble[e + 1] = liste.get(i).y();
            e = e + 2;
        }
        return listeDouble;
    }

    public IForme dupliquer() {
        Polygone copiePolygone = new Polygone(getListe());
        return copiePolygone;
    }

}
