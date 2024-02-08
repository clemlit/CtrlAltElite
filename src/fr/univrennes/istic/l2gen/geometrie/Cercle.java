/**
 * La classe Cercle représente un cercle dans un espace bidimensionnel.
 * Elle implémente l'interface IForme pour fournir des fonctionnalités
 * liées à la manipulation de formes géométriques.
 */
package fr.univrennes.istic.l2gen.geometrie;

public class Cercle implements IForme {

    /**
     * Coordonnée x du centre du cercle.
     */
    private double x;

    /**
     * Coordonnée y du centre du cercle.
     */
    private double y;

    /**
     * Rayon du cercle.
     */
    private double r;

    /**
     * Constructeur de la classe Cercle.
     *
     * @param x Coordonnée x du centre du cercle.
     * @param y Coordonnée y du centre du cercle.
     * @param r Rayon du cercle.
     */
    public Cercle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    /**
     * Constructeur de la classe Cercle prenant un point et un rayon.
     *
     * @param p Point représentant les coordonnées du centre du cercle.
     * @param r Rayon du cercle.
     */
    public Cercle(Point p, double r) {
        this.x = p.x();
        this.y = p.y();
        this.r = r;
    }

    /**
     * Retourne un Point représentant les coordonnées du centre du cercle.
     *
     * @return Point représentant le centre du cercle.
     */
    public Point centre() {
        return new Point(this.x, this.y);
    }

    /**
     * Retourne une description du cercle avec une indentation spécifiée.
     *
     * @param indentation Niveau d'indentation pour la description.
     * @return Chaîne de caractères décrivant le cercle.
     */
    public String description(int indentation) {
        Point centre = centre();
        String ind = "";
        for (int i = 0; i < indentation; i++) {
            ind += "  ";
        }
        return ind + "Cercle centre = " + centre.x() + " , " + centre.y() + " r = " + this.r;
    }

    /**
     * Retourne la hauteur du cercle (équivalente au rayon).
     *
     * @return Hauteur du cercle.
     */
    public double hauteur() {
        return r;
    }

    /**
     * Retourne la largeur du cercle (équivalente à deux fois le rayon).
     *
     * @return Largeur du cercle.
     */
    public double largeur() {
        return r * 2;
    }

    /**
     * Duplique le cercle en créant une nouvelle instance avec les mêmes propriétés.
     *
     * @return Nouvelle instance de Cercle identique à l'instance actuelle.
     */
    public IForme dupliquer() {
        Cercle copieCercle = new Cercle(this.x, this.y, this.r);
        return copieCercle;
    }

    /**
     * Redimensionne le cercle en multipliant les coordonnées x et y par les facteurs spécifiés.
     *
     * @param px Facteur de redimensionnement pour l'axe des x.
     * @param py Facteur de redimensionnement pour l'axe des y.
     */
    public void redimmensionner(double px, double py) {
        this.x = x * px;
        this.y = y * py;
    }

    /**
     * Déplace le cercle en ajoutant les valeurs spécifiées aux coordonnées x et y.
     *
     * @param dx Déplacement sur l'axe des x.
     * @param dy Déplacement sur l'axe des y.
     */
    public void deplacer(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Redimensionne le cercle en multipliant le rayon par le facteur maximum entre dx et dy.
     *
     * @param dx Facteur de redimensionnement pour l'axe des x.
     * @param dy Facteur de redimensionnement pour l'axe des y.
     */
    @Override
    public void redimensionner(double dx, double dy) {
        this.r = this.r * Math.max(dx, dy);
    }

    /**
     * Génère une représentation SVG du cercle.
     *
     * @return Chaîne de caractères représentant le cercle en format SVG.
     */
    @Override
    public String enSVG() {
        Point centre = centre();
        String svg = "<circle cx=\"" + centre.x() + "\" cy=\"" + centre.y() + "\" r=\"" + this.r + "\" fill=\"white\" stroke=\"black\" />";
        return svg;
    }
}
