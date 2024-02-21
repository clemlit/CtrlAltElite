package fr.univrennes.istic.l2gen.geometrie.visustats;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Point;

public class Faisceau implements IForme {
    private String nom;
    private double h1;
    private double h2;
    private double h3;
    private List<Rectangle> barres;

    public Faisceau(String nom, double h1, double h2, double h3) {
        this.nom = nom;
        this.barres = new ArrayList<>();
        // Ajouter des rectangles avec des hauteurs différentes à la liste
        barres.add(new Rectangle(new Point(0, 0), 1, h1));
        barres.add(new Rectangle(new Point(0, 0), 1, h2));
        barres.add(new Rectangle(new Point(0, 0), 1, h3));
    }

    public Faisceau(String nom){
        this.nom = nom;
        this.barres = new ArrayList<>();
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
 * @param axeX Axe sur lequel aligner la gauche (si verticalement) ou le bas (si horizontalement) du faisceau
 * @param axeY Axe sur lequel aligner la bas (si verticalement) ou la gauche (si horizontalement) du faisceau
 * @param largeur Largeur totale du faisceau
 * @param echelle Facteur d'échelle vertical pour les différentes barres
 * @param verticalement Vrai si les barres sont empilées verticalement, faux si horizontalement
 */
public void agencer(double axeX, double axeY, double largeur, double echelle, boolean verticalement) {
    Double totalx = axeX;
    double totaly = axeY;
    if (verticalement){
            for (Rectangle barre : barres) {
                barre.deplacer(axeX, totaly); // Déplacement de la barre
               
                // Redimensionnement de la barre
                barre.redimensionner(largeur, echelle);
                
                // Mise à jour des coordonnées pour la prochaine barre en fonction de l'orientation
                
                totaly -= 2*barre.hauteur()-10;
            }
        } else {
        double l = (largeur-10)/3;
        for (Rectangle barre : barres) {
            barre.deplacer(axeX, axeY); // Déplacement de la barre
           
            // Redimensionnement de la barre
            barre.redimensionner(l, echelle);
            
        
            
                barre.deplacer(totalx, axeY- barre.hauteur() / 2); // Déplacement horizontal
                totalx += (barre.largeur()+ 5 );
            }
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
        Faisceau copieFaisceau = new Faisceau(nom, h1, h2, h3);
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