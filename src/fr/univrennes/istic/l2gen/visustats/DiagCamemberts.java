package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;

public class DiagCamemberts implements IDataVisualiseur {

    private Camembert camembert;

    public DiagCamemberts(String donnees, int x) {
        // TODO: Logique de construction du diagramme camembert
    }

    @Override
    public IDataVisualiseur agencer() {
        // TODO: Logique d'agencement des données pour le camembert
    }

    @Override
    public IDataVisualiseur ajouterDonnees(String donnees, double... x) {
        // TODO: Logique d'ajout de données pour le camembert
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
        return camembert.enSVG();
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
        // TODO : Logique de légende pour le diagramme camembert
    }

    @Override
    public IForme redimensionner(double x, double y) {
        return camembert.redimensionner(x, y);
    }

    @Override
    public IDataVisualiseur setOptions(String... options) {
        // TODO : Logique de configuration pour le diagramme camembert
    }

    @Override
    public IForme tourner(int angle) {
        return camembert.tourner(angle);
    }
}
