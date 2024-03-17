package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.Alignement;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Point;

public class Faisceau implements IForme {
    private String nom;
    private List<Rectangle> barres;

    public Faisceau(String nom, double... h) {
        this.nom = nom;
        this.barres = new ArrayList<Rectangle>();
        for (int i = 0; i < h.length; i++) {
            Rectangle r = new Rectangle(0, 0, 1, h[i]);
            barres.add(r);
        }
    }

    public void ajouterBarre(Rectangle rectangle) {
        barres.add(rectangle);
    }

    @Override
    public IForme colorier(String... couleurs) {
        int i = 0;
        for (Rectangle barre : barres) {
            if (i < couleurs.length) {
                barre.colorier(couleurs[i]);
                i++;
            } else {
                break;
            }
        }
        return this;
    }

    /**
     * Agence les barres selon l'orientation spécifiée.
     * 
     * @param axeX          Axe sur lequel aligner
     * @param axeY          Axe sur lequel aligner
     * @param largeur       Largeur totale du faisceau
     * @param echelle       Facteur d'échelle vertical pour les différentes barres
     * @param verticalement Vrai si les barres sont empilées verticalement, faux si
     *                      horizontalement
     */
    public void agencer(double axeX, double axeY, double largeur, double echelle, boolean verticalement) {
        Groupe groupe = new Groupe();
        for (Rectangle barre : barres) {
            groupe.ajoutGroupe(barre);
        }       
        if (verticalement) {
            groupe.redimensionner(largeur, echelle);
            groupe.alignerElements(Alignement.HAUT, axeY);
            groupe.alignerElements(Alignement.DROITE, axeX); 
            groupe.empilerElements(Alignement.HAUT, axeX, 0); // Aligner les barres vers le haut

            
        } else {
            double lbarre = (largeur - (5 * (barres.size() - 1))) / barres.size();
            groupe.redimensionner(lbarre, echelle);
            groupe.alignerElements(Alignement.HAUT, axeY);
            groupe.alignerElements(Alignement.DROITE, axeX);
            groupe.empilerElements(Alignement.DROITE, axeX, 5); // Aligner les barres vers la droite
        }
    }

    @Override
    public Point centre() {
        double sommeX = 0.0;
        double sommeY = 0.0;
        for (Rectangle barre : barres) {
            sommeX += barre.centre().x();
            sommeY += barre.centre().y();
        }
        double centreX = sommeX / barres.size();
        double centreY = sommeY / barres.size();
        return new Point(centreX, centreY);
    }

    @Override
    public double hauteur() {
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for (Rectangle barre : barres) {
            double barreMinY = barre.centre().y() - barre.hauteur() / 2;
            double barreMaxY = barre.centre().y() + barre.hauteur() / 2;
            if (barreMinY < minY) {
                minY = barreMinY;
            }
            if (barreMaxY > maxY) {
                maxY = barreMaxY;
            }
        }
        return maxY - minY;
    }

    @Override
    public double largeur() {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (Rectangle barre : barres) {
            double barreMinX = barre.centre().x() - barre.largeur() / 2;
            double barreMaxX = barre.centre().x() + barre.largeur() / 2;
            if (barreMinX < minX) {
                minX = barreMinX;
            }
            if (barreMaxX > maxX) {
                maxX = barreMaxX;
            }
        }
        return maxX - minX;
    }

    @Override
    public String description(int indentation) {
        StringBuilder sb = new StringBuilder();
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += " ";
        }
        sb.append(indent).append("Faisceau - Nombre de barres : ").append(barres.size()).append("\n");
        for (Rectangle barre : barres) {
            sb.append(indent).append(barre.description(indentation + 2)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public IForme redimensionner(double dx, double dy) {
        for (Rectangle barre : barres) {
            barre.redimensionner(dx, dy);
        }
        return this;
    }

    @Override
    public IForme deplacer(double dx, double dy) {
        for (Rectangle barre : barres) {
            barre.deplacer(dx, dy);
        }
        return this;
    }

    @Override
    public String createEnSVG() {
        return "<svg xmlns=\"http://www.w3.org/2000/svg\">" + enSVG() + "</svg>";
    }

    @Override
    public String getCouleur() {
        return null; // Aucune couleur spécifique pour le faisceau lui-même
    }

    @Override
    public IForme tourner(int angle) {
        for (Rectangle barre : barres) {
            barre.tourner(angle);
        }
        return this;
    }

    @Override
    public IForme dupliquer() {
        Faisceau copieFaisceau = new Faisceau(nom);
        for (Rectangle barre : barres) {
            copieFaisceau.ajouterBarre(new Rectangle(barre.centre(), barre.largeur(), barre.hauteur()));
        }
        return copieFaisceau;
    }

    @Override
    public String enSVG() {
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<g>\n");
        for (Rectangle barre : barres) {
            svgBuilder.append(barre.enSVG());
        }
        svgBuilder.append("</g>\n");
        return svgBuilder.toString();
    }

}
