package fr.univrennes.istic.l2gen.geometrie;

import javafx.geometry.Point2D;

public class Secteur implements IForme {
    private Point centre;
    private double rayon;
    private double angleDebut;
    private double angleFin;

    // Constructeur
    public Secteur(Point centre, double rayon, double angleDebut, double angleFin) {
        this.centre = centre;
        this.rayon = rayon;
        this.angleDebut = angleDebut;
        this.angleFin = angleFin;
    }

    public Secteur(double p1,double p2, double rayon, double angleDebut, double angleFin) {
        this.centre = new Point(p1, p2);
        this.rayon = rayon;
        this.angleDebut = angleDebut;
        this.angleFin = angleFin;
    }

    // Accesseurs
    public Point getCentre() {
        return centre;
    }

    public double getRayon() {
        return rayon;
    }

    public double getAngleDebut() {
        return angleDebut;
    }

    public double getAngleFin() {
        return angleFin;
    }

    // Mutateurs
    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    public void setAngleDebut(double angleDebut) {
        this.angleDebut = angleDebut;
    }

    public void setAngleFin(double angleFin) {
        this.angleFin = angleFin;
    }

   // Implémentation de la méthode description pour l'interface IForme
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

   // Implémentation de la méthode toSVG pour générer la représentation SVG
   // Implémentation de la méthode toSVG pour générer la représentation SVG
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
    svgBuilder.append("\" fill=\"none\" stroke=\"black\"/>");

    return svgBuilder.toString();
}

   
    // Implémentation de la méthode dupliquer pour l'interface IForme
    @Override
    public IForme dupliquer() {
        return new Secteur(centre, rayon, angleDebut, angleFin);
    }
    
    // Implémentation de la méthode redimensionner pour l'interface IForme
    @Override
    public void redimensionner(double facteurX, double facteurY) {
        rayon *= Math.max(facteurX, facteurY);
    }

    @Override
    public void deplacer(double dx, double dy) {
        centre = centre.plus(dx, dy);
    }

    @Override
    public Point centre() {
        return centre;
    }
    
    @Override
    public double hauteur() {
        return 2 * rayon;
    }
    
    @Override
    public double largeur() {
        return 2 * rayon;
    }
    
   

    
}
