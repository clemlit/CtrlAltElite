package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

public class Ligne implements IForme {
    private List<Point> sommets;

    public Ligne(double... l) {
        this.sommets = new ArrayList<>();
        for (int i = 0; i < l.length; i += 2) {
            this.sommets.add(new Point(l[i], l[i + 1]));
        }
    }

    public Ligne(List<Point> sommets) {
        this.sommets = new ArrayList<>(sommets);
    }

    public void ajouterSommet(Point p) {
        this.sommets.add(p);
    }

    public void ajouterSommet(double x, double y) {
        this.sommets.add(new Point(x, y));
    }

    public Point centre() {
        double sommeX = 0;
        double sommeY = 0;
        for (Point sommet : sommets) {
            sommeX += sommet.x();
            sommeY += sommet.y();
        }
        return new Point(sommeX / sommets.size(), sommeY / sommets.size());
    }

    public String description(int indentation) {
        String indent = "";
        for (int i = 0; i < indentation; i++) {
            indent += " ";
        }
        String sommet = "";
        for (int i = 0; i < sommets.size(); i++) {
            sommet += sommets.get(i).x() + "," + sommets.get(i).y() + " ";
        }
        return indent + "Ligne " + sommet;
        
    }

    public List<Point> getSommets() {
        return new ArrayList<>(this.sommets);
    }

    public double hauteur() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Point sommet : sommets) {
            min = Math.min(min, sommet.y());
            max = Math.max(max, sommet.y());
        }
        return max - min;
    }

    public double largeur() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Point sommet : sommets) {
            min = Math.min(min, sommet.x());
            max = Math.max(max, sommet.x());
        }
        return max - min;
    }

    public void deplacer(double dx, double dy) {
        for (Point sommet : sommets) {
            sommet.plus(dx, dy);
        }
    }

    public IForme dupliquer() {
        List<Point> nouveauxSommets = new ArrayList<>();
        for (Point sommet : sommets) {
            nouveauxSommets.add(new Point(sommet.x(), sommet.y()));
        }
        return new Ligne(nouveauxSommets);
    }

    public void redimensionner(double px, double py) {
        for (Point sommet : sommets) {
            sommet.redimensionner(px, py);
        }
    }

    public String enSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append("<line ");
        svg.append("x1=\"" + sommets.get(0).x() + "\" ");
        svg.append("y1=\"" + sommets.get(0).y() + "\" ");
        svg.append("x2=\"" + sommets.get(1).x() + "\" ");
        svg.append("y2=\"" + sommets.get(1).y() + "\" ");
        svg.append("stroke=\"black\" ");
        svg.append("/>");
        return svg.toString();
    }
}