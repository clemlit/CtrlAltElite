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

    private String couleur;

    private int angle;

    // CONSTRUCTEUR


    /**
     * Constructeur de la classe Groupe.
     *
     * @param formes Liste variable de formes géométriques à ajouter au groupe.
     */
    public Groupe(IForme... formes) {
        this.formes = new ArrayList<>();
        this.angle = 0;
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
     * @return Le groupe déplacé.
     */
    public IForme deplacer(double dx, double dy) {
        for (int i = 0; i < formes.size(); i++) {
            formes.get(i).deplacer(dx, dy);
        }
        return this;
    }

    /**
     * Génère une description du groupe et de ses formes incluses avec l'indentation spécifiée.
     *
     * @param indentation Niveau d'indentation pour la description.
     * @return Chaîne de caractères décrivant le groupe et ses formes incluses.
     */
    public String description(int indentation) {
        String indent = "  ";
        for (int i = 0; i < indentation; i++) {
            indent += "  "; // Deux espaces pour chaque niveau d'indentation
        }

        // Construction de la description du groupe
        String description = indent + "Groupe";

        // Appel récursif à la méthode description pour chaque forme dans le groupe
        for (IForme forme : formes) {
            description += "\n" + forme.description(indentation + 1);
        }

        return description;
    }

    /**
     * Duplique le groupe en créant une nouvelle instance avec des copies des formes incluses.
     *
     * @return Nouvelle instance de Groupe identique à l'instance actuelle.
     */
    public IForme dupliquer() {
        Groupe groupeDuplique = new Groupe();
        for (IForme forme : formes) {
            IForme formeDupliquee = forme.dupliquer();
            groupeDuplique.ajoutGroupe(formeDupliquee);
        }
        groupeDuplique.angle = this.angle; // Ajoutez cette ligne pour mettre à jour l'angle.
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
     * @return Le groupe redimensionné.
     */
    public IForme redimensionner(double dx, double dy) {
        for (int i = 0; i < formes.size(); i++) {
            formes.get(i).redimensionner(dx, dy);
        }
        return this;
    }

    /**
     * Génère une représentation SVG du groupe avec ses formes incluses.
     *
     * @return Une chaîne de caractères représentant le groupe en format SVG.
     */
    public String enSVG() {
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<g>\n");

        for (IForme forme : formes) {
            // Générer le SVG pour chaque forme en lui appliquant sa couleur
            String couleurForme = (forme.getCouleur() != null) ? forme.getCouleur() : "White";
            svgBuilder.append(forme.colorier(couleurForme).enSVG());
        }

        svgBuilder.append("</g>\n");
        return svgBuilder.toString();
    }

    /**
     * Change la couleur de chaque forme du groupe en utilisant les couleurs spécifiées.
     *
     * @param couleurs Tableau de couleurs à appliquer.
     * @return Le groupe avec les nouvelles couleurs appliquées.
     */
    @Override
    public IForme colorier(String... couleurs) {
        for (int i = 0; i < formes.size(); i++) {
            IForme forme = formes.get(i);
            if (i < couleurs.length) {
                forme.colorier(new String[]{couleurs[i]});
            } else {
                forme.colorier(new String[]{couleurs[i % couleurs.length]});
            }
        }
        return this;
    }

    /**
     * Renvoie la couleur du groupe (utilisée lors de la génération SVG).
     *
     * @return La couleur du groupe.
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Renvoie l'angle actuel de rotation du groupe.
     *
     * @return L'angle de rotation du groupe.
     */
    public int getAngle() {
        return angle;
    }

    /**
     * Fait tourner chaque forme du groupe en spécifiant un angle.
     *
     * @param angle L'angle de rotation à appliquer aux formes du groupe.
     * @return Le groupe avec les formes tournées.
     */
    public IForme tourner(int angle) {
        this.angle = angle;
        for (IForme forme : formes) {
            forme.tourner(angle);
        }
        return this;
    }
}
