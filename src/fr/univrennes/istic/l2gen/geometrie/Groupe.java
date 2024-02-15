/**
 * La classe Groupe représente un ensemble de formes géométriques regroupées.
 * Elle implémente l'interface IForme pour fournir des fonctionnalités liées à la manipulation
 * de formes géométriques et permet de traiter un groupe de formes comme une seule entité.
 */
package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

public class Groupe implements IForme {
    // ATTRIBUTS

    /**
     * Liste des formes géométriques contenues dans le groupe.
     */
    private List<IForme> formes;

    /**
     * Index utilisé pour parcourir la liste des formes.
     */
    private int index;

    // CONSTRUCTEUR

    /**
     * Constructeur de la classe Groupe.
     *
     * @param formes Liste variable de formes géométriques à ajouter au groupe.
     */
    public Groupe(IForme... formes) {
        this.formes = new ArrayList<>();
    }

    // METHODES

    /**
     * Ajoute une forme géométrique au groupe.
     *
     * @param forme Forme géométrique à ajouter.
     * @return Le groupe avec la nouvelle forme ajoutée.
     */
    public Groupe ajoutGroupe(IForme forme) {
        formes.add(forme);
        return this;
    }

    /**
     * Calcule et retourne le centre du groupe en effectuant la moyenne des centres de toutes les formes.
     *
     * @return Point représentant le centre du groupe.
     */
    public Point centre() {
        if (formes.isEmpty()) {
            return null;
        }

        double centreX = 0.0;
        double centreY = 0.0;
        int nombreDeFormes = 0;

        for (IForme forme : formes) {
            Point centreForme = forme.centre();
            if (centreForme != null) {
                centreX += centreForme.x();
                centreY += centreForme.y();
                nombreDeFormes++;
            }
        }

        // Moyenne du centre de toutes les formes
        if (nombreDeFormes > 0) {
            centreX /= nombreDeFormes;
            centreY /= nombreDeFormes;
        }

        return new Point(centreX, centreY);
    }

    /**
     * Déplace toutes les formes du groupe selon les déplacements spécifiés sur les axes x et y.
     *
     * @param dx Déplacement sur l'axe des x.
     * @param dy Déplacement sur l'axe des y.
     */
    public void deplacer(double dx, double dy) {
        for (int i = 0; i < formes.size(); i++) {
            formes.get(i).deplacer(dx, dy);
        }
    }

    /**
     * Génère une description du groupe et de ses formes incluses avec l'indentation spécifiée.
     *
     * @param indentation Niveau d'indentation pour la description.
     * @return Chaîne de caractères décrivant le groupe et ses formes incluses.
     */
    public String description(int indentation) {
        String result = "Groupe\n";

        for (IForme sousForme : formes) {
            if (sousForme instanceof Groupe) {
                result += sousForme.description(0);
            } else {
                result += sousForme.description(0) + "\n";
            }
        }

        return result;
    }

    /**
     * Duplique le groupe en créant une nouvelle instance avec des copies des formes incluses.
     *
     * @return Nouvelle instance de Groupe identique à l'instance actuelle.
     */
    public IForme dupliquer() {
        Groupe groupeDuplique = new Groupe();

        for (int i = 0; i < formes.size(); i++) {
            IForme formeDupliquee = formes.get(i).dupliquer();
            groupeDuplique.ajoutGroupe(formeDupliquee);
        }

        return groupeDuplique;
    }

    /**
     * Calcule et retourne la hauteur maximale parmi toutes les formes incluses dans le groupe.
     *
     * @return Hauteur maximale du groupe.
     */
    public double hauteur() {
        double hauteurMax = 0.0;

        for (int i = 0; i < index; i++) {
            double hauteurForme = formes.get(i).hauteur();
            if (hauteurForme > hauteurMax) {
                hauteurMax = hauteurForme;
            }
        }

        return hauteurMax;
    }

    /**
     * Calcule et retourne la largeur maximale parmi toutes les formes incluses dans le groupe.
     *
     * @return Largeur maximale du groupe.
     */
    public double largeur() {
        double largeurMax = 0.0;

        for (int i = 0; i < index; i++) {
            double largeur = formes.get(i).largeur();
            if (largeur > largeurMax) {
                largeurMax = largeur;
            }
        }

        return largeurMax;
    }

    /**
     * Redimensionne toutes les formes du groupe en fonction des facteurs spécifiés sur les axes x et y.
     *
     * @param dx Facteur de redimensionnement pour l'axe des x.
     * @param dy Facteur de redimensionnement pour l'axe des y.
     */
    public void redimensionner(double dx, double dy) {
        for (int i = 0; i < formes.size(); i++) {
            formes.get(i).redimensionner(dx, dy);
        }
    }

    /**
     * Génère une représentation SVG du groupe et de ses formes incluses.
     *
     * @return Chaîne de caractères représentant le groupe en format SVG.
     */
    public String enSVG() {
        String svg = "<g>\n";

        for (IForme forme : formes) {
            svg += forme.enSVG();
        }

        svg += "</g>\n";
        return svg;
    }

    public void coloriser(String... couleurs) {

        int indexCouleur = 0;
        for (IForme forme : formes) {
            forme.coloriser(couleurs[indexCouleur]);
            indexCouleur += 1 % couleurs.length;
        }
    }
}
