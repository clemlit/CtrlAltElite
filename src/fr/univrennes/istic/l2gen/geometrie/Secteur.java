/**
 * La classe Secteur représente un secteur circulaire défini par un centre, un rayon, un angle de début
 * et un angle de fin. Elle implémente l'interface IForme pour fournir des fonctionnalités liées à la manipulation
 * de secteurs dans un espace bidimensionnel.
 *
 */
package fr.univrennes.istic.l2gen.geometrie;

public class Secteur implements IForme {

    // ATTRIBUTS

    /**
     * Centre du secteur.
     */
    private Point centre;

    /**
     * Rayon du secteur.
     */
    private double rayon;

    /**
     * Angle de début du secteur en degrés.
     */
    private double angleDebut;

    /**
     * Angle de fin du secteur en degrés.
     */
    private double angleFin;

    private String couleur;

    private int angle;

    // CONSTRUCTEUR

    /**
     * Retourne l'angle du secteur.
     *
     * @return Angle du secteur.
     */
    public int getAngle() {
        return angle;
    }

    /**
     * Définit l'angle du secteur.
     *
     * @param angle Nouvel angle du secteur.
     */
    public void setAngle(int angle) {
        this.angle = angle;
    }

    /**
     * Constructeur de la classe Secteur.
     *
     * @param centre     Centre du secteur.
     * @param rayon      Rayon du secteur.
     * @param angleDebut Angle de début du secteur en degrés.
     * @param angleFin   Angle de fin du secteur en degrés.
     * @requires Le point ne doit pas être nul.
     * @requires Les valeurs de (rayon,angleDebut,angleFin) doivent être strictement
     *           positives.
     */
    public Secteur(Point centre, double rayon, double angleDebut, double angleFin) {
        this.centre = centre;
        this.rayon = rayon;
        this.angleDebut = angleDebut;
        this.angleFin = angleFin;
        this.angle = 0;
        this.couleur = "White";
    }

    /**
     * Constructeur alternatif de la classe Secteur.
     *
     * @param p1         Coordonnée x du centre du secteur.
     * @param p2         Coordonnée y du centre du secteur.
     * @param rayon      Rayon du secteur.
     * @param angleDebut Angle de début du secteur en degrés.
     * @param angleFin   Angle de fin du secteur en degrés.
     * @require Les valeurs de (p1,p2,rayon,angleDebut,angleFin) doivent être
     *          strictement positives.
     */
    public Secteur(double p1, double p2, double rayon, double angleDebut, double angleFin) {
        this.centre = new Point(p1, p2);
        this.rayon = rayon;
        this.angleDebut = angleDebut;
        this.angleFin = angleFin;
        this.angle = 0;
        this.couleur = "White";

    }

    // ACCESSEURS

    /**
     * Retourne le centre du secteur.
     *
     * @return Centre du secteur.
     */
    public Point getCentre() {
        return centre;
    }

    /**
     * Retourne le rayon du secteur.
     *
     * @return Rayon du secteur.
     */
    public double getRayon() {
        return rayon;
    }

    /**
     * Retourne l'angle de début du secteur en degrés.
     *
     * @return Angle de début du secteur.
     */
    public double getAngleDebut() {
        return angleDebut;
    }

    /**
     * Retourne l'angle de fin du secteur en degrés.
     *
     * @return Angle de fin du secteur.
     */
    public double getAngleFin() {
        return angleFin;
    }

    // MUTATEURS

    /**
     * Définit le centre du secteur.
     *
     * @param centre Nouveau centre du secteur.
     */
    public void setCentre(Point centre) {
        this.centre = centre;
    }

    /**
     * Définit le rayon du secteur.
     *
     * @param rayon Nouveau rayon du secteur.
     */
    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    /**
     * Définit l'angle de début du secteur en degrés.
     *
     * @param angleDebut Nouvel angle de début du secteur.
     */
    public void setAngleDebut(double angleDebut) {
        this.angleDebut = angleDebut;
    }

    /**
     * Définit l'angle de fin du secteur en degrés.
     *
     * @param angleFin Nouvel angle de fin du secteur.
     */
    public void setAngleFin(double angleFin) {
        this.angleFin = angleFin;
    }

    // METHODES

