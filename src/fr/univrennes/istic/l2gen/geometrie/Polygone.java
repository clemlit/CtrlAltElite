package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

public class Polygone implements IForme {
    List<Point> liste;
    private String couleur;
    private int angle;

    public Polygone(double... l) {
        this.liste = new ArrayList<Point>();
        for (int i = 0; i < l.length; i = i + 2) {
            Point p = new Point(l[i], l[i + 1]);
            liste.add(p);
        }
        this.angle = 0;
    }

    /**
     * @return hauteur du polygone
     */
    public double hauteur() {
        ArrayList<Point> liste = getSommets();
        double minY = liste.get(0).y();
        double maxY = liste.get(0).y();
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).y() < minY) {
                minY = liste.get(i).y();
            }
            if (liste.get(i).y() > maxY) {
                maxY = liste.get(i).y();
            }
        }
        return maxY - minY;
    }

    /**
     * @return centre du polygone
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
     * @return largeur polygone
     */
    public double largeur() {
        ArrayList<Point> liste = getSommets();
        double minX = liste.get(0).x();
        double maxX = liste.get(0).x();
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).x() < minX) {
                minX = liste.get(i).x();
            }
            if (liste.get(i).x() > maxX) {
                maxX = liste.get(i).x();
            }
        }
        return maxX - minX;
    }

    /**
     * Redimensionne le rectangle selon les proportions spécifiées.
     * 
     * @param px Le facteur de redimensionnement en largeur.
     * @param py Le facteur de redimensionnement en hauteur.
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
        return this;
    }

    /**
     * Déplace le rectangle selon les valeurs spécifiées de déplacement en x et en
     * y.
     * 
     * @param dx La valeur de déplacement en x.
     * @param dy La valeur de déplacement en y.
     */
    public IForme deplacer(double dx, double dy) {
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
    public void ajouterSommetPoint(Point p) {
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
        StringBuilder svgBuilder = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\"> <g>\n<polygon points=\"");

        for (Point sommet : liste) {
            svgBuilder.append(sommet.x()).append(" ").append(sommet.y()).append(" ");
        }

        svgBuilder.append("\" fill=\"" + couleur + "\" stroke=\"black\"");

        // Ajouter la transformation de rotation ici
        if (angle != 0) {
            svgBuilder.append(" transform=\"rotate(").append(angle).append(" ").append(centre().x()).append(" ")
                    .append(centre().y()).append(")\"");
        }

        svgBuilder.append(" />\n</g>\n</svg>");

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
        copiePolygone.angle=angle;
        return copiePolygone;
    }

    @Override
    public IForme colorier(String... couleurs) {
        if (couleurs.length > 0) {
            // Ici, vous pouvez prendre la première couleur du tableau couleurs
            String couleur = couleurs[0];
            // Implémentation pour colorier un cercle avec la couleur spécifiée
            this.couleur = couleur;
        }
        return this;
    }

    public String getCouleur() {
        return couleur;
    }

    public IForme tourner(int angle) {
        this.angle += angle;
        return this;
    }

    public int getAngle() {
        return angle;
    }
}
