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
        this.couleur = "white";
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
        this.couleur = "white";
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
        return indent + "Rectangle " + "Centre=" + (int) centre().x() + "," + (int) centre().y() + " L=" + largeur
                + " H=" + hauteur + " Couleur=" + couleur;
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
        // Calcul du nouveau centre après redimensionnement
        double nouveauCentreX = p.x() + largeur / 2;
        double nouveauCentreY = p.y() + hauteur / 2;

        // Redimensionnement
        largeur *= px;
        hauteur *= py;

        // Ajustement des coordonnées du coin supérieur gauche
        p = new Point(nouveauCentreX - largeur / 2, nouveauCentreY - hauteur / 2);
    }

    /**
     * Retourne une représentation SVG du rectangle.
     * @return La représentation SVG du rectangle.
     */
    public String enSVG() {
        // Construction de la chaîne SVG
        String svg = "<rect";

        // Ajout des attributs du rectangle
        String xAttribute = " x=\"" + centre().x() + "\"";
        String yAttribute = " y=\"" + centre().y() + "\"";
        String widthAttribute = " width=\"" + largeur() + "\"";
        String heightAttribute = " height=\"" + hauteur() + "\"";
        String fillAttribute = " fill=\"" + couleur + "\""; 
        String strokeAttribute = " stroke=\"black\"";

        // Construction finale de la chaîne SVG
        svg += xAttribute + yAttribute + widthAttribute + heightAttribute + fillAttribute + strokeAttribute + " />\n";

        return svg;
    }

    public void coloriser(String... couleurs) {
        if (couleurs != null && couleurs.length > 0) {
            this.couleur = couleurs[0];
        }       

    }

}
