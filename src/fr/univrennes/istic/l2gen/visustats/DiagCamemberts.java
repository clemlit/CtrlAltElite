package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;


import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;



public class DiagCamemberts implements IDataVisualiseur {

    private Camembert camembert;
    private String couleur; 
    private String legence;
    private String legendeGeneral;
    private int nbreDePart;
    private List<Camembert> camemberts;
    private StringBuilder legendeSVG = new StringBuilder();

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
        this.legence = Titre;
        this.nbreDePart = x;
        this.camemberts = new ArrayList<>();

    }

    @Override
    public IDataVisualiseur agencer() {
    if (camemberts != null) {
        int xOffset = 100; // Ajustez le décalage horizontal

        for (Camembert currentCamembert : camemberts) {
            currentCamembert.deplacer(xOffset, 0);
            xOffset += currentCamembert.largeur() + 20; // Ajustez l'espacement horizontal entre les camemberts
        }
    }

    return this;
}


    @Override
    public IDataVisualiseur ajouterDonnees(String legende, double... valeurs) {
        this.legendeGeneral = legende;
        if (valeurs.length > 0) {
            double total = 0;

            for (double valeur : valeurs) {
                total += valeur;
            }
            

            for (int i = 0; i < valeurs.length; i++) {
                String couleur = "Couleur" + i;
                double pourcentage = valeurs[i] / total;
                camembert.ajouterSecteur(couleur, pourcentage);
            }
        }

        return this; 
    }

    @Override
    public Point centre() {
        return camembert.centre();
    }

    @Override
    public IForme colorier(String... couleurs) {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100);
        }

        if (couleurs.length > 0) {
            camembert.colorier(couleurs);

            int xOffset = 78;
            int yOffset = (int) camembert.centre().y() + (int) camembert.hauteur() + 15;

            legendeSVG.append("<text x=\"").append(camembert.centre().x()-20).append("\" y=\"")
                    .append(camembert.largeur() + 175)
                    .append("\" fill=\"black\">").append(legendeGeneral)
                    .append("</text>\n");

            for (int i = 0; i < couleurs.length; i++) {
                String couleur = couleurs[i];

                legendeSVG.append("<rect x=\"").append(xOffset).append("\" y=\"").append(yOffset)
                        .append("\" width=\"10\" height=\"10\" fill=\"")
                        .append(couleur).append("\" />\n");

                xOffset += 85;
            }
        }

        return this;
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

    @Override
    public String createEnSVG() {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100); // Création d'un camembert avec un rayon de 100 à la position 0
        }

        String updatedCamembertSVG = camembert.createEnSVG();

        String resultSVG = updatedCamembertSVG + camembert.getLegende();

        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + resultSVG + "</svg>";
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
    public IDataVisualiseur legender(String... legendes) {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100); // Création d'un camembert avec un rayon de 100 à la position 0
        }

        // Générer une représentation de la légende
        StringBuilder legendeTexteSVG = new StringBuilder();

        int xOffset = 100;
        int yOffset = (int)camembert.centre().y() + (int)camembert.hauteur() + 10; 

        for (int i = 0; i < legendes.length; i++) {
            String legende = legendes[i];
            int nbreChar = legende.length();

            legendeTexteSVG.append("<text x=\"").append(xOffset + 2).append("\" y=\"").append(yOffset + 15).append("\" fill=\"black\">").append(legende)
                    .append("</text>\n");

            xOffset += nbreChar + 75; // Ajouter un espace supplémentaire entre les légendes
        }
        legendeSVG.append(legendeTexteSVG.toString());
        return this;
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
    public String enSVG() {

        String updatedCamembertSVG = camembert.createEnSVG();
        String resultSVG = updatedCamembertSVG + legendeSVG.toString();

        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + resultSVG + "</svg>";
    
    }
    
}
