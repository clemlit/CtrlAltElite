package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

public class Groupe implements IForme {
    // ATTRIBUTS
    private List<IForme> formes;
    private int index;

    // CONSTRUCTEUR
    public Groupe(IForme... formes) {
        this.formes = new ArrayList<>();
    }

    // METHODES

    public Groupe ajoutGroupe(IForme forme) {
        formes.add(forme);
        return this;
    }

    public Point centre() {
        if (formes.isEmpty()){
            return null;
        }

        double centreX = 0.0;
        double centreY = 0.0;
        int nombreDeFormes = 0;

        for (IForme forme : formes){
            Point centreForme = forme.centre();
            if (centreForme != null){
                centreX += centreForme.x();
                centreY += centreForme.y();
                nombreDeFormes++;
            }
        }

        //Moyenne du centre de toutes les formes
        if(nombreDeFormes > 0){
            centreX /= nombreDeFormes;
            centreY /= nombreDeFormes;
        }

        return new Point(centreX, centreY);

    }

    public void deplacer(double dx, double dy) {
        for (int i = 0; i < formes.size(); i++) {
            formes.get(i).deplacer(dx, dy);
        }
    }

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

    public IForme dupliquer() {
        Groupe groupedupliquer = new Groupe();

        for (int i = 0; i < formes.size(); i++) {
            IForme formeDupliquee = formes.get(i).dupliquer();
            groupedupliquer.ajoutGroupe(formeDupliquee);
        }

        return groupedupliquer;
    }

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

    public void redimensionner(double dx, double dy) {
        for (int i = 0; i < formes.size(); i++) {
            formes.get(i).redimensionner(dx, dy);
        }
    }
}