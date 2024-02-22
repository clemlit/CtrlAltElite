package fr.univrennes.istic.l2gen.geometrie;

/**
 * Cette classe représente un rectangle dans un plan cartésien.
 * Elle implémente l'interface IForme.
 */
public class Rectangle implements IForme {
    private Point p;
    private double largeur;
    private double hauteur;
    private String couleur;
    private int angle;

    /**
     * Constructeur de la classe Rectangle.
     * Crée un rectangle avec les coordonnées du coin supérieur gauche, la largeur
     * et la hauteur spécifiées.
     * 
     * @param x       La coordonnée x du coin supérieur gauche du rectangle.
     * @param y       La coordonnée y du coin supérieur gauche du rectangle.
     * @param largeur La largeur du rectangle.
     * @param hauteur La hauteur du rectangle.
     * @requires Les valeurs de (x,y,largeur,hauteur) doivent être strictement
     *           positives.
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
     * Crée un rectangle avec le point du coin supérieur gauche, la largeur et la
     * hauteur spécifiées.
     * 
     * @param p       Le point du coin supérieur gauche du rectangle.
     * @param largeur La largeur du rectangle.
     * @param hauteur La hauteur du rectangle.
     * @requires Le point p ne doit pas être nul.
     * @requires Les valeurs largeur et hauteur doivent être strictement positives.
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
     * 
     * @return Le point représentant le centre du rectangle.
     */
    public Point centre() {
        return p;
    }

    /**
     * Retourne une description textuelle du rectangle avec un certain niveau
     * d'indentation.
     * 
     * @param indentation Le niveau d'indentation de la description.
     *                    <<<<<<< HEAD
     * @require indentation >= 0.
     *          =======
     * @requires
     *           indentation<0
     *           >>>>>>> 016084087eefdb40dd20c942ab0a918dd9e39e0d
     * @return La description textuelle du rectangle.
     */
    public String description(int indentation) {
        if (indentation < 0)
            throw new IllegalArgumentException("indentation<0");
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
     * 
     * @return La hauteur du rectangle.
     * @requires La valeur rendu doit être strictement positives.
     */
    public double hauteur() {
        if (hauteur <= 0)
            throw new IllegalArgumentException("hauteur<=0");
        return hauteur;
    }

    /**
     * Retourne la largeur du rectangle.
     * 
     * @return La largeur du rectangle.
     * @ensures La valeur rendu doit être strictement positives.
     */
    public double largeur() {
        if (largeur <= 0)
            throw new IllegalArgumentException("largeur<=0");
        return largeur;
    }

    /**
     * Déplace le rectangle selon les valeurs spécifiées de déplacement en x et en
     * y.
     * 
     * @param dx La valeur de déplacement en x.
     * @param dy La valeur de déplacement en y.
     * @ensures Le rectangle ne doit pas dépasser les limites d'affichage.
     */
    public IForme deplacer(double dx, double dy) {
        p = p.plus(dx, dy);
        return this;
    }

    /**
     * Duplique le rectangle.
     * <<<<<<< HEAD
     * 
     * =======
     * 
     * @requires la forme de base est un rectangle
     *           >>>>>>> 016084087eefdb40dd20c942ab0a918dd9e39e0d
     * @return Une nouvelle instance de Rectangle identique à celle actuelle.
     */
    public IForme dupliquer() {
        if (!this.getClass().getSimpleName().equals("Rectangle"))
            throw new IllegalArgumentException("Mauvaise classe");
        Rectangle rectangleDuplique = new Rectangle(p, largeur, hauteur);
        rectangleDuplique.couleur = this.couleur;
        rectangleDuplique.angle = this.angle;
        return rectangleDuplique;
    }

    /**
     * Redimensionne le rectangle selon les proportions spécifiées.
     * 
     * @param px Le facteur de redimensionnement en largeur.
     * @param py Le facteur de redimensionnement en hauteur.
     * @requires Les valeurs de px et py doivent être strictement positives.
     * @ensures Le rectangle ne doit pas dépasser les limites d'affichage.
     */
    public IForme redimensionner(double px, double py) {
        if (!(px > 0 && py > 0))
            throw new IllegalArgumentException("Valeurs nulles ou négatives");
        // Calcul des nouvelles dimensions du rectangle
        double nouvelleLargeur = largeur * px;
        double nouvelleHauteur = hauteur * py;

        // Calcul des nouvelles coordonnées du coin supérieur gauche
        double nouveauX = p.x() - (nouvelleLargeur - largeur) / 2;
        double nouveauY = p.y() - (nouvelleHauteur - hauteur) / 2;

        // Mise à jour du point représentant le coin supérieur gauche
        p = new Point(nouveauX, nouveauY);

        // Mise à jour de la largeur et de la hauteur
        largeur = nouvelleLargeur;
        hauteur = nouvelleHauteur;

        return this;
    }

    /**
     * Retourne une représentation SVG du rectangle.
     * 
     * @return La représentation SVG du rectangle.
     */
    public String enSVG() {
        String svg = "<rect";

        // Calcul des coordonnées du coin supérieur gauche du rectangle
        double coin_gauche_rectangleX = p.x();
        double coin_gauche_rectangleY = p.y();

        // Ajout des attributs du rectangle
        String xAttribute = " x=\"" + coin_gauche_rectangleX + "\"";
        String yAttribute = " y=\"" + coin_gauche_rectangleY + "\"";
        String widthAttribute = " width=\"" + largeur() + "\"";
        String heightAttribute = " height=\"" + hauteur() + "\"";
        String fillAttribute = " fill=\"" + couleur + "\"";
        String strokeAttribute = " stroke=\"black\"";

        String transformAttribute = (angle != 0)
                ? " transform=\"rotate(" + angle + " " + centre().x() + " " + centre().y() + ")\""
                : "";

        svg += xAttribute + yAttribute + widthAttribute + heightAttribute + fillAttribute + strokeAttribute
                + transformAttribute + " />\n";

        return svg;
    }

    /**
     * Change la couleur du rectangle en utilisant la première couleur spécifiée
     * dans le tableau.
     *
     * @param couleurs Tableau de couleurs à appliquer.
     * @require couleurs n'est pas vide.
     * @require les couleurs du tableau couleurs sont des couleurs existantes dans
     *          la bibliothèque SVG.
     * @return L'objet Rectangle avec la nouvelle couleur.
     */
    @Override
    public IForme colorier(String... couleurs) {
        if (couleurs.length <= 0)
            throw new IllegalArgumentException("Tableau vide");
        // Ici, vous pouvez prendre la première couleur du tableau couleurs
        String couleur = couleurs[0];
        // Implémentation pour colorier un cercle avec la couleur spécifiée
        this.couleur = couleur;

        return this;
    }

    /**
     * Retourne la couleur du rectangle.
     *
     * @return La couleur du rectangle.
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Tourne le rectangle d'un certain angle.
     * 
     * @param angle L'angle de rotation.
     * @return La même instance de rectangle après rotation.
     */
    public IForme tourner(int angle) {
        this.angle += angle;
        return this;
    }
}
