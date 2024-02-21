package fr.univrennes.istic.l2gen.geometrie;

/**
 * La classe Triangle représente un triangle dans un espace bidimensionnel.
 * Un triangle peut être défini par ses coordonnées x et y de trois points.
 * Les méthodes de la classe permettent d'effectuer diverses opérations sur le
 * triangle,
 * telles que calculer le centre, la hauteur, la largeur, redimensionner,
 * déplacer,
 * dupliquer et générer le code SVG correspondant.
 */
public class Triangle implements IForme {

    /*
     * Les coordonnées du point X
     */
    public Point X;
    public double x1;
    public double x2;

    /*
     * Les coordonnées du point Y
     */
    public Point Y;
    public double y1;
    public double y2;

    /*
     * Les coordonnées du point Z
     */
    public Point Z;
    public double z1;
    public double z2;

    /*
     * La couleur du triangle
     */
    private String couleur;

    private int angle;


    public Triangle(Point X, Point Y, Point Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.angle = 0;
    }

    public Triangle(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.X = new Point(x1, x2);
        this.Y = new Point(y2, y1);
        this.Z = new Point(z1, z2);
    }


    /*
     * Calcule et retourne les coordonnées du centre du Triangle
     * 
     * @return les coordonnées du centre du Triangle
     */
    public Point centre() {
        double centreX = (this.X.x() + this.Y.x() + this.Z.x()) / 3.0;
        double centreY = (this.X.y() + this.Y.y() + this.Z.y()) / 3.0;

        Point centre = new Point(centreX, centreY);
        return centre;
    }

    /**
     * Calcule et retourne la hauteur du triangle.
     *
     * @return La hauteur du triangle.
     */
    public double hauteur() {
        double numerateur = Math.abs((this.Y.x() - this.X.x()) * (this.X.y() - this.Z.y())
                - (this.X.x() - this.Z.x()) * (this.Y.y() - this.X.y()));

        double denominateur = Math.sqrt(Math.pow(this.Y.x() - this.X.x(), 2) + Math.pow(this.Y.y() - this.X.y(), 2));

        double hauteur = numerateur / denominateur;

        return (int)hauteur;
    }

    /**
     * Détermine le côté le plus petit du triangle pour déterminer la largeur.
     *
     * @return La largeur du triangle.
     */
    public double largeur() {
        double res = Math.sqrt(Math.pow(this.Y.x() - this.X.x(), 2) + Math.pow(this.Y.x() - this.X.y(), 2));

        if (res > Math.sqrt(Math.pow(this.Y.x() - this.Z.x(), 2) + Math.pow(this.Y.y() - this.Z.y(), 2))) {
            res = Math.sqrt(Math.pow(this.Y.x() - this.Z.x(), 2) + Math.pow(this.Y.y() - this.Z.y(), 2));
        }
        if (res > Math.sqrt(Math.pow(this.X.x() - this.Z.x(), 2) + Math.pow(this.X.y() - this.Z.y(), 2))) {
            res = Math.sqrt(Math.pow(this.X.x() - this.Z.x(), 2) + Math.pow(this.X.y() - this.Z.y(), 2));
        }
        return (int)res;
    }

    /**
     * Génère une description textuelle du triangle avec une indentation spécifiée.
     *
     * @param indentation L'indentation pour la description.
     * @return La description du triangle.
     */
    public String description(int indentation) {
        String spaces = "  ";
        for (int i = 0; i < indentation; i++) {
            spaces += "  ";
        }
        return spaces + "Triangle " + this.X.x() + "," + this.X.y() + "," + this.Y.y() + "," + this.Y.x() + ","
                + this.Z.x() + ","
                + this.Z.y();
    }

