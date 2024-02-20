package fr.univrennes.istic.l2gen.geometrie;

public class Texte implements IForme{
    private double x, y;
    private int fontSize;
    private String text;
    private String couleur;
    private int angle;

    public Texte(double x, double y, int fontSize, String text) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.text = text;
        this.angle = 0;
        this.couleur = "Black";
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
        return fontSize;
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
        // Construction de la chaîne SVG
        String svg = "<text";

        // Ajout des attributs du texte
        String xAttribute = " x=\"" + x + "\"";
        String yAttribute = " y=\"" + y + "\"";
        String fontSizeAttribute = " font-size=\"" + hauteur() + "\"";
        String textAnchorAttribute = " text-anchor=\"middle\"";
        String fillAttribute = " fill=\"" + couleur + "\"";
        String strokeAttribute = " stroke=\"" + couleur + "\"";

        // Condition pour ajouter l'attribut transform seulement si l'angle est
        // différent de zéro
        String transformAttribute = (angle != 0) ? " transform=\"rotate(" + angle + " " + x + " " + y + ")\"" : "";

        // Construction finale de la chaîne SVG
        svg += xAttribute + yAttribute + fontSizeAttribute + textAnchorAttribute + fillAttribute + strokeAttribute
                + transformAttribute + ">" + text + "</text>";

        return svg;
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

    public IForme tourner(int angle) {
         this.angle = angle;
         return this;
    }
}
