package fr.univrennes.istic.l2gen.geometrie.visustats;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Secteur;

public class Camembert implements IForme {
    //ATTRIBUTS 
    private double rayon;
    private Point centre;
    private double angle;
    private String couleur;
    private List<Secteur> secteurs;


    public double getAngle() {
        return angle;
    }

    //CONSTRUCTEURS
    public Camembert(double valeur1, double valeur2, double rayon){
        this(new Point(valeur1, valeur2), rayon);
    }

    public Camembert(Point point, Double rayon){
        this.rayon = rayon;
        this.centre = point;
        this.secteurs = new ArrayList<>();
        this.couleur = "White";
    }

    public Camembert ajouterSecteur(String chaine, double valeur){
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

    public Point centre(){
        return centre;
    }

    public IForme colorier(String ... couleurs){
        for (Secteur secteur : secteurs) {
            secteur.colorier(couleurs);
        }
        return this;
    }

    public IForme deplacer(double dx, double dy){
        this.centre = this.centre.plus(dx, dy);
        return this;
    }

    public String description(int valeur){
        return null;
    }

    public IForme dupliquer(){
        Camembert copieCamembert = new Camembert(centre, rayon);
        return copieCamembert;
    }

    public String enSVG(){
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<g>\n");

        for (Secteur secteur : secteurs) {
            svgBuilder.append(secteur.enSVG());
        }

        svgBuilder.append("</g>\n");
        return svgBuilder.toString();
    }

    public double hauteur(){
        return 2 * rayon;
    }

    public double largeur(){
        return 2 * rayon;
    }

    public IForme redimensionner(double dx, double dy) {
        // Redimensionner le camembert lui-même
        rayon *= Math.max(dx, dy);

        // Redimensionner chaque secteur dans la liste
        for (Secteur secteur : secteurs) {
            secteur.redimensionner(dx, dy);
        }

        return this;
    }

    public IForme tourner(int angle){
        this.angle = angle;
        return this;
    }




    @Override
    public String getCouleur() {
        return couleur;
    }



}
