/**
 * La classe Ellipse représente une ellipse dans un espace 2D.
 * Elle implémente l'interface IForme, fournissant des méthodes pour manipuler et décrire l'ellipse.
 */
package fr.univrennes.istic.l2gen.geometrie;

public class Ellipse implements IForme {

    // ATTRIBUTS
    private double x; // Coordonnée x du centre de l'ellipse
    private double y; // Coordonnée y du centre de l'ellipse
    private double demiGrandAxe; // Demi-grand axe
    private double demiPetitAxe; // Demi-petit axe

    /**
     * Construit une Ellipse avec les paramètres spécifiés.
     *
     * @param x            La coordonnée x du centre de l'ellipse.
     * @param y            La coordonnée y du centre de l'ellipse.
     * @param demiGrandAxe La longueur du demi-grand axe.
     * @param demiPetitAxe La longueur du demi-petit axe.
     */
    public Ellipse(double x, double y, double demiGrandAxe, double demiPetitAxe) {
        this.x = x;
        this.y = y;
        this.demiGrandAxe = demiGrandAxe;
        this.demiPetitAxe = demiPetitAxe;
    }

    /**
     * Construit une Ellipse avec l'objet Point spécifié et les longueurs des axes.
     *
     * @param point        Le Point représentant le centre de l'ellipse.
     * @param demiGrandAxe La longueur du demi-grand axe.
     * @param demiPetitAxe La longueur du demi-petit axe.
     */
    public Ellipse(Point point, double demiGrandAxe, double demiPetitAxe) {
        this.x = point.x();
        this.y = point.y();
        this.demiGrandAxe = demiGrandAxe;
        this.demiPetitAxe = demiPetitAxe;
    }

    /**
     * Retourne le Point central de l'ellipse.
     *
     * @return Le Point central de l'ellipse.
     */
    public Point centre() {
        return new Point(x, y);
    }

    /**
     * Génère une description sous forme de chaîne de caractères de l'ellipse avec
     * une indentation.
     *
     * @param indentation Le nombre d'espaces pour l'indentation.
     * @return La description formatée de l'ellipse.
     */
    public String description(int indentation) {
        String espaces = "";
        Point point = centre();
        for (int i = 0; i < indentation; i++) {
            espaces += "  ";
        }
        return espaces + "Ellipse  Centre=" + (int) point.x() + "," + (int) point.y() + "  rx=" + largeur() + "  ry="
                + hauteur();
    }

    /**
     * Retourne la longueur du demi-petit axe de l'ellipse.
     *
     * @return La longueur du demi-petit axe.
     */
    public double hauteur() {
        return demiPetitAxe;
    }

    /**
     * Retourne la longueur du demi-grand axe de l'ellipse.
     *
     * @return La longueur du demi-grand axe.
     */
    public double largeur() {
        return demiGrandAxe;
    }

    /**
     * Redimensionne l'ellipse en mettant à l'échelle les longueurs de ses axes.
     *
     * @param dx Le facteur d'échelle pour le demi-grand axe.
     * @param dy Le facteur d'échelle pour le demi-petit axe.
     */
    public void redimensionner(double dx, double dy) {
        this.demiGrandAxe = demiGrandAxe * dx;
        this.demiPetitAxe = demiPetitAxe * dy;
    }

    /**
     * Déplace l'ellipse des montants spécifiés dans les directions x et y.
     *
     * @param dx La quantité à déplacer dans la direction x.
     * @param dy La quantité à déplacer dans la direction y.
     */
    public void deplacer(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Crée et retourne une copie de l'ellipse.
     *
     * @return Un nouvel objet Ellipse avec les mêmes paramètres.
     */
    public IForme dupliquer() {
        Ellipse copieEllipse = new Ellipse(this.x, this.y, this.demiGrandAxe, this.demiPetitAxe);
        return copieEllipse;
    }

    /**
     * Génère une représentation SVG de l'ellipse.
     *
     * @return La chaîne SVG représentant l'ellipse.
     */
    public String enSVG() {
        // Construction de la chaîne SVG
        String svg = "<ellipse";

        // Ajout des attributs de l'ellipse
        svg += " cx=\"" + x + "\"";
        svg += " cy=\"" + y + "\"";
        svg += " rx=\"" + demiGrandAxe + "\"";
        svg += " ry=\"" + demiPetitAxe + "\"";
        svg += " fill=\"white\"";
        svg += " stroke=\"black\"";
        svg += " />\n";

        return svg;
    }
}
