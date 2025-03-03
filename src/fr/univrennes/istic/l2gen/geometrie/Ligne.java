/**
 * La classe Ligne représente une ligne définie par une liste de sommets (points) dans un espace bidimensionnel.
 * Elle implémente l'interface IForme pour fournir des fonctionnalités liées à la manipulation de lignes.
 */
package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente une ligne dans un espace géométrique.
 * Une ligne est définie par une liste de points qui la composent.
 * Elle implémente l'interface IForme.
 */
public class Ligne implements IForme {

    // ATTRIBUTS

    /**
     * Liste des sommets (points) de la ligne.
     */
    private List<Point> sommets;
    private String couleur = "White";
    private int angle;

    // CONSTRUCTEURS

    /**
     * Constructeur de la classe Ligne à partir d'un tableau de coordonnées.
     *
     * @param l Tableau de coordonnées alternant entre les valeurs x et y.
     * @requires Le tableau l doit avoir un nombre pair d'élément.
     */
    public Ligne(double... l) {
        this.sommets = new ArrayList<>();
        for (int i = 0; i < l.length; i += 2) {
            this.sommets.add(new Point(l[i], l[i + 1]));
        }
        this.angle = 0;
    }

    /**
     * Constructeur de la classe Ligne.
     * Crée une ligne à partir d'une liste de points.
     * 
     * @param sommets la liste des points qui composent la ligne
     * @requires La liste de sommet ne doit pas être nul.
     */
    public Ligne(List<Point> sommets) {
        this.sommets = new ArrayList<>(sommets);
    }

    // METHODES

    /**
     * Ajoute un sommet à la liste des sommets de la ligne.
     *
     * @param p Sommet (point) à ajouter.
     */
    public void ajouterSommet(Point p) {
        this.sommets.add(p);
    }

    /**
     * Ajoute un sommet à la liste des sommets de la ligne à partir de coordonnées
     * spécifiées.
     *
     * @param x Coordonnée x du sommet.
     * @param y Coordonnée y du sommet.
     */
    public void ajouterSommetCor(double x, double y) {
        this.sommets.add(new Point(x, y));
    }

    /**
     * Retourne le centre de la ligne, calculé comme la moyenne des coordonnées de
     * tous les sommets.
     *
     * @return Centre de la ligne.
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
     * Génère une description textuelle de la ligne avec l'indentation spécifiée.
     *
     * @param indentation Niveau d'indentation pour la description.
     * @require indentation >= 0.
     * @return Chaîne de caractères décrivant la ligne.
     */
    public String description(int indentation) {
        String indent = "  ";
        for (int i = 0; i < indentation; i++) {
            indent += "  ";
        }
        String sommet = "";
        for (int i = 0; i < sommets.size(); i++) {
            sommet += sommets.get(i).x() + "," + sommets.get(i).y() + " ";
        }
        return indent + "Ligne " + sommet;
    }

    /**
     * Retourne la liste des sommets de la ligne.
     *
     * @return Liste des sommets de la ligne.
     */
    public List<Point> getSommets() {
        return new ArrayList<>(this.sommets);
    }

    /**
     * Calcule et retourne la hauteur de la ligne comme la différence entre la
     * coordonnée y maximale
     * et la coordonnée y minimale parmi tous les sommets.
     *
     * @return Hauteur de la ligne.
     * @ensures La valeur rendu doit être strictement positives.
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
     * Calcule et retourne la largeur de la ligne comme la différence entre la
     * coordonnée x maximale
     * et la coordonnée x minimale parmi tous les sommets.
     *
     * @return Largeur de la ligne.
     * @ensures La valeur rendu doit être strictement positives.
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
     * Déplace tous les sommets de la ligne selon les déplacements spécifiés sur les
     * axes x et y.
     *
     * @param dx Déplacement sur l'axe des x.
     * @param dy Déplacement sur l'axe des y.
     * @ensures La ligne ne doit pas dépasser les limites d'affichage.
     */
    public IForme deplacer(double dx, double dy) {
        for (Point sommet : sommets) {
            sommet.plus(dx, dy);
        }
        return this;
    }

    /**
     * Duplique la ligne en créant une nouvelle instance avec des sommets
     * identiques.
     *
     * @return Nouvelle instance de la ligne.
     */
    public IForme dupliquer() {
        List<Point> nouveauxSommets = new ArrayList<>();
        for (Point sommet : sommets) {
            nouveauxSommets.add(new Point(sommet.x(), sommet.y()));
        }
        return new Ligne(nouveauxSommets);
    }

    /**
     * Redimensionne tous les sommets de la ligne selon les facteurs spécifiés sur
     * les axes x et y.
     *
     * @param px Facteur de redimensionnement pour l'axe des x.
     * @param py Facteur de redimensionnement pour l'axe des y.
     * @requires les valeurs de px et py doivent être strictement positives.
     * @ensures la IForme ne dépasse pas les limites de l'affichage.
     */
    public IForme redimensionner(double px, double py) {
        Point centre = centre();

        for (Point sommet : sommets) {
            double distanceX = sommet.x() - centre.x();
            double distanceY = sommet.y() - centre.y();

            double nouvelleDistanceX = distanceX * px;
            double nouvelleDistanceY = distanceY * py;

            sommet.setX(centre.x() + nouvelleDistanceX);
            sommet.setY(centre.y() + nouvelleDistanceY);
        }
        return this;
    }

    /**
     * Génère une représentation SVG de la ligne.
     *
     * @return Chaîne de caractères représentant la ligne en SVG.
     */
    public String enSVG() {
        // Construction de la chaîne SVG
        String svg = "<polyline points=\"";

        // Ajout des attributs de la ligne
        for (Point point : sommets) {
            svg += point.x() + " " + point.y() + " ";
        }

        svg += "\" fill=\"" + couleur + "\" stroke=\"black\"";

        // Ajout de la transformation de rotation
        if (angle != 0) {
            Point centre = centre();
            svg += " transform=\"rotate(" + angle + " " + centre.x() + " " + centre.y() + ")\"";
        }

        svg += " />\n";

        return svg;
    }

    /**
     * Change la couleur de la ligne en utilisant la première couleur spécifiée dans
     * le tableau.
     *
     * @param couleurs Tableau de couleurs à appliquer.
     * @require couleurs n'est pas vide.
     * @require couleurs est un tableau de couleurs existantes dans la bibliothèque
     *          SVG.
     * @return L'objet Ligne avec la nouvelle couleur.
     */
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

    /**
     * Renvoie la couleur actuelle de la ligne.
     *
     * @return La couleur de la ligne.
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Fait tourner la ligne en spécifiant un angle.
     *
     * @param angle L'angle de rotation à appliquer a la ligne.
     * @return L'objet Ligne tourné.
     */
    public IForme tourner(int angle) {
        this.angle += angle;
        return this;
    }

    /**
     * Renvoie l'angle actuel de la ligne.
     *
     * @return L'angle de la ligne.
     */
    public int getAngle() {
        return angle;
    }

    @Override
    public String createEnSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + enSVG() + "</svg>";
    }
}
