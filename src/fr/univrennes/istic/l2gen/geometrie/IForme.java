package fr.univrennes.istic.l2gen.geometrie;

public interface IForme {

    public Point centre();

    public String description(int idnentation);

    public double hauteur();

    public double largeur();

    public void redimensionner(double dx, double dy);

    public void deplacer(double dx, double dy);

    public IForme dupliquer();
}
