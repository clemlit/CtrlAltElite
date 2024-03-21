package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagColonnes implements IDataVisualiseur {

    /**
     * Retourne le faisceau.
     * 
     * @return un faisceau.
     */
    public Faisceau getFaisceau() {
        return faisceau;
    }

    /**
     * Définit les valeurs du faisceau.
     * 
     * @param faisceau nouvelles valeur du faisceau.
     * @ensures la valeur de faisceau doit être un Faisceau.
     */
    public void setFaisceau(Faisceau faisceau) {
        this.faisceau = faisceau;
    }

    /**
     * attributs d'un faisceau.
     */
    private Faisceau faisceau;
    private List<Faisceau> faisceaux;
    private String titre;

    /**
     * Retourne le titre du diagramme.
     * 
     * @return une chaine de charactères.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre du diagramme.
     * 
     * @param titre les nouvelles valeurs du titre.
     * @require titre est une chaîne de caractères.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    private List<Rectangle> Rec;
    private List<Texte> LegendeTexte;

    private StringBuilder legendeSVG = new StringBuilder();

    /**
     * Constructeur du diagrammes colonnes.
     * 
     * @param titre le titre du diagramme.
     * @param x     sert à rien (à supprimer ?)
     */
    public DiagColonnes(String titre, int x) {
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

    /**
     * Colorie la forme géométrique avec les couleurs spécifiées.
     *
     * @param couleurs Tableau variable de chaînes de caractères représentant les
     *                 couleurs.
     * @require couleur n'est pas vide.
     * @require les couleurs du tableau couleurs sont des couleurs existantes dans
     *          la bibliothèque SVG.
     * @require couleurs est une couleur existante dans la bibliothèque SVG.
     */
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

    /**
     * Génère le code SVG représentant le triangle sans remplissage.
     *
     * @return Le code SVG du diagramme sans remplissage.
     */
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

    /**
     * Déplacer les faisceaux pour pouvoir mettre les informations dans l'ordre
     * souhaité par l'utilisateur.
     * 
     * @requires des faisceaux déjà existants.
     */
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
            Texte texteTitre = new Texte(centerX - longueurTexte * 3, 150, 20, getTitre());
            this.legendeSVG.append(texteTitre.enSVG());
        }
        return this;
    }

    /**
     * Créer des faisceaux pour ajouter des données au diagramme.
     * 
     * @param donnees une chaine de caractères à ajouter.
     * @param x       un tableau de double.
     */
    @Override
    public IDataVisualiseur ajouterDonnees(String donnees, double... x) {
        Faisceau nvfaisceau = new Faisceau(donnees, x);
        double echelle = 100.0 / nvfaisceau.hauteur();
        nvfaisceau.agencer(55, 200, 100, echelle, false);
        faisceaux.add(nvfaisceau);

        Texte texte = new Texte((int) nvfaisceau.centre().x(),
                (int) (nvfaisceau.centre().y() - 85 + nvfaisceau.largeur() + 20), 15, donnees);
        LegendeTexte.add(texte);

        return this;
    }

    /**
     * Ajoute une légende au diagramme.
     * 
     * @param legendes un tableau de chaînes de caractères.
     */
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
