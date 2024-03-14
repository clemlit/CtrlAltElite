package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Secteur;

public class Camembert implements IForme {
    // ATTRIBUTS
    private double rayon;
    private Point centre;
    private double angle;
    private String couleur;
    private String legende;
    private List<Secteur> secteurs;

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }
    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public double getAngle() {
        return angle;
    }

    // CONSTRUCTEURS
    /**
     *
     * @param valeur1 Coordonnée x du centre du camembert.
     * @param valeur2 Coordonnée y du centre du camembert.
     * @param rayon   Rayon du camembert.
     */
    public Camembert(double valeur1, double valeur2, double rayon) {
        this(new Point(valeur1, valeur2), rayon);
    }

    /**
     *
     * @param point Coordonnées du centre du camembert.
     * @param rayon Rayon du camembert.
     */
    public Camembert(Point point, Double rayon) {
        this.rayon = rayon;
        this.centre = point;
        this.secteurs = new ArrayList<>();
        this.couleur = "White";
    }

    /**
     * Ajoute un secteur au camembert avec la couleur spécifiée et le pourcentage de
     * l'angle qu'il doit occuper.
     *
     * @param chaine Couleur du secteur.
     * @param valeur Pourcentage de l'angle occupé par le secteur.
     * @return L'instance actuelle de Camembert après l'ajout du secteur.
     */
    public Camembert ajouterSecteur(String chaine, double valeur) {
        double angleDebut = angle;
        double angleFin = angleDebut + (360.0 * valeur);

        // Créer un nouveau secteur avec la couleur spécifiée et les angles calculés
        Secteur secteur = new Secteur(centre, rayon, angleDebut, angleFin);
        secteur.colorier(chaine);

        // Ajouter le secteur à la liste des secteurs
        secteurs.add(secteur);

        // Mettre à jour l'angle pour le prochain secteur
        angle = angleFin;

        return this;
    }

    /**
     * Retourne le centre du camembert.
     *
     * @return Coordonnées du centre du camembert.
     */
    public Point centre() {
        return centre;
    }

    /**
     * Colorie le camembert en utilisant les couleurs spécifiées.
     *
     * @param couleurs Couleurs à appliquer au camembert.
     * @return L'instance actuelle de Camembert après la coloration.
     */
    public IForme colorier(String... couleurs) {
        for (int i = 0; i < secteurs.size() && i < couleurs.length; i++) {
            secteurs.get(i).colorier(couleurs[i]);
            this.couleur = couleurs[i];
        }

        return this;
    }

    /**
     * Déplace le camembert selon les déplacements spécifiés sur les axes x et y.
     *
     * @param dx Déplacement sur l'axe des x.
     * @param dy Déplacement sur l'axe des y.
     * @return L'instance actuelle de Camembert après le déplacement.
     */
    public IForme deplacer(double dx, double dy) {
        this.centre = this.centre.plus(dx, dy);
        return this;
    }

    public String description(int indentation) {
        String result = "  ";

        for (int i = 0; i < indentation; i++) {
            result += "  ";
        }

        for (Secteur secteur : secteurs) {
            double pourcentage = (secteur.getAngleFin() - secteur.getAngleDebut()) / 360.0;
            String description = "Secteur de couleur " + secteur.getCouleur() + " avec un pourcentage de "
                    + pourcentage * 100 + "%\n";
            result += description;
        }

        return result;
    }

    /**
     * Duplique le camembert en créant une nouvelle instance identique.
     *
     * @return Nouvelle instance de Camembert.
     */
    public IForme dupliquer() {
        Camembert copieCamembert = new Camembert(centre, rayon);
        return copieCamembert;
    }

    /**
     * Génère une représentation SVG du camembert.
     *
     * @return Chaîne de caractères représentant le camembert en format SVG.
     */
    public String enSVG() {
        String svg = "<g>\n";

        for (Secteur secteur : secteurs) {
            svg += secteur.enSVG();
        }

        svg += "</g>\n";
        return svg;
    }

    /**
     * Retourne la hauteur du camembert.
     *
     * @return Hauteur du camembert.
     */
    public double hauteur() {
        return 2 * rayon;
    }

    /**
     * Retourne la largeur du camembert.
     *
     * @return largeur du camembert.
     */
    public double largeur() {
        return 2 * rayon;
    }

    /**
     * Redimensionne le camembert en fonction des facteurs spécifiés sur les axes x
     * et y.
     *
     * @param dx Facteur de redimensionnement pour l'axe des x.
     * @param dy Facteur de redimensionnement pour l'axe des y.
     * @return L'instance actuelle de Camembert après la redimensionnement.
     */
    public IForme redimensionner(double dx, double dy) {
        // Redimensionner le camembert lui-même
        rayon *= Math.max(dx, dy);

        // Redimensionner chaque secteur dans la liste
        for (Secteur secteur : secteurs) {
            secteur.redimensionner(dx, dy);
        }

        return this;
    }

    @Override
    public String createEnSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + enSVG() + "</svg>";
    }

    /**
     * Tourne le camembert selon l'angle spécifié.
     *
     * @param angle Angle de rotation.
     * @return L'instance actuelle de Camembert après la rotation.
     */
    public IForme tourner(int angle) {
        this.angle += angle;
        return this;
    }

    /**
     * Retourne la couleur actuelle du camembert.
     *
     * @return Couleur actuelle du camembert.
     */
    @Override
    public String getCouleur() {
        return couleur;
    }

    public String getLegende() {
        return legende;
    }

    public void setLegende(String legende) {
        this.legende = legende;
    }

    // Ajouter une légende au camembert
    public void ajouterLegende(String legende) {
        this.legende = legende;
    }

}
