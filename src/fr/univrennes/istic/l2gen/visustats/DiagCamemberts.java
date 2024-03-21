package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;


import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;



public class DiagCamemberts implements IDataVisualiseur {

    private Camembert camembert;
    private String couleur; 
    private String legence;
    private String titre;
    private List<Rectangle> Rec;
    private int nbreDePart;
    private List<Camembert> camemberts;
    private List<Texte> LegendeTexte;
    private StringBuilder legendeSVG = new StringBuilder();

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLegence() {
        return legence;
    }

    public void setLegence(String legence) {
        this.legence = legence;
    }

    public int getNbreDePart() {
        return nbreDePart;
    }

    public void setNbreDePart(int nbreDePart) {
        this.nbreDePart = nbreDePart;
    }

    public DiagCamemberts(String Titre, int x) {
        this.titre = Titre;
        this.nbreDePart = x;
        this.camemberts = new ArrayList<>();
        this.Rec = new ArrayList<>();
        this.LegendeTexte = new ArrayList<>(); 
    }

    @Override
    public IDataVisualiseur agencer() {
        if (camemberts != null) {
            int xOffset = 200; 
            int longeurtotale = (int)camemberts.get(0).centre().x();
            int longueurTexte = getTitre().length();
            for (int i = 0; i < camemberts.size(); i++) {
                Camembert currentCamembert = camemberts.get(i);
                currentCamembert.deplacer(xOffset, 0);
                Texte legende = LegendeTexte.get(i);
                legende.deplacer(xOffset, 0);
                xOffset += currentCamembert.largeur() + 70;
            }
            double testY=camemberts.get(0).centre().y();
            double maxY=camemberts.get(0).centre().y()-camemberts.get(0).hauteur();
            // Calculer la largeur totale des camemberts
            for (Camembert camembert : camemberts) {
                if (camembert.centre().y()>testY){
                    testY=camembert.centre().y();
                    maxY=camembert.centre().y()-camembert.hauteur();
                }
                longeurtotale += camembert.centre().x();
            }
            int centerX = longeurtotale /  camemberts.size();
            Texte texteTitre = new Texte(centerX - longueurTexte * camemberts.size(), maxY, 20, getTitre());
            this.legendeSVG.append(texteTitre.enSVG());
        }
        return this;
    }

    @Override
    public IDataVisualiseur ajouterDonnees(String legende, double... valeurs) {
        Camembert nouveauCamembert = new Camembert(250, 250, 100);

        double total = 0;
        for (double valeur : valeurs) {
            total += valeur;
        }

        // Ajouter les secteurs au nouveau camembert
        for (double valeur : valeurs) {
            double pourcentage = valeur / total;
            nouveauCamembert.ajouterSecteur(couleur, pourcentage);
        }

        // Ajouter le nouveau camembert à la liste des camemberts
        camemberts.add(nouveauCamembert);

        // Créer un rectangle sous le camembert pour la légende
        Texte texte = new Texte((int) nouveauCamembert.getCentre().x(),
                (int) (nouveauCamembert.getCentre().y() + nouveauCamembert.getRayon() + 20), 15, legende);
        LegendeTexte.add(texte);

        return this;
    }

    @Override
    public Point centre() {
        return camembert.centre();
    }

    @Override
    public IDataVisualiseur legender(String... legendes) {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100);
        }

        if (legendes.length > 0) {
            // Déterminons la position de départ de la légende
            int startX = (int) camembert.centre().x() + 400;
            int startY = (int) (camembert.hauteur() + 300);

            // Ajoutons la légende pour chaque carré de couleur
            for (int i = 0; i < legendes.length; i++) {
                String legende = legendes[i];

                // Créer un carré de couleur
                Rectangle rect = new Rectangle(startX, startY, 20, 10); // Ajustez les dimensions selon votre besoin
                Rec.add(rect);

                String legendeSVG = "<text x=\"" + (startX + 15) + "\" y=\"" + (startY + 5) + "\">" + legende
                        + "</text>";

                // Ajoutons le carré de couleur et la légende à la légende générale
                this.legendeSVG.append(rect.enSVG()).append(legendeSVG);

                startX += 100;
            }
        }
        return this;
    }

    @Override
    public IForme colorier(String... couleurs) {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100);
        }
        
        for (Camembert camembert : camemberts){
            camembert.colorier(couleurs);
        }

        //ATTENTION DIVISION PAR 0
        if (couleurs.length == 0) {
            return this;
        }

        int index = 0;
        for (Rectangle rectangle : Rec) {
            String couleur = couleurs[index % couleurs.length];
            rectangle.colorier(couleur);
            this.legendeSVG.append(rectangle.enSVG());
            index++;
        }
        return this;
    }

    @Override
    public String createEnSVG() {
        String svg = "";
        // Générer le SVG de chaque camembert
        for (Camembert currentCamembert : camemberts) {
            svg += currentCamembert.enSVG();
        }

        for (Texte legende : LegendeTexte ){
            svg += legende.enSVG();
        }
        svg += legendeSVG;
        return svg;
    }

    @Override
    public String enSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + createEnSVG() + "</svg>";
    }
    

    @Override
    public double hauteur() {
        return camembert.hauteur();
    }

    @Override
    public double largeur() {
        return camembert.largeur();
    }

    @Override
    public IForme redimensionner(double x, double y) {
        return camembert.redimensionner(x, y);
    }

    @Override
    public IDataVisualiseur setOptions(String... options) {
        return this;
    }

    @Override
    public IForme tourner(int angle) {
        return camembert.tourner(angle);
    }

    @Override
    public String getCouleur() {
        return couleur;
    }    
    
    @Override
    public IForme deplacer(double x, double y) {
        return camembert.deplacer(x, y);
    }

    @Override
    public String description(int indentation) {
        return camembert.description(indentation);
    }

    @Override
    public IForme dupliquer() {
        return camembert.dupliquer();
    }

}