    /**
     * Redimensionne le triangle en multipliant les coordonnées par les facteurs de
     * redimensionnement.
     *
     * @param dx Facteur de redimensionnement pour la coordonnée x.
     * @param dy Facteur de redimensionnement pour la coordonnée y.
     */
    public IForme redimensionner(double dx, double dy) {
        // ON REDIMENSIONNE A PARTIR DU CENTRE DE LA FIGURE ET NON DES POINTS DE CETTE DERNIERE
        Point centre = centre();

        // REDIMENSIONNEMENT DU POINT X
        double distanceX1 = X.x() - centre.x();
        double nouvelleDistanceX1 = distanceX1 * dx;
        X.setX(Math.ceil(centre.x() + nouvelleDistanceX1)); // ON ARRONDI AU SUP

        double distanceX2 = X.y() - centre.y();
        double nouvelleDistanceX2 = distanceX2 * dx;
        X.setY(Math.ceil(centre.y() + nouvelleDistanceX2)); // ON ARRONDI AU SUP

        // REDIMENSIONNEMENT DU POINT Y
        double distanceY1 = Y.x() - centre.x();
        double nouvelleDistanceY1 = distanceY1 * dx;
        Y.setX(Math.ceil(centre.x() + nouvelleDistanceY1)); // ON ARRONDI AU SUP

        double distanceY2 = Y.y() - centre.y();
        double nouvelleDistanceY2 = distanceY2 * dy;
        Y.setY(Math.ceil(centre.y() + nouvelleDistanceY2)); // ON ARRONDI AU SUP

        // REDIMENSIONNEMENT DU POINT Z
        double distanceZ1 = Z.x() - centre.x();
        double nouvelleDistanceZ1 = distanceZ1 * dx;
        Z.setX(Math.ceil(centre.x() + nouvelleDistanceZ1)); // ON ARRONDI AU SUP

        double distanceZ2 = Z.y() - centre.y();
        double nouvelleDistanceZ2 = distanceZ2 * dy;
        Z.setY(Math.ceil(centre.y() + nouvelleDistanceZ2)); // ON ARRONDI AU SUP

        return this;

    }

    /**
     * Déplace le triangle en ajoutant les valeurs spécifiées aux coordonnées des
     * points.
     *
     * @param dx Valeur de déplacement pour la coordonnée x.
     * @param dy Valeur de déplacement pour la coordonnée y.
     */
    public IForme deplacer(double dx, double dy) {
        // Déplacement des coordonnées du point X
        this.X.setX(this.X.x() + dx);
        this.X.setY(this.X.y() + dy);

        // Déplacement des coordonnées du point Y
        this.Y.setX(this.Y.x() + dx);
        this.Y.setY(this.Y.y() + dy);

        // Déplacement des coordonnées du point Z
        this.Z.setX(this.Z.x() + dx);
        this.Z.setY(this.Z.y() + dy);

        return this;
    }

    /**
     * Duplique le triangle en créant une nouvelle instance avec les mêmes
     * coordonnées.
     *
     * @return Une nouvelle instance de Triangle avec les mêmes coordonnées.
     */
    public IForme dupliquer() {
        Point nouveauX = new Point(this.X.x(), this.X.y());
        Point nouveauY = new Point(this.Y.x(), this.Y.y());
        Point nouveauZ = new Point(this.Z.x(), this.Z.y());

        return new Triangle(nouveauX, nouveauY, nouveauZ);
    }

    /**
     * Génère le code SVG représentant le triangle sans remplissage.
     *
     * @return Le code SVG du triangle sans remplissage.
     */
    public String enSVG() {
        String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\"><polygon points=\"" + this.X.x() + " " + this.X.y() + " " + this.Y.x() + " " + this.Y.y() + " "
                + this.Z.x() + " " + this.Z.y() + "\"";
        svg += " fill=\"" + couleur + "\"";
        svg += " stroke=\"black\"";
        if (angle != 0) {
            Point centre = centre();
            svg += " transform=\"rotate(" + angle + " " + centre.x() + " " + centre.y() + ")\"";
        }
        svg += " />\n</svg>";
        return svg;
    }

    /**
     * Colorie la forme géométrique avec les couleurs spécifiées.
     *
     * @param couleurs Tableau variable de chaînes de caractères représentant les
     *                 couleurs.
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

    public String getCouleur() {
        return couleur;
    }

    public IForme tourner(int angle) {
        this.angle += angle;
        return this;
    }

    public double getAngle() {
        return angle;
    }

}