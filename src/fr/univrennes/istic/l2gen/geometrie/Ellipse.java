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
    private String couleur;
    private int angle;


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
        this.angle = 0; // Initialise l'angle à zéro par défaut
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
        String espaces = "  ";
        Point point = centre();
        for (int i = 0; i < indentation; i++) {
            espaces += "  ";
        }
        return espaces + "Ellipse  Centre=" + (int) point.x() + "," + (int) point.y() + "  rx=" + largeur() + "  ry="
                + hauteur() + " Angle=" + this.angle;
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
    public IForme redimensionner(double dx, double dy) {
        this.demiGrandAxe = demiGrandAxe * dx;
        this.demiPetitAxe = demiPetitAxe * dy;
        return this;
    }

    /**
     * Déplace l'ellipse des montants spécifiés dans les directions x et y.
     *
     * @param dx La quantité à déplacer dans la direction x.
     * @param dy La quantité à déplacer dans la direction y.
     */
    public IForme deplacer(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    /**
     * Crée et retourne une copie de l'ellipse.
     *
     * @return Un nouvel objet Ellipse avec les mêmes paramètres.
     */
    public IForme dupliquer() {
        Ellipse copieEllipse = new Ellipse(this.x, this.y, this.demiGrandAxe, this.demiPetitAxe);
        copieEllipse.tourner(this.angle); // Assure que la copie a le même angle que l'original
        return copieEllipse;
    }
    

    /**
     * Génère une représentation SVG de l'ellipse.
     *
     * @return La chaîne SVG représentant l'ellipse.
     */
    public String enSVG() {
        String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\"><ellipse";
    
        svg += " cx=\"" + x + "\"";
        svg += " cy=\"" + y + "\"";
        svg += " rx=\"" + demiGrandAxe + "\"";
        svg += " ry=\"" + demiPetitAxe + "\"";
        svg += " fill=\""+couleur+"\"";
        svg += " stroke=\"black\"";
        
        if (angle != 0) {
            Point centre = centre();
            svg += " transform=\"rotate(" + angle + " " + centre.x() + " " + centre.y() + ")\"";
        }
        
        svg += " />\n</svg>";
    
        return svg;
    }
    
    /**
     * Change la couleur du ellipse en utilisant la première couleur spécifiée dans le tableau.
     *
     * @param couleurs Tableau de couleurs à appliquer.
     * @return L'objet Ellipse avec la nouvelle couleur.
     */
    @Override
    public IForme colorier(String... couleurs) {
        if (couleurs.length > 0) {
            String couleur = couleurs[0];
            this.couleur = couleur;
        }
        return this;
    }
    
    /**
     * Renvoie la couleur actuelle du ellipse.
     *
     * @return La couleur de l'ellipse.
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Fait tourner le ellipse en spécifiant un angle.
     *
     * @param angle L'angle de rotation à appliquer au ellipse.
     * @return L'objet Ellipse tourné.
     */
    public IForme tourner(int angle) {
        this.angle += angle; // Met à jour l'angle de l'ellipse
        return this;
    }
    
    /**
     * Renvoie l'angle actuel de l'ellipse.
     *
     * @return L'angle de l'ellipse.
     */
    public int getAngle() {
        return this.angle;
    }

    /**
     * Aligne le cercle en fonction de l'alignement spécifié et de la cible donnée.
     *
     * @param alignment Alignement souhaité (HAUT, BAS, GAUCHE, DROITE).
     * @param cible     Coordonnée cible pour l'alignement.
     * @return Instance de la forme géométrique alignée.
     */
    @Override
    public IForme aligner(Alignement alignment, double cible) {
        switch (alignment) {
            case HAUT:
                this.y = cible + this.demiPetitAxe;
                break;
            case BAS:
                this.y = cible - this.demiPetitAxe;
                break;
            case GAUCHE:
                this.x = cible + this.demiGrandAxe;
                break;
            case DROITE:
                this.x = cible - this.demiGrandAxe;
                break;
        }
        return this;
    }
    
}
