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

    private Point A;
    private Point B;
    private Point C;

    private double x1;
    private double x2;
    private double x3;
    private double y1;
    private double y2;
    private double y3;
    private String couleur;

    public Triangle(Point A, Point B, Point C){
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public Triangle(double val1, double val2, double val3, double val4, double val5, double val6){
        x1 = (int)val1;
        x2 = (int)val3;
        x3 = (int)val5;
        y1 = (int)val2;
        y2 = (int)val4;
        y3 = (int)val6;
    }

    public Point centre() {
        Point centre = null;

        if (A != null && B != null && C != null) {
            // Utiliser les points A, B et C pour calculer le centre si disponibles
            double centreX = (A.x() + B.x() + C.x()) / 3;
            double centreY = (A.y() + B.y() + C.y()) / 3;
            centre = new Point(centreX, centreY);
        } else {
            // Utiliser les coordonnées x et y si les points ne sont pas disponibles
            double centreX = (x1 + x2 + x3) / 3;
            double centreY = (y1 + y2 + y3) / 3;
            centre = new Point(centreX, centreY);
        }

        return centre;
    }


    /**
     * Calcule et retourne la hauteur du triangle.
     *
     * @return La hauteur du triangle.
     */
    public double hauteur() {
        double numerateur = Math.abs((x2 - x1) * (y1 - y3) - (x1 - x3) * (y2 - y1));

        double denominateur = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        double hauteur = numerateur / denominateur;

        return hauteur;
    }

    /**
     * Détermine le côté le plus petit du triangle pour déterminer la largeur.
     *
     * @return La largeur du triangle.
     */
    public double largeur() {
        double distance1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double distance2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double distance3 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

        double largeur = Math.min(distance1, Math.min(distance2, distance3));

        return largeur;
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
        return spaces + "Triangle " + (int) x1 + "," + (int) y1 + "," + (int) x2 + "," + (int) y2 + "," + (int) x3 + ","+ (int) y3;
    }

    /**
     * Redimensionne le triangle en multipliant les coordonnées par les facteurs de
     * redimensionnement.
     *
     * @param dx Facteur de redimensionnement pour la coordonnée x.
     * @param dy Facteur de redimensionnement pour la coordonnée y.
     */
    public IForme redimensionner(double dx, double dy) {
        Point centre = centre();

        // REDIMENSIONNEMENT DU POINT X1 ET Y2
        double distanceX1 = x1 - centre.x();
        double newDistanceX1 = distanceX1 * dx;
        x1 = Math.ceil(centre.x()+newDistanceX1);

        double distanceY1 = y1 -centre.y();
        double newDistanceY1 = distanceY1 * dy;
        y1 = Math.ceil(centre.y()+newDistanceY1);


        //REDIMENSIONNEMENT DU POINT X2 ET Y2
        double distanceX2 = x2 - centre.x();
        double newDistanceX2 = distanceX2 * dx;
        x2 = Math.ceil(centre.x() + newDistanceX2);
        

        double distanceY2 = y2 - centre.y();
        double newDistanceY2 = distanceY2 * dy;
        y2 = Math.ceil(centre.y() + newDistanceY2);

        //REDIMENSIONNEMENT DE X3 ET Y3
        double distanceX3 = x3 - centre.x();
        double newDistanceX3 = distanceX3 * dx;
        x3 = Math.ceil(centre.x() + newDistanceX3);

        double distanceY3 = y3 - centre.y();
        double newDistanceY3 = distanceY3 * dy;
        y3 = Math.ceil(centre.y() + newDistanceY3);
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

        x3 += dx;
        y3 += dy;
        return this;
    }

    /**
     * Duplique le triangle en créant une nouvelle instance avec les mêmes
     * coordonnées.
     *
     * @return Une nouvelle instance de Triangle avec les mêmes coordonnées.
     */
    public IForme dupliquer() {
        return new Triangle(x1, y1, x2, y2, x3, y3);
    }

    /**
     * Génère le code SVG représentant le triangle sans remplissage.
     *
     * @return Le code SVG du triangle sans remplissage.
     */
    public String enSVG() {
        String svg = "<polygon points=\"" + (int) x1 + " " + (int) y1 + " " + (int) x2 + " " + (int) y2 + " " + (int) x3
                + " " + (int) y3 + "\"";
        svg += " fill=\""+couleur +"\"";
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