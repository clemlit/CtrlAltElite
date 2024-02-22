/**
 * L'interface IForme définit les méthodes communes pour les formes géométriques dans un espace bidimensionnel.
 */
package fr.univrennes.istic.l2gen.geometrie;

public interface IForme {

    /**
     * Retourne le centre de la forme géométrique.
     *
     * @return Point représentant le centre de la forme.
     */
    public Point centre();

    /**
     * Génère une description de la forme géométrique avec l'indentation spécifiée.
     *
     * @param indentation Niveau d'indentation pour la description.
     * @require indentation >= 0.
     * @return Chaîne de caractères décrivant la forme géométrique.
     */
    public String description(int indentation);

    /**
     * Retourne la hauteur de la forme géométrique.
     *
     * @return Hauteur de la forme.
     */
    public double hauteur();

    /**
     * Retourne la largeur de la forme géométrique.
     *
     * @return Largeur de la forme.
     */
    public double largeur();

    /**
     * Redimensionne la forme géométrique en fonction des facteurs spécifiés sur les
     * axes x et y.
     *
     * @param dx Facteur de redimensionnement pour l'axe des x.
     * @param dy Facteur de redimensionnement pour l'axe des y.
     * @require les valeurs de dx et dy doivent être strictement positives.
     * @ensures la IForme ne doit pas dépasser les limites de l'affichage.
     */
    public IForme redimensionner(double dx, double dy);

    /**
     * Déplace la forme géométrique selon les déplacements spécifiés sur les axes x
     * et y.
     *
     * @param dx Déplacement sur l'axe des x.
     * @param dy Déplacement sur l'axe des y.
     * @ensures La IForme ne doit pas dépasser les limites de l'affichage.
     */
    public IForme deplacer(double dx, double dy);

    /**
     * Duplique la forme géométrique en créant une nouvelle instance identique.
     *
     * @return Nouvelle instance de la forme géométrique.
     */
    public IForme dupliquer();

    /**
     * Génère une représentation SVG de la forme géométrique.
     *
     * @return Chaîne de caractères représentant la forme géométrique en format SVG.
     */
    public String enSVG();

    /**
     * Colorie la forme géométrique avec les couleurs spécifiées.
     *
     * @param couleurs Tableau variable de chaînes de caractères représentant les
     *                 couleurs.
     * @require couleurs n'est pas vide.
     * @require les couleurs de couleurs sont des couleurs existantes dans la
     *          bibliothèque SVG.
     */
    public IForme colorier(String... couleurs);

    /**
     * Récupère la couleur de la IForme
     *
     * @return la couleur de la IForme
     */
    public String getCouleur();

    /**
     * Effectue une rotation de la forme géométrique selon l'angle spécifié.
     *
     * @param angle Angle de rotation en degrés.
     */
    public IForme tourner(int angle);

    /**
     * Aligne la forme géométrique en fonction de l'alignement spécifié et de la
     * cible donnée.
     *
     * @param alignment Alignement souhaité (HAUT, BAS, GAUCHE, DROITE).
     * @require alignement = HAUT ou alignement = BAS ou alignement = GAUCHE ou
     *          alignement = DROITE
     * @param cible Coordonnée cible pour l'alignement.
     * @require cible < aux limites de l'affichage.
     * @return Instance de la forme géométrique alignée.
     * @ensures La forme ne doit pas dépasser les limites de l'écran.
     */
    default IForme aligner(Alignement alignment, double cible) {
        return this;
    }

    public String createEnSVG();

    
}
