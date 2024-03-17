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
        double maxX = formes.get(0).centre().x()+formes.get(0).hauteur()/2;
        double minX = formes.get(0).centre().x()-formes.get(0).hauteur()/2;
        double maxY = formes.get(0).centre().y()+formes.get(0).largeur()/2;
        double minY = formes.get(0).centre().y()-formes.get(0).largeur()/2;
        
        for (int i =0;i<formes.size();i++){
            if (maxX<formes.get(i).centre().x()+formes.get(i).hauteur()/2){
                maxX=formes.get(i).centre().x()+formes.get(i).hauteur()/2;
            } else if (minX>formes.get(i).centre().x()-formes.get(i).hauteur()/2){
                minX=formes.get(i).centre().x()-formes.get(i).hauteur()/2;
            } if (maxY<formes.get(i).centre().y()+formes.get(i).largeur()/2){
                maxY=formes.get(i).centre().y()+formes.get(i).largeur()/2;
            } else if (minY>formes.get(i).centre().y()-formes.get(i).largeur()/2){
                minY=formes.get(i).centre().y()-formes.get(i).largeur()/2;
            }
        }
        return new Point((maxX-minX)/2, (maxY-minY)/2);
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
        groupeDuplique.angle = this.angle;
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
                svgBuilder.append(forme.enSVG());
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
        int index = 0;
        couleursRecursive(this, couleurs, index);
        return this;
    }

    /**
     * Méthode auxiliaire pour appliquer les couleurs de manière récursive.
     *
     * @param forme    Forme actuelle à colorier.
     * @param couleurs Tableau de couleurs à appliquer.
     * @param index    Indice pour suivre la couleur actuelle.
     * @return Nouvel indice de couleur.
     */
    private static int couleursRecursive(IForme forme, String[] couleurs, int index) {
        if (forme instanceof Groupe) {
            Groupe groupe = (Groupe) forme;
            for (IForme formeInterne : groupe.formes) {
                index = couleursRecursive(formeInterne, couleurs, index);
            }
        } 
        else {
            if (index < couleurs.length) {
                forme.colorier(couleurs[index]);
                index++;
            } else {
                forme.colorier(couleurs[index % couleurs.length]);
                index++;
            }
        }
        return index;
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

    @Override
    public String createEnSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + enSVG() + "</svg>";
    }


 /**
 * Aligne les éléments du groupe selon un alignement spécifié sur une cible donnée.
 *
 * @param alignement Direction d'alignement (HAUT, BAS, DROITE, GAUCHE).
 * @param axe        Ligne horizontale ou verticale sur laquelle doivent s'aligner les éléments du groupe.
 * @return Le groupe avec les éléments alignés.
 */
 public IForme alignerElements(Alignement alignement, double axe) {
     for (IForme forme : formes) {
         double decalageX = 0;
         double decalageY = 0;
         switch (alignement) {
             case HAUT:
                 decalageY = -forme.hauteur() / 2; // Alignement par le milieu de la hauteur vers le haut
                 break;
             case BAS:
                 decalageY = forme.hauteur() / 2; // Alignement par le milieu de la hauteur vers le bas
                 break;
             case DROITE:
                 decalageX = -forme.largeur() / 2; // Alignement par le milieu de la largeur vers la droite
                 break;
             case GAUCHE:
                 decalageX = forme.largeur() / 2; // Alignement par le milieu de la largeur vers la gauche
                 break;
         }
         forme.deplacer(axe + decalageX, axe + decalageY);
     }
     return this;
 }

 
/**
 * Empile les éléments du groupe selon un alignement spécifié sur une cible donnée avec une distance de séparation.
 *
 * @param alignement Direction d'alignement (HAUT, BAS, DROITE, GAUCHE).
 * @param cible      Ligne horizontale ou verticale sur laquelle doivent s'aligner les éléments du groupe.
 * @param separation Distance entre chaque élément empilé.
 * @return Le groupe avec les éléments empilés.
 */
public IForme empilerElements(Alignement alignement, double cible, double separation) {
    double positionX = 0; // Position horizontale de départ pour l'empilement
    double positionY = 0; // Position verticale de départ pour l'empilement

    for (IForme forme : formes) {
        double decalageX = 0;
        double decalageY = 0;

        // Ajustement de la position horizontale en fonction de l'alignement horizontal
        switch (alignement) {
            case DROITE:
                decalageX = positionX; // Ajustement pour l'alignement par la droite de la forme
                break;
            case GAUCHE:
                decalageX = -positionX; // Ajustement pour l'alignement par la gauche de la forme
                break;
        
            case HAUT:
                decalageY -= positionY; // Ajustement pour l'alignement par le haut de la forme
                break;
            case BAS:
                decalageY += positionY; // Ajustement pour l'alignement par le bas de la forme
                break;
        }

        forme.deplacer(cible + decalageX, cible + decalageY);

        // Mise à jour de la position pour la prochaine forme
        positionX += forme.largeur() + separation; // Met à jour la position horizontale pour l'empilement horizontal
        positionY += forme.hauteur() + separation; // Met à jour la position verticale pour l'empilement vertical
    }

    return this;
}






}
