package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagBarre implements IDataVisualiseur {

    public Faisceau getFaisceau() {
        return faisceau;
    }

    public void setFaisceau(Faisceau faisceau) {
        this.faisceau = faisceau;
    }

    private Faisceau faisceau;
    private List<Faisceau> faisceaux;
    private String titre;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    private List<Rectangle> Rec;
    private List<Texte> LegendeTexte;

    private StringBuilder legendeSVG = new StringBuilder();

    public DiagBarre(String titre, int x) {
        this.titre = titre;
        this.faisceaux = new ArrayList<>();
        this.Rec = new ArrayList<>();
        this.LegendeTexte = new ArrayList<>();

    }

    @Override
    public Point centre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'centre'");
    }

    @Override
    public String description(int indentation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'description'");
    }

    @Override
    public double hauteur() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hauteur'");
    }

    @Override
    public double largeur() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'largeur'");
    }

    @Override
    public IForme redimensionner(double dx, double dy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'redimensionner'");
    }

    @Override
    public IForme deplacer(double dx, double dy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deplacer'");
    }

    @Override
    public IForme dupliquer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dupliquer'");
    }

    @Override
    public String enSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + createEnSVG() + "</svg>";
    }

    @Override
    public IForme colorier(String... couleurs) {
        if (couleurs.length == 0) {
            return this;
        }
        for (Faisceau faisceau : faisceaux) {
            faisceau.colorier(couleurs);
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
    public String getCouleur() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCouleur'");
    }

    @Override
    public IForme tourner(int angle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tourner'");
    }

    @Override
    public String createEnSVG() {
        String svg = "";
        for (Faisceau faisceau : faisceaux) {
            svg += faisceau.enSVG();
        }
        for (Texte legende : LegendeTexte) {
            svg += legende.enSVG();
        }
        svg += legendeSVG;
        return svg;
    }

    @Override
    public IDataVisualiseur agencer() {

        if (faisceaux != null) {
            int xOffset = 100;
            int longeurtotale = (int) faisceaux.get(0).centre().x();
            int longueurTexte = getTitre().length();
            for (int i = 0; i < faisceaux.size(); i++) {
                Faisceau faisceau = faisceaux.get(i);
                faisceau.deplacer(xOffset, 0);
                Texte legende = LegendeTexte.get(i);
                legende.deplacer(xOffset, 0);
                xOffset += faisceau.largeur() + 50;
            }
            for (Faisceau faisceau : faisceaux) {
                longeurtotale += faisceau.centre().x();
            }
            int centerX = longeurtotale / faisceaux.size();
            Texte texteTitre = new Texte(centerX - longueurTexte * 3, 100, 20, getTitre());
            this.legendeSVG.append(texteTitre.enSVG());
        }
        return this;
    }

    @Override
    public IDataVisualiseur ajouterDonnees(String donnees, double... x) {
        Faisceau nvfaisceau = new Faisceau(donnees, x);
        double echelle = 100.0 / nvfaisceau.hauteur();
        nvfaisceau.agencer(55, 200, 100, echelle, true);
        faisceaux.add(nvfaisceau);

        Texte texte = new Texte((int) nvfaisceau.centre().x(),
                (int) (nvfaisceau.centre().y() - 5 + nvfaisceau.largeur() + 20), 15, donnees);
        LegendeTexte.add(texte);

        return this;
    }

    @Override
    public IDataVisualiseur legender(String... legendes) {
        if (legendes.length > 0) {
            // Déterminons la position de départ de la légende
            int startX = 450;
            int startY = 350;

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
    public IDataVisualiseur setOptions(String... options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOptions'");
    }

}
