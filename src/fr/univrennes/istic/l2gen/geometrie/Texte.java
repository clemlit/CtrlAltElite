package fr.univrennes.istic.l2gen.geometrie;

public class Texte implements IForme{
    private double x, y;
    private int fontSize;
    private String text;
    private String couleur;

    public Texte(double x, double y, int fontSize, String text) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.text = text;
    }

    public Point centre() {
        return new Point(x, y);
    }

    public String description(int indentation) {
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += " ";
        }
        return indent + "Texte " + "Centre=" + (int) centre().x() +"," + (int) centre().y() + " FontSize=" + fontSize + " Text=" + text;
    }

    public double hauteur() {
        return 0;
    }

    public double largeur() {
        return 0;
    }

    public IForme redimensionner(double dx, double dy) {
        x *= dx;
        y *= dy;
        return this;
    }

    public IForme deplacer(double dx, double dy) {
        x += dx;
        y += dy;
        return this;
    }

    public IForme dupliquer() {
        return new Texte(x, y, fontSize, text);
    }

    public String enSVG() {
        return "<text x=\"" + x + "\" y=\"" + y + "\" font-size=\"" + hauteur() + "\" text-anchor=\"middle\" fill=\"black\" stroke=\"black\">" + text + "</text>";
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
}
