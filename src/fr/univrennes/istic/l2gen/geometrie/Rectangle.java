package fr.univrennes.istic.l2gen.geometrie;

public class Rectangle implements IForme{
    private Point p;
    private double largeur;
    private double hauteur;

    public Rectangle(double x, double y, double largeur, double hauteur) {
        this.p = new Point(x, y);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public Rectangle(Point p, double largeur, double hauteur) {
        this.p = p;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public Point centre() {
        return p;
    }

    public String description(int indentation) {
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += " ";
        }
        return indent + "Rectangle " + "Centre=" + (int) centre().x() +"," + (int) centre().y() + " L=" + largeur + " H=" + hauteur;
    }

    public double hauteur() {
        return hauteur;
    }

    public double largeur() {
        return largeur;
    }

    public void deplacer(double dx, double dy) {
        p = p.plus(dx, dy);
    }

    public IForme dupliquer() {
        return new Rectangle(p, largeur, hauteur);
    }

    public void redimensionner(double px, double py) {
        largeur *= px;
        hauteur *= py;
    }

    public String enSVG() {
        return "<rect x=\"" + centre().x() + "\" y=\"" + centre().y() + "\" width=\"" + largeur() + "\" height=\""
                + hauteur()
                + "\"\n" + "\t" + "fill=\"white\" stroke=\"black\"/>";
    }
}
