package fr.univrennes.istic.l2gen.visustats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Ligne;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagColonnes implements IDataVisualiseur {

    private double xmax = 0.0;

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
            for (int i = 0; i < faisceaux.size(); i++) {
                Faisceau faisceau = faisceaux.get(i);
                faisceau.deplacer(xOffset, 0);
                Texte legende = LegendeTexte.get(i);
                legende.deplacer(xOffset, 0);
                xOffset += faisceau.largeur() + 50;
            }
            double testY = faisceaux.get(0).centre().y();
            for (Faisceau faisceau : faisceaux) {
                if (faisceau.centre().y() > testY) {
                    testY = faisceau.centre().y();
                }
            }
            int x_titre;
            if (faisceaux.size() % 2 == 0) {
                x_titre = 404 + (404 + 135 * faisceaux.size() - 404) / 2 - 35;
            } else {
                x_titre = 404 + (404 + 135 * faisceaux.size() - 404 - 50) / 2;
            }
            Texte texteTitre = new Texte(x_titre, 100, 20, getTitre());
            this.legendeSVG.append(texteTitre.enSVG());

            double pointX1 = faisceaux.get(0).centre().x() - faisceaux.get(0).largeur() / 2;
            double pointY1 = faisceaux.get(0).centre().y() + faisceaux.get(0).hauteur() /2;
            double pointY2 = pointY1 - faisceaux.get(0).hauteur();
            double longeurMax = faisceaux.get(0).centre().x();
            for (Faisceau faisceau : faisceaux) {
                if (faisceau.centre().x() > longeurMax) {
                    longeurMax = faisceau.centre().x();
                }
            }
            double pointAbscisse = longeurMax + faisceaux.get(0).largeur() / 2;
            Ligne axeAbcisse = new Ligne(pointX1, pointY1, pointAbscisse, pointY1);
            this.legendeSVG.append(axeAbcisse.enSVG());
            Ligne axeOrdonee = new Ligne(pointX1, pointY1, pointX1, pointY2);
            this.legendeSVG.append(axeOrdonee.enSVG());

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            double[] scaleValuesAxes = generateProportionalValues(pointY1, pointY2, 6);
            double[] scaleValuesValeurs = generateProportionalValues(0.0, xmax, 6);

            for (int i = 0; i < scaleValuesAxes.length; i++) {
                double y = scaleValuesAxes[i];
                String valeurFormatee = decimalFormat.format(scaleValuesValeurs[i]);
                Texte legende = new Texte(pointX1 - 20, y + 4, 10, valeurFormatee);
                Ligne segment = new Ligne(pointX1 - 3, y, pointX1 + 3, y);
                this.legendeSVG.append(legende.enSVG());
                this.legendeSVG.append(segment.enSVG());
            }
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
        double echelle = 200.0 / nvfaisceau.hauteur();
        nvfaisceau.agencer(55, 200, 100, echelle, false);
        faisceaux.add(nvfaisceau);

        Texte texte = new Texte((int) nvfaisceau.centre().x(),
                (int) (nvfaisceau.centre().y() - 85 + nvfaisceau.largeur() + 20), 15, donnees);
        LegendeTexte.add(texte);

        for (int i = 0; i < x.length; i++) {
            if (x[i] > xmax) {
                xmax = x[i];
            }
        }

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
            int taille_legendes = 0;
            for (String legendeString : legendes) {
                taille_legendes += 20 + legendeString.length() * 10;
            }
            taille_legendes -= 20;
            int startX;
            if (faisceaux.size() % 2 == 0) { // ici on veut se positionner au milieu des 2 rectangles aux positions
                                             // faisceaux.size et faisceaux.size+1 car pair
                startX = 404 + (150 * faisceaux.size() - taille_legendes) / 2;
            } else { // ici on se positionne au milieu du rectangle à la position faisceaux.size/2+1
                // car impair
                startX = 404 + (150 * faisceaux.size() -taille_legendes-50) / 2;
            }
            int startY = 350;
            // Ajoutons la légende pour chaque carré de couleur
            for (int i = 0; i < legendes.length; i++) {
                String legende = legendes[i];

                // Créer un carré de couleur
                Rectangle rect = new Rectangle(startX, startY, 20, 10);
                Rec.add(rect);

                String legendeSVG = "<text x=\"" + (startX + 15) + "\" y=\"" + (startY + 5) + "\">" + legende
                        + "</text>";

                // Ajoutons le carré de couleur et la légende à la légende générale
                this.legendeSVG.append(rect.enSVG()).append(legendeSVG);

                startX += 20 + legende.length() * 10;
            }
        }
        return this;

    }

    @Override
    public IDataVisualiseur setOptions(String... options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOptions'");
    }

    public static double[] generateProportionalValues(double lowerBound, double upperBound, int count) {
        double[] scaleValues = new double[count];
        double step = (upperBound - lowerBound) / (count - 1);
        for (int i = 0; i < count; i++) {
            scaleValues[i] = lowerBound + i * step;
        }
        return scaleValues;
    }

}
