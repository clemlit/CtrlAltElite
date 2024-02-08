package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente une ligne dans un espace géométrique.
 * Une ligne est définie par une liste de points qui la composent.
 * Elle implémente l'interface IForme.
 */
public class Ligne implements IForme {
    private List<Point> sommets;

    /**
     * Constructeur de la classe Ligne.
     * Crée une ligne à partir des coordonnées des sommets.
     * @param l les coordonnées des sommets de la ligne
     */
    public Ligne(double... l) {
        this.sommets = new ArrayList<>();
        for (int i = 0; i < l.length; i += 2) {
            this.sommets.add(new Point(l[i], l[i + 1]));
        }
    }

    /**
     * Constructeur de la classe Ligne.
     * Crée une ligne à partir d'une liste de points.
     * @param sommets la liste des points qui composent la ligne
     */
    public Ligne(List<Point> sommets) {
        this.sommets = new ArrayList<>(sommets);
    }

    /**
     * Ajoute un sommet à la ligne.
     * @param p le point à ajouter comme sommet
     */
    public void ajouterSommet(Point p) {
        this.sommets.add(p);
    }

    /**
     * Ajoute un sommet à la ligne à partir de ses coordonnées.
     * @param x la coordonnée x du sommet
     * @param y la coordonnée y du sommet
     */
    public void ajouterSommet(double x, double y) {
        this.sommets.add(new Point(x, y));
    }

    /**
     * Calcule le centre de la ligne.
     * @return le point représentant le centre de la ligne
     */
    public Point centre() {
        double sommeX = 0;
        double sommeY = 0;
        for (Point sommet : sommets) {
            sommeX += sommet.x();
            sommeY += sommet.y();
        }
        return new Point(sommeX / sommets.size(), sommeY / sommets.size());
    }

    /**
     * Retourne une description de la ligne avec une indentation spécifiée.
     * @param indentation le niveau d'indentation de la description
     * @return la description de la ligne
     */
    public String description(int indentation) {
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += " ";
        }
        String sommet = "";
        for (int i = 0; i < sommets.size(); i++) {
            sommet += sommets.get(i).x() + "," + sommets.get(i).y() + " ";
        }
        return indent + "Ligne " + sommet;
    }

    /**
     * Retourne la liste des sommets de la ligne.
     * @return la liste des sommets de la ligne
     */
    public List<Point> getSommets() {
        return new ArrayList<>(this.sommets);
    }

    /**
     * Calcule la hauteur de la ligne.
     * @return la hauteur de la ligne
     */
    public double hauteur() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Point sommet : sommets) {
            min = Math.min(min, sommet.y());
            max = Math.max(max, sommet.y());
        }
        return max - min;
    }

    /**
     * Calcule la largeur de la ligne.
     * @return la largeur de la ligne
     */
    public double largeur() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Point sommet : sommets) {
            min = Math.min(min, sommet.x());
            max = Math.max(max, sommet.x());
        }
        return max - min;
    }

    /**
     * Déplace la ligne selon les valeurs spécifiées.
     * @param dx la valeur de déplacement selon l'axe x
     * @param dy la valeur de déplacement selon l'axe y
     */
    public void deplacer(double dx, double dy) {
        for (Point sommet : sommets) {
            sommet.plus(dx, dy);
        }
    }

    /**
     * Duplique la ligne.
     * @return une nouvelle instance de la ligne avec les mêmes sommets
     */
    public IForme dupliquer() {
        List<Point> nouveauxSommets = new ArrayList<>();
        for (Point sommet : sommets) {
            nouveauxSommets.add(new Point(sommet.x(), sommet.y()));
        }
        return new Ligne(nouveauxSommets);
    }

    /**
     * Redimensionne la ligne selon les proportions spécifiées.
     * @param px le facteur de redimensionnement selon l'axe x
     * @param py le facteur de redimensionnement selon l'axe y
     */
    public void redimensionner(double px, double py) {
        for (Point sommet : sommets) {
            sommet.redimensionner(px, py);
        }
    }

    /**
     * Génère une représentation SVG de la ligne.
     * @return la représentation SVG de la ligne
     */
    public String enSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append("<line ");
        svg.append("x1=\"" + sommets.get(0).x() + "\" ");
        svg.append("y1=\"" + sommets.get(0).y() + "\" ");
        svg.append("x2=\"" + sommets.get(1).x() + "\" ");
        svg.append("y2=\"" + sommets.get(1).y() + "\" ");
        svg.append("stroke=\"black\" ");
        svg.append("/>");
        return svg.toString();
    }
}