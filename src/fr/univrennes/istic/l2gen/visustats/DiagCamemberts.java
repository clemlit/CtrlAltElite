package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;


import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;



public class DiagCamemberts implements IDataVisualiseur {

    private Camembert camembert;
    private String couleur; 
    private String legence;
    private List<Rectangle> Rec;
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
        this.Rec = new ArrayList<>();

    }

    @Override
    public IDataVisualiseur agencer() {
        if (camemberts != null) {
            int xOffset = 100; 
            for (Camembert currentCamembert : camemberts) {
                currentCamembert.deplacer(xOffset, 0);
                xOffset += currentCamembert.largeur() + 20; 
            }
        }
        return this;
    }


    @Override
    public IDataVisualiseur ajouterDonnees(String legende, double... valeurs) {
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
    public IDataVisualiseur legender(String... legendes) {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100);
        }

        if (legendes.length > 0) {
            // Déterminons la position de départ de la légende
            int startX = (int) camembert.centre().x();
            int startY = (int) (camembert.hauteur() + 180);

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
        camembert.colorier(couleurs);

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
            camembert = new Camembert(250, 250, 100); 
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
