/**
 * La classe Secteur représente un secteur circulaire défini par un centre, un rayon, un angle de début
 * et un angle de fin. Elle implémente l'interface IForme pour fournir des fonctionnalités liées à la manipulation
 * de secteurs dans un espace bidimensionnel.
 *
 * @author Votre Nom
 * @version 1.0
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
     * Constructeur de la classe Secteur.
     *
     * @param centre     Centre du secteur.
     * @param rayon      Rayon du secteur.
     * @param angleDebut Angle de début du secteur en degrés.
     * @param angleFin   Angle de fin du secteur en degrés.
     */
    public Secteur(Point centre, double rayon, double angleDebut, double angleFin) {
        this.centre = centre;
        this.rayon = rayon;
        this.angleDebut = angleDebut;
        this.angleFin = angleFin;
        this.angle = 0;
    }

    /**
     * Constructeur alternatif de la classe Secteur.
     *
     * @param p1         Coordonnée x du centre du secteur.
     * @param p2         Coordonnée y du centre du secteur.
     * @param rayon      Rayon du secteur.
     * @param angleDebut Angle de début du secteur en degrés.
     * @param angleFin   Angle de fin du secteur en degrés.
     */
    public Secteur(double p1, double p2, double rayon, double angleDebut, double angleFin) {
        this.centre = new Point(p1, p2);
        this.rayon = rayon;
        this.angleDebut = angleDebut;
        this.angleFin = angleFin;
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
     * @return Chaîne de caractères décrivant le secteur.
     */
    @Override
    public String description(int indentation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append(" ");
        }
        sb.append("Secteur centre=").append(String.format("%.0f", centre.x())).append(",").append(String.format("%.0f", centre.y()));
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
        double xDebut = centre.x() + rayon * Math.cos(Math.toRadians(angleDebut));
        double yDebut = centre.y() - rayon * Math.sin(Math.toRadians(angleDebut));
        double xFin = centre.x() + rayon * Math.cos(Math.toRadians(angleFin));
        double yFin = centre.y() - rayon * Math.sin(Math.toRadians(angleFin));

        // Conversion des angles en degrés positifs entre 0 et 360
        double angleStart = Math.min(angleDebut, angleFin);
        double angleEnd = Math.max(angleDebut, angleFin);
        double angleExtent = angleEnd - angleStart;
        if (angleExtent < 0) {
            angleExtent += 360; // Correction si l'angle de fin est inférieur à l'angle de début
        }

        // Création de la chaîne SVG représentant le secteur
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<path d=\"");
        svgBuilder.append("M").append(centre.x()).append(",").append(centre.y()); // Move to centre
        svgBuilder.append("L").append(xDebut).append(",").append(yDebut); // Line to start point of arc
        svgBuilder.append("A").append(rayon).append(",").append(rayon); // Arc with radius
        svgBuilder.append(" 0 "); // x-axis-rotation
        svgBuilder.append(angleExtent >= 180 ? "1" : "0").append(" "); // large-arc-flag
        svgBuilder.append(angleDebut > angleFin ? "1" : "0").append(" "); // sweep-flag
        svgBuilder.append(xFin).append(",").append(yFin); // End point of arc
        svgBuilder.append("Z"); // Close path
        svgBuilder.append("\" fill=\""+couleur +"\" stroke=\"black\"/>");

        return svgBuilder.toString();
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
     * Redimensionne le secteur en fonction des facteurs spécifiés sur les axes x et y.
     *
     * @param facteurX Facteur de redimensionnement pour l'axe des x.
     * @param facteurY Facteur de redimensionnement pour l'axe des y.
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
     */
    @Override
    public double hauteur() {
        return 2 * rayon;
    }

    /**
     * Retourne la largeur du secteur (le double du rayon).
     *
     * @return Largeur du secteur.
     */
    @Override
    public double largeur() {
        return 2 * rayon;
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
        // TODO Faire tourner pour chaque forme, 
        // modifier dupliquer, description, enSVG, et les test en conséquence
    }
}