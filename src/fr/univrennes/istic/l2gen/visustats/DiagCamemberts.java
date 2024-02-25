package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;


import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Secteur;



public class DiagCamemberts implements IDataVisualiseur {

    private Camembert camembert;
    private String couleur; 
    private String legence;
    private int nbreDePart;
    private List<Camembert> camemberts;

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
    public IDataVisualiseur ajouterDonnees(String donnees, double... valeurs) {

        // Vérification si le nombre de valeurs est au moins 1
        if (valeurs.length > 0) {
            double total = 0;

            // Calculer la somme totale des valeurs
            for (double valeur : valeurs) {
                total += valeur;
            }

            // Ajouter les secteurs au camembert en utilisant les valeurs absolues
            for (int i = 0; i < valeurs.length; i++) {
                String couleur = "Couleur" + i;
                double pourcentage = valeurs[i] / total;
                camembert.ajouterSecteur(couleur, pourcentage);
            }
        } else {
            throw new IllegalArgumentException("Erreur");
        }

        return this; // Ajoutez cette ligne pour respecter la signature de la méthode
    }

    @Override
    public Point centre() {
        return camembert.centre();
    }

    @Override
    public IForme colorier(String... couleurs) {
        return camembert.colorier(couleurs);
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
    public String enSVG() {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100); // Création d'un camembert avec un rayon de 100 à la position 0
        }

        // Mettre à jour la légende du camembert
        String updatedCamembertSVG = camembert.createEnSVG();

        // Concaténer la légende avec le contenu du camembert
        String resultSVG = updatedCamembertSVG + camembert.getLegende();

        // Retourner le résultat
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
        StringBuilder legendeSVG = new StringBuilder();

        int xOffset = ((int)camembert.centre().x()) / 2; // Ajuster l'espacement horizontal entre les légendes
        int yOffset = (int)camembert.centre().y() + (int)camembert.hauteur() + 20; // Offset vertical en dessous du camembert

        List<Secteur> secteurs = camembert.getSecteurs();
        


        for (int i = 0; i < legendes.length; i++) {
            String legende = legendes[i];
            int nbreChar = legende.length();

            legendeSVG.append("<text x=\"").append(xOffset + 20).append("\" y=\"").append(yOffset + 15).append("\" fill=\"black\">").append(legende)
                    .append("</text>\n");

            xOffset += nbreChar + 85; // Ajouter un espace supplémentaire entre les légendes
        }
        camembert.ajouterLegende(legendeSVG.toString());

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
    public String createEnSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + enSVG() + "</svg>";
    }
    
}
