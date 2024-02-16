package fr.univrennes.istic.l2gen.geometrie;

/**
 * Cette classe représente un rectangle dans un plan cartésien.
 * Elle implémente l'interface IForme.
 */
public class Rectangle implements IForme{
    private Point p;
    private double largeur;
    private double hauteur;
    private String couleur; 
    private int angle;

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
        this.couleur = "White";
        this.angle = 0;
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
        this.couleur = "White";
        this.angle = 0;
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
        String indent = "  ";
        for (int i = 0; i < indentation; i++) {
            indent += "  "; // Deux espaces pour chaque niveau d'indentation
        }

        // Construction de la description
        String description = indent + "Rectangle " + "Centre=" + (int) centre().x() + "," + (int) centre().y() +
                " L=" + largeur + " H=" + hauteur + " Angle=" + angle + " Couleur=" + couleur;

        return description;
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
    public IForme deplacer(double dx, double dy) {
        p = p.plus(dx, dy);
        return this;
    }

    /**
     * Duplique le rectangle.
     * @return Une nouvelle instance de Rectangle identique à celle actuelle.
     */
    public IForme dupliquer() {
        Rectangle rectangleDuplique = new Rectangle(p, largeur, hauteur);
        rectangleDuplique.couleur = this.couleur;
        rectangleDuplique.angle = this.angle;
        return rectangleDuplique;
    }

    /**
     * Redimensionne le rectangle selon les proportions spécifiées.
     * @param px Le facteur de redimensionnement en largeur.
     * @param py Le facteur de redimensionnement en hauteur.
     */
    public IForme redimensionner(double px, double py) {
        largeur *= px;
        hauteur *= py;
        return this;
    }

    /**
     * Retourne une représentation SVG du rectangle.
     * @return La représentation SVG du rectangle.
     */
    public String enSVG() {
        // Construction de la chaîne SVG
        String svg = "<rect";
        double coin_gauche_rectangleX = centre().x() - largeur()/2;
        double coin_gauche_rectangleY = centre().y() - hauteur() / 2;


        // Calcul des coordonnées du coin supérieur gauche du rectangle SVG en fonction
        // du centre

        // Ajout des attributs du rectangle
        String xAttribute = " x=\"" + coin_gauche_rectangleX + "\"";
        String yAttribute = " y=\"" + coin_gauche_rectangleY + "\"";
        String widthAttribute = " width=\"" + largeur() + "\"";
        String heightAttribute = " height=\"" + hauteur() + "\"";
        String fillAttribute = " fill=\"" + couleur + "\"";
        String strokeAttribute = " stroke=\"black\"";

        String transformAttribute = " transform=\"rotate(" + angle + " " + centre().x() + " " + centre().y() + ")\"";

        // Construction finale de la chaîne SVG
        svg += xAttribute + yAttribute + widthAttribute + heightAttribute + fillAttribute + strokeAttribute + transformAttribute+" />\n";

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

    public void tourner(int angle) {
        this.angle += angle;
    }
}
