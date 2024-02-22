package fr.univrennes.istic.l2gen.geometrie;

/**
 * La classe Texte représente un élément de texte dans un espace bidimensionnel.
 * Implémente l'interface IForme pour les opérations géométriques de base.
 */
public class Texte implements IForme {

    private double x, y;
    private int fontSize;
    private String text;
    private String couleur;
    private int angle;

    /**
     * Constructeur de la classe Texte.
     *
     * @param x        La coordonnée x du centre du texte.
     * @param y        La coordonnée y du centre du texte.
     * @param fontSize La taille de la police du texte.
     * @param text     Le contenu textuel du texte.
     * @requires Les valeurs de x et y doivent être strictement positives.
     * @requires fontSize ne doit pas être nul.
     * @requires text ne doit pas être nul.
     */
    public Texte(double x, double y, int fontSize, String text) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.text = text;
        this.angle = 0;
        this.couleur = "Black";
    }

    /**
     * Renvoie le point central du texte.
     *
     * @return Un objet Point représentant les coordonnées du centre du texte.
     */
    public Point centre() {
        return new Point(x, y);
    }

    /**
     * Renvoie une description du texte avec une indentation spécifiée.
     *
     * @param indentation L'indentation à appliquer à la description.
     * @return Une chaîne de caractères décrivant le texte avec l'indentation spécifiée.
     */
    public String description(int indentation) {
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += " ";
        }
        return indent + "Texte " + "Centre=" + (int) centre().x() + "," + (int) centre().y() + " FontSize=" + fontSize + " Text=" + text;
    }

    /**
     * Renvoie la hauteur du texte (équivalente à la taille de la police).
     *
     * @return La hauteur du texte.
     */
    public double hauteur() {
        return fontSize;
    }

    /**
     * Renvoie la largeur du texte (toujours égale à zéro).
     *
     * @return La largeur du texte.
     */
    public double largeur() {
        return 0;
    }

    /**
     * Redimensionne le texte en modifiant ses coordonnées selon les facteurs de redimensionnement spécifiés.
     *
     * @param dx Le facteur de redimensionnement horizontal.
     * @param dy Le facteur de redimensionnement vertical.
     * @return L'objet Texte redimensionné.
     * @ensures Le texte ne doit pas dépasser les limites d'affichage.
     */
    public IForme redimensionner(double dx, double dy) {
        x *= dx;
        y *= dy;
        return this;
    }

    /**
     * Déplace le texte en ajoutant les déplacements spécifiés à ses coordonnées actuelles.
     *
     * @param dx Le déplacement horizontal.
     * @param dy Le déplacement vertical.
     * @return L'objet Texte déplacé.
     * @ensures Le texte ne doit pas dépasser les limites d'affichage.
     */
    public IForme deplacer(double dx, double dy) {
        x += dx;
        y += dy;
        return this;
    }

    /**
     * Duplique le texte en créant un nouvel objet Texte avec les mêmes propriétés.
     *
     * @return Un nouvel objet Texte identique à celui d'origine.
     */
    public IForme dupliquer() {
        return new Texte(x, y, fontSize, text);
    }

    /**
     * Génère une représentation SVG du texte avec ses attributs.
     *
     * @return Une chaîne de caractères représentant le texte en format SVG.
     */
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

    /**
     * Change la couleur du texte en utilisant la première couleur spécifiée dans le tableau.
     *
     * @param couleurs Tableau de couleurs à appliquer.
     * @return L'objet Texte avec la nouvelle couleur.
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
     * Renvoie la couleur actuelle du texte.
     *
     * @return La couleur du texte.
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Fait tourner le texte en spécifiant un angle.
     *
     * @param angle L'angle de rotation à appliquer au texte.
     * @return L'objet Texte tourné.
     */
    public IForme tourner(int angle) {
        this.angle = angle;
        return this;
    }
}
