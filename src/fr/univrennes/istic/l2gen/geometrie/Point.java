/**
 * La classe Point représente un point dans un espace bidimensionnel défini par ses coordonnées x et y.
 * Elle fournit des méthodes pour la comparaison, l'addition avec d'autres points ou des valeurs scalaires,
 * l'accès aux coordonnées, et la redimensionnement du point.
 */
package fr.univrennes.istic.l2gen.geometrie;

public class Point {
    // ATTRIBUTS

    /**
     * Coordonnée x du point.
     */
    private double x;

    /**
     * Coordonnée y du point.
     */
    private double y;

    // CONSTRUCTEUR

    /**
     * Constructeur de la classe Point.
     *
     * @param x Coordonnée x du point.
     * @param y Coordonnée y du point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // METHODES

    /**
     * Compare ce point avec un autre objet.
     *
     * @param object Objet à comparer avec le point.
     * @return true si les objets sont identiques, false sinon.
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true; // Les objets sont identiques, pas besoin de comparer plus loin
        }
        if (object == null || getClass() != object.getClass()) {
            return false; // L'objet n'est pas une instance de la classe Point
        }
        Point otherPoint = (Point) object;

        return Double.compare(otherPoint.x, this.x) == 0 && Double.compare(otherPoint.y, this.y) == 0;
    }

    /**
     * Ajoute les coordonnées d'un autre point à ce point et retourne un nouveau
     * point résultant de l'addition.
     *
     * @param point Point à ajouter à ce point.
     * @return Nouveau point résultant de l'addition.
     */
    public Point plus(Point point) {
        double newX = this.x + point.x;
        double newY = this.y + point.y;

        return new Point(newX, newY);
    }

    /**
     * Ajoute des valeurs spécifiées aux coordonnées de ce point.
     *
     * @param x Valeur à ajouter à la coordonnée x.
     * @param y Valeur à ajouter à la coordonnée y.
     * @return Ce point après l'addition.
     */
    public Point plus(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Retourne la coordonnée x du point.
     *
     * @return Coordonnée x du point.
     */
    public double x() {
        return x;
    }

    /**
     * Retourne la coordonnée y du point.
     *
     * @return Coordonnée y du point.
     */
    public double y() {
        return y;
    }

    /**
     * Redimensionne les coordonnées du point en fonction des facteurs spécifiés sur
     * les axes x et y.
     *
     * @param px Facteur de redimensionnement pour l'axe des x.
     * @param py Facteur de redimensionnement pour l'axe des y.
     */
    public void redimensionner(double px, double py) {
        this.x *= px;
        this.y *= py;
    }
}
