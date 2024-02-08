package fr.univrennes.istic.l2gen.geometrie;

/**
 * Cette classe représente un rectangle dans un plan cartésien.
 * Elle implémente l'interface IForme.
 */
public class Rectangle implements IForme{
    private Point p;
    private double largeur;
    private double hauteur;

    /**
     * Constructeur de la classe Rectangle.
     * Crée un rectangle avec les coordonnées du coin supérieur gauche, la largeur et la hauteur spécifiées.
     * @param x La coordonnée x du coin supérieur gauche du rectangle.
     * @param y La coordonnée y du coin supérieur gauche du rectangle.
     * @param largeur La largeur du rectangle.
     * @param hauteur La hauteur du rectangle.
     */
    public Rectangle(double x, double y, double largeur, double hauteur) {
        this.p = new Point(x, y);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /**
     * Constructeur de la classe Rectangle.
     * Crée un rectangle avec le point du coin supérieur gauche, la largeur et la hauteur spécifiées.
     * @param p Le point du coin supérieur gauche du rectangle.
     * @param largeur La largeur du rectangle.
     * @param hauteur La hauteur du rectangle.
     */
    public Rectangle(Point p, double largeur, double hauteur) {
        this.p = p;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /**
     * Retourne le centre du rectangle.
     * @return Le point représentant le centre du rectangle.
     */
    public Point centre() {
        return p;
    }

    /**
     * Retourne une description textuelle du rectangle avec un certain niveau d'indentation.
     * @param indentation Le niveau d'indentation de la description.
     * @return La description textuelle du rectangle.
     */
    public String description(int indentation) {
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += " ";
        }
        return indent + "Rectangle " + "Centre=" + (int) centre().x() +"," + (int) centre().y() + " L=" + largeur + " H=" + hauteur;
    }

    /**
     * Retourne la hauteur du rectangle.
     * @return La hauteur du rectangle.
     */
    public double hauteur() {
        return hauteur;
    }

    /**
     * Retourne la largeur du rectangle.
     * @return La largeur du rectangle.
     */
    public double largeur() {
        return largeur;
    }

    /**
     * Déplace le rectangle selon les valeurs spécifiées de déplacement en x et en y.
     * @param dx La valeur de déplacement en x.
     * @param dy La valeur de déplacement en y.
     */
    public void deplacer(double dx, double dy) {
        p = p.plus(dx, dy);
    }

    /**
     * Duplique le rectangle.
     * @return Une nouvelle instance de Rectangle identique à celle actuelle.
     */
    public IForme dupliquer() {
        return new Rectangle(p, largeur, hauteur);
    }

    /**
     * Redimensionne le rectangle selon les proportions spécifiées.
     * @param px Le facteur de redimensionnement en largeur.
     * @param py Le facteur de redimensionnement en hauteur.
     */
    public void redimensionner(double px, double py) {
        largeur *= px;
        hauteur *= py;
    }

    /**
     * Retourne une représentation SVG du rectangle.
     * @return La représentation SVG du rectangle.
     */
    public String enSVG() {
        return "<rect x=\"" + centre().x() + "\" y=\"" + centre().y() + "\" width=\"" + largeur() + "\" height=\""
                + hauteur()
                + "\"\n" + "\t" + "fill=\"white\" stroke=\"black\"/>";
    }
}