    /**
     * Génère une description textuelle du secteur avec l'indentation spécifiée.
     * 
     * @param indentation Niveau d'indentation pour la description.
     * @require indentation >= 0.
     * @return Chaîne de caractères décrivant le secteur.
     */
    @Override
    public String description(int indentation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append(" ");
        }
        sb.append("Secteur centre=").append(String.format("%.0f", centre.x())).append(",")
                .append(String.format("%.0f", centre.y()));
        sb.append(" Angle=").append(angleDebut);
        double arc = angleFin - angleDebut;
        if (arc < 0) {
            arc += 360; // Correction si l'angle de fin est inférieur à l'angle de début
        }
        sb.append(" Arc=").append(arc);
        return sb.toString();
    }

    /**
     * Génère une représentation SVG du secteur.
     *
     * @return Chaîne de caractères représentant le secteur en format SVG.
     */
    @Override
    public String enSVG() {
        // Calcul des coordonnées des points de début et de fin de l'arc
        int xFin = (int) Math.round(centre.x() + rayon * Math.cos(Math.toRadians(-angleFin + 90)));
        int yFin = (int) Math.round(centre.y() + rayon * Math.sin(Math.toRadians(angleFin - 90)));

        // Correction des angles pour être compris entre 0 et 360 degrés
        double angleStart = Math.min(angleDebut, angleFin);
        double angleEnd = Math.max(angleDebut, angleFin);
        double angleExtent = angleEnd - angleStart;
        if (angleExtent < 0) {
            angleExtent += 360; // Correction si l'angle de fin est inférieur à l'angle de début
        }

        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\"><path d=\"");
        svg.append("M").append(this.centre.x()).append(" ").append(this.centre.y()).append(" "); // Déplacement initial
                                                                                                 // au centre du cercle
        svg.append("l0,-").append(this.getRayon()).append(" "); // Ligne verticale vers le bord du cercle
        svg.append("A").append(this.getRayon()).append(" ").append(this.getRayon()).append(" "); // Arc de cercle
        svg.append("0 "); // Rotation de l'ellipse par rapport à l'axe X
        svg.append(angleExtent > 180 ? "1 " : "0 "); // Grand angle ou petit angle de l'ellipse
        svg.append("1 "); // Arc de cercle de 60 degrés
        svg.append(xFin).append(",").append(yFin).append(" "); // Point final de l'arc
        svg.append("Z\" "); // Fermeture du chemin
        svg.append("fill=\"" + couleur + "\" stroke=\"black\"/></svg>"); // Couleur de remplissage et de contour
        return svg.toString();
    }

    /**
     * Duplique le secteur en créant une nouvelle instance identique.
     *
     * @return Nouvelle instance du secteur.
     */
    @Override
    public IForme dupliquer() {
        return new Secteur(centre, rayon, angleDebut, angleFin);
    }

    /**
     * Redimensionne le secteur en fonction des facteurs spécifiés sur les axes x et
     * y.
     *
     * @param facteurX Facteur de redimensionnement pour l'axe des x.
     * @param facteurY Facteur de redimensionnement pour l'axe des y.
     * @requires Les valeurs de x et y doivent être strictement positives.
     * @ensures Le secteur ne doit pas dépasser les limites d'affichage.
     */
    @Override
    public IForme redimensionner(double facteurX, double facteurY) {
        rayon *= Math.max(facteurX, facteurY);
        return this;
    }

    /**
     * Déplace le secteur selon les déplacements spécifiés sur les axes x et y.
     *
     * @param dx Déplacement sur l'axe des x.
     * @param dy Déplacement sur l'axe des y.
     * @ensures Le secteur ne doit pas dépasser les limites d'affichage.
     */
    @Override
    public IForme deplacer(double dx, double dy) {
        centre = centre.plus(dx, dy);
        return this;
    }

    /**
     * Retourne le centre du secteur.
     *
     * @return Centre du secteur.
     */
    @Override
    public Point centre() {
        return centre;
    }

    /**
     * Retourne la hauteur du secteur (le double du rayon).
     *
     * @return Hauteur du secteur.
     * @ensures La hauteur doit être strictement positive.
     */
    @Override
    public double hauteur() {
        return rayon;
    }

    /**
     * Retourne la largeur du secteur (le double du rayon).
     *
     * @return Largeur du secteur.
     * @ensures La largeur doit être strictement positive.
     */
    @Override
    public double largeur() {
        return 2 * rayon;
    }

    /**
     * Colorie le secteur avec la couleur spécifiée.
     *
     * @param couleurs Tableau de couleurs (seule la première couleur sera
     *                 utilisée).
     * @require couleurs n'est pas vide.
     * @require les couleurs du tableau couleurs sont des couleurs existantes dans
     *          la bibliothèque SVG.
     * @return Instance du secteur colorié.
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

    /**
     * Retourne la couleur du secteur.
     *
     * @return Couleur du secteur.
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Tourne le secteur selon l'angle spécifié.
     *
     * @param angle Angle de rotation.
     * @return Instance du secteur après rotation.
     */
    public IForme tourner(int angle) {
        angleDebut = (angleDebut + angle) % 360;
        angleFin = (angleFin + angle) % 360;
        return this;
    }

}