package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagCamemberts implements IDataVisualiseur {

    /**
     * Attributs du diagramme.
     */
    private Camembert camembert;
    private String couleur;
    private String legence;
    private String titre;
    private List<Rectangle> Rec;
    private int nbreDePart;
    private List<Camembert> camemberts;
    private List<Texte> LegendeTexte;
    private StringBuilder legendeSVG = new StringBuilder();

    /**
     * Obtient le titre.
     * 
     * @return une chaine de caractères.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit la valeur du titre.
     * 
     * @param titre une chaine de caractères.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Obtient la légende.
     * 
     * @return une chaîne de caractères legence.
     */
    public String getLegence() {
        return legence;
    }

    /**
     * Définit la valeur de legence.
     * 
     * @param legence une chaîne de caractères.
     */
    public void setLegence(String legence) {
        this.legence = legence;
    }

    /**
     * Obtient le nombre de partie du camembert.
     * 
     * @return le nombre de part du camembert.
     */
    public int getNbreDePart() {
        return nbreDePart;
    }

    /**
     * Définit la valeur du nombre de parties du camembert.
     * 
     * @param nbreDePart le nombre de parties du camembert.
     */
    public void setNbreDePart(int nbreDePart) {
        this.nbreDePart = nbreDePart;
    }

    /**
     * Constructeur du diagramme camembert.
     * 
     * @param Titre le titre du diagramme
     * @param x     le nombre de parties du diagramme
     */
    public DiagCamemberts(String Titre, int x) {
        this.titre = Titre;
        this.nbreDePart = x;
        this.camemberts = new ArrayList<>();
        this.Rec = new ArrayList<>();
        this.LegendeTexte = new ArrayList<>();
    }

    /**
     * Déplacer les faisceaux pour pouvoir mettre les informations dans l'ordre
     * souhaité par l'utilisateur.
     * 
     * @requires des faisceaux déjà existants.
     */
    @Override
    public IDataVisualiseur agencer() {
        if (camemberts != null) {
            int xOffset = 200;
            int longeurtotale = (int) camemberts.get(0).centre().x();
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
            int centerX = longeurtotale / camemberts.size();
            Texte texteTitre = new Texte(centerX - longueurTexte * camemberts.size(), maxY, 20, getTitre());
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

    /**
     * Retourne le centre de la forme géométrique
     * 
     * @return un point au centre de la forme géométrique
     */
    @Override
    public Point centre() {
        return camembert.centre();
    }

    /**
     * Ajoute une légende au diagramme.
     * 
     * @param legendes un tableau de chaînes de caractères.
     */
    @Override
    public IDataVisualiseur legender(String... legendes) {
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100);
        }
        int taille_legendes=0;
        for (String legendeString : legendes){
            taille_legendes+=20+legendeString.length()*10;
        }
        taille_legendes-=20;
        if (legendes.length > 0) {
            // Déterminons la position de départ de la légende
            int startX;
            if (camemberts.size() % 2 == 0) { // ici on veut se positionner au milieu des 2 rectangles aux positions
                                             // faisceaux.size et faisceaux.size+1 car pair
                startX = 350 + (270 * camemberts.size() -taille_legendes) / 2;
            } else { // ici on se positionne au milieu du rectangle à la position faisceaux.size/2+1
                     // car impair
                startX = 350 + (270 * camemberts.size() - 70-taille_legendes) / 2;
            }
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

                startX+=20+legende.length()*10;
            }
        }
        return this;
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
        if (camembert == null) {
            camembert = new Camembert(250, 250, 100);
        }

        for (Camembert camembert : camemberts) {
            camembert.colorier(couleurs);
        }

        // ATTENTION DIVISION PAR 0
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

    /**
     * Génère le code SVG représentant le triangle sans remplissage.
     *
     * @return Le code SVG du diagramme sans remplissage.
     */
    @Override
    public String createEnSVG() {
        String svg = "";
        // Générer le SVG de chaque camembert
        for (Camembert currentCamembert : camemberts) {
            svg += currentCamembert.enSVG();
        }

        for (Texte legende : LegendeTexte) {
            svg += legende.enSVG();
        }
        svg += legendeSVG;
        return svg;
    }

    /**
     * Génère le code en SVG.
     */
    @Override
    public String enSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + createEnSVG() + "</svg>";
    }

    /**
     * Calcule la hauteur de la forme.
     */
    @Override
    public double hauteur() {
        return camembert.hauteur();
    }

    /**
     * Calcule la largeur de la forme.
     */
    @Override
    public double largeur() {
        return camembert.largeur();
    }

    /**
     * Renvoi la nouvelle forme modifié
     * 
     * @param x le facteur de redimenssionement pour l'axe des x
     * @param y le facteur de redimenssionement pour l'axe des y
     */
    @Override
    public IForme redimensionner(double x, double y) {
        return camembert.redimensionner(x, y);
    }

    /**
     * Définit les nouvelles options de dataVisualiseurs
     * 
     * @param options un tableau de String
     * @return une chaine de caractères
     */
    @Override
    public IDataVisualiseur setOptions(String... options) {
        return this;
    }

    /**
     * Renvoi la forme après modification de son angle
     * 
     * @param angle le facteur de rotation à appliquer à la figure
     * @return la forme modifié
     */
    @Override
    public IForme tourner(int angle) {
        return camembert.tourner(angle);
    }

    /**
     * Obtient la couleur de la forme
     */
    @Override
    public String getCouleur() {
        return couleur;
    }

    /**
     * Change les coordonnées du centre de la figure
     * 
     * @param x la valeur a ajouté à l'axe des x
     * @param y la valeur a ajouté à l'axe des y
     * @return la figure déplacée
     */
    @Override
    public IForme deplacer(double x, double y) {
        return camembert.deplacer(x, y);
    }

    /**
     * Renvoi la description de la forme
     * 
     * @return la descritption d'un camembert
     */
    @Override
    public String description(int indentation) {
        return camembert.description(indentation);
    }

    /**
     * Renvoi une copie du camembert
     * 
     * @return un camembert
     */
    @Override
    public IForme dupliquer() {
        return camembert.dupliquer();
    }

}
