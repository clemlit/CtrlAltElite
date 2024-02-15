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

    private Point x;
    private Point y;
    private Point z;

    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double z1;
    private double z2;
    private String couleur;

    public Triangle(Point x, Point y, Point z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Triangle(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public Point centre() {
        double centreX = (this.x1 + this.y1 + this.z1) / 3.0;
        double centreY = (this.x2 + this.y2 + this.z2) / 3.0;

        Point centre = new Point(centreX, centreY);
        return centre;
    }

    /**
     * Calcule et retourne la hauteur du triangle.
     *
     * @return La hauteur du triangle.
     */
    public double hauteur() {
        double numerateur = Math.abs((y1 - x1) * (x2 - z2) - (x1 - z1) * (y2 - x2));

        double denominateur = Math.sqrt(Math.pow(y1 - x1, 2) + Math.pow(y2 - x2, 2));

        double hauteur = numerateur / denominateur;

        return hauteur;
    }

    /**
     * Détermine le côté le plus petit du triangle pour déterminer la largeur.
     *
     * @return La largeur du triangle.
     */
    public double largeur() {
        double res = Math.sqrt(Math.pow(y1 - x1, 2) + Math.pow(y1 - x2, 2));

        if (res > Math.sqrt(Math.pow(y1 - z1, 2) + Math.pow(y2 - z2, 2))) {
            res = Math.sqrt(Math.pow(y1 - z1, 2) + Math.pow(y2 - z2, 2));
        }
        if (res > Math.sqrt(Math.pow(x1 - z1, 2) + Math.pow(x2 - z2, 2))) {
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
        String spaces = "";
        for (int i = 0; i < indentation; i++) {
            spaces += " ";
        }
        return spaces + "Triangle " + x1 + "," + y1 + "," + x2 + "," + y2 + "," + z1 + ","
                + z2;
    }

    /**
     * Redimensionne le triangle en multipliant les coordonnées par les facteurs de
     * redimensionnement.
     *
     * @param dx Facteur de redimensionnement pour la coordonnée x.
     * @param dy Facteur de redimensionnement pour la coordonnée y.
     */
    public IForme redimensionner(double dx, double dy) {
        this.x1 *= dx;
        this.x2 *= dy;
        this.y1 *= dx;
        this.y2 *= dy;
        this.z1 *= dx;
        this.z2 *= dy;
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
        // Déplacement des coordonnées des points du triangle
        x1 += dx;
        y1 += dy;

        x2 += dx;
        y2 += dy;

        z1 += dx;
        z2 += dy;

        return this;
    }

    /**
     * Duplique le triangle en créant une nouvelle instance avec les mêmes
     * coordonnées.
     *
     * @return Une nouvelle instance de Triangle avec les mêmes coordonnées.
     */
    public IForme dupliquer() {
        return new Triangle(x1, y1, x2, y2, z1, z2);
    }

    /**
     * Génère le code SVG représentant le triangle sans remplissage.
     *
     * @return Le code SVG du triangle sans remplissage.
     */
    public String enSVG() {
        String svg = "<polygon points=\"" + x1 + " " + y1 + " " + x2 + " " + y2 + " " + z1
                + " " + z2 + "\"";
        svg += " fill=\"" + couleur + "\"";
        svg += " stroke=\"black\" />\n";
        return svg;
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

}