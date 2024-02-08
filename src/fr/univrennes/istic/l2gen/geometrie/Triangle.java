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
     * Coordonnées du premier point, x
     */
    private double x1;
    /**
     * Coordonnées du premier point, x.
     */
    private double x2;

    /**
     * Point représentant le premier point du triangle.
     */
    private Point x;

    /* coordonnées du deuxième point, y */
    private double y1;

    /* coordonnées du deuxième point, y */
    private double y2;

    /**
     * Point représentant le deuxième point du triangle.
     */
    private Point y;

    /* coordonnées du troisième point, z */
    private double z1;

    /* coordonnées du troisième point, z */
    private double z2;

    /**
     * Point représentant le troisième point du triangle.
     */
    private Point z;

    /**
     * Constructeur pour initialiser les coordonnées du triangle.
     *
     * @param x1 Coordonnée x du premier point.
     * @param x2 Coordonnée y du premier point.
     * @param y1 Coordonnée x du deuxième point.
     * @param y2 Coordonnée y du deuxième point.
     * @param z1 Coordonnée x du troisième point.
     * @param z2 Coordonnée y du troisième point.
     */
    public Triangle(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    /**
     * Constructeur alternatif pour initialiser les points du triangle avec des
     * objets Point.
     *
     * @param x Premier point du triangle.
     * @param y Deuxième point du triangle.
     * @param z Troisième point du triangle.
     */
    public Triangle(Point x, Point y, Point z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calcule et retourne le centre du triangle.
     *
     * @return Le point représentant le centre du triangle.
     */
    public Point centre() {
        double centreX = (this.x.x() + this.y.x() + this.z.x()) / 3;

        double centreY = (this.x.y() + this.y.y() + this.z.y()) / 3;

        Point centre = new Point(centreX, centreY);

        return centre;
    }

    /**
     * Calcule et retourne la hauteur du triangle.
     *
     * @return La hauteur du triangle.
     */
    public double hauteur() {
        double numerateur = Math.abs((y1 - x1) * (x1 - z1) - (x1 - z1) * (y1 - x1));

        double denominateur = Math.sqrt(Math.pow(y1 - x1, 2) + Math.pow(y1 - x1, 2));

        double hauteur = numerateur / denominateur;

        return hauteur;
    }

    /**
     * Détermine le côté le plus petit du triangle pour déterminer la largeur.
     *
     * @return La largeur du triangle.
     */
    public double largeur() {
        double res = Math.sqrt(Math.pow(y1 - x1, 2) + Math.pow(y2 - x2, 2));

        if (res > Math.sqrt(Math.pow(y1 - z1, 2) + Math.pow(y2 - z2, 2))) {
            res = Math.sqrt(Math.pow(y1 - z1, 2) + Math.pow(y2 - z2, 2));
        }

        else if (res > Math.sqrt(Math.pow(x1 - z1, 2) + Math.pow(x2 - z2, 2))) {
            res = Math.sqrt(Math.pow(x1 - z1, 2) + Math.pow(x2 - z2, 2));
        }

        return res;
    }

    /**
     * Génère une description textuelle du triangle avec une indentation spécifiée.
     *
     * @param indentation L'indentation pour la description.
     * @return La description du triangle.
     */
    public String description(int indentation) {
        String ali = "";
        for (int i = 0; i < indentation; i++) {
            ali += " ";
        }
        return ali + "Triangle " + x1 + "," + x2 + " " + y1 + "," + y2 + " " + z1 + "," + z2;
    }

    /**
     * Redimensionne le triangle en multipliant les coordonnées par les facteurs de
     * redimensionnement.
     *
     * @param dx Facteur de redimensionnement pour la coordonnée x.
     * @param dy Facteur de redimensionnement pour la coordonnée y.
     */
    public void redimensionner(double dx, double dy) {
        this.x1 *= dx;
        this.x2 *= dy;
        this.y1 *= dx;
        this.y2 *= dy;
        this.z1 *= dx;
        this.z2 *= dy;
    }

    /**
     * Déplace le triangle en ajoutant les valeurs spécifiées aux coordonnées des
     * points.
     *
     * @param dx Valeur de déplacement pour la coordonnée x.
     * @param dy Valeur de déplacement pour la coordonnée y.
     */
    public void deplacer(double dx, double dy) {
        // Déplacement des coordonnées des points du triangle
        this.x1 += dx;
        this.x2 += dy;
        this.y1 += dx;
        this.y2 += dy;
        this.z1 += dx;
        this.z2 += dy;
    }

    /**
     * Duplique le triangle en créant une nouvelle instance avec les mêmes
     * coordonnées.
     *
     * @return Une nouvelle instance de Triangle avec les mêmes coordonnées.
     */
    public IForme dupliquer() {
        return new Triangle(x1, x2, y1, y2, z1, z2);
    }

    /**
     * Génère le code SVG représentant le triangle sans remplissage.
     *
     * @return Le code SVG du triangle sans remplissage.
     */
    public String enSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">\n");
        svg.append("<polygon points=\"" + x1 + "," + x2 + " " + y1 + "," + y2 + " " + z1 + "," + z2 + "\" />\n");
        svg.append("</svg>");
        return svg.toString();
    }

}
