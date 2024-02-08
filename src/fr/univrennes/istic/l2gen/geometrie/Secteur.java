package fr.univrennes.istic.l2gen.geometrie; 
import java.io.FileWriter;
import java.io.IOException;

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
       sb.append("Secteur centre=").append(String.format("%.0f", centre.getX())).append(",").append(String.format("%.0f", centre.getY()));
       sb.append(" Angle=").append(angleDebut);
       double arc = angleFin - angleDebut;
       if (arc < 0) {
           arc += 360; // Correction si l'angle de fin est inférieur à l'angle de début
       }
       sb.append(" Arc=").append(arc);
       return sb.toString();
   }

   // Implémentation de la méthode toSVG pour générer la représentation SVG
   @Override
public String enSVG() {
    double x1 = centre.getX() + rayon * Math.cos(Math.toRadians(angleDebut));
    double y1 = centre.getY() - rayon * Math.sin(Math.toRadians(angleDebut));
    double x2 = centre.getX() + rayon * Math.cos(Math.toRadians(angleFin));
    double y2 = centre.getY() - rayon * Math.sin(Math.toRadians(angleFin));
    
    // Arc flag
    int arcFlag = (angleFin - angleDebut <= 180) ? 0 : 1;

    StringBuilder svgBuilder = new StringBuilder();
    svgBuilder.append("<svg width=\"500\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n");
    svgBuilder.append("<path d=\"");
    svgBuilder.append("M").append(centre.getX()).append(" ").append(centre.getY());
    svgBuilder.append(" L").append(x2).append(",").append(y2); 
    svgBuilder.append(" A").append(rayon).append(",").append(rayon).append(" 0 ").append(arcFlag).append(",1 ").append(x1).append(",").append(y1); 
    svgBuilder.append(" Z\" stroke=\"black\" fill=\"white\" />\n");
    svgBuilder.append("</svg>");
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
    public void deplacer(double deltaX, double deltaY) {
        centre.deplacer(deltaX, deltaY);
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
