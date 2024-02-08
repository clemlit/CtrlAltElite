package fr.univrennes.istic.l2gen.geometrie;

public class Ellipse implements IForme {
    //ATTRIBUTS
    private double x;
    private double y; 
    private double demiGrandAxe;
    private double demiPetitAxe;

    //CONSTRUCTEUR
    public Ellipse(double x, double y, double demiGrandAxe, double demiPetitAxe){
        this.x = x;
        this.y = y;
        this.demiGrandAxe = demiGrandAxe;
        this.demiPetitAxe = demiPetitAxe;
    }

    public Ellipse(Point point, double demiGrandAxe, double demiPetitAxe){
        this.x = point.x();
        this.y = point.y();
        this.demiGrandAxe = demiGrandAxe;
        this.demiPetitAxe = demiPetitAxe;
    }

    public Point centre() {
        return new Point(x, y);
    }

    public String description(int indentation) {
        String vide = "";
        Point point = centre();
        for (int i = 0; i < indentation; i++) {
            vide += "  ";
        }
        return vide + "Ellipse  Centre=" + (int) point.x() + "," + (int) point.y() + "  rx=" + largeur() + "  ry=" + hauteur();
    }

    public double hauteur() {
        return demiPetitAxe;
    }

    public double largeur() {
        return demiGrandAxe;
    }

    public void redimensionner(double dx, double dy) {
        this.demiGrandAxe = demiGrandAxe *  dx;
        this.demiPetitAxe = demiPetitAxe * dy;
    }

    public void deplacer(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public IForme dupliquer() {
        Ellipse copieEllipse = new Ellipse(this.x, this.y, this.demiGrandAxe, this.demiPetitAxe);
        return copieEllipse;
    }


}
