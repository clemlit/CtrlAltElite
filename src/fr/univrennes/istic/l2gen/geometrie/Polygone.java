package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;
import java.util.List;

public class Polygone implements IForme {
    List<Point> liste;

    public Polygone(double ... l) {
        this.liste=new ArrayList<Point>();
        for (int i=0;i<l.length;i=i+2){
            Point p = new Point(l[i], l[i+1]);
            liste.add(p);
        }
    }

    public double hauteur(){
        return null;
    }
    public Point centre(){
        return null;
    }

    public double largeur(){
        return null;
    }

    public void redimensionner(double dx, double dy){

    }

    public void deplacer(double dx, double dy){

    }

    
    public ArrayList<Point> getSommets() {
        ArrayList<Point> list = new ArrayList<Point>();
        for (int i = 0; i < liste.size(); i++) {
            list.add(liste.get(i));
        }
        return list;
    }
    
    @Override
    public String description(int i) {
        String txt = "Polygone";
        for (int e = 0; e < liste.size();e ++) {
            for (int f = 0; f < i; f++) {
                txt+=(" ");
            }
            txt+=(liste.get(e).x() + "," + liste.get(e).y());
        }
        return txt;
    }
    public void ajouterSommet(Point p) {
        liste.add(p);
    }
    
    public void ajouterSommet(double x, double y) {
        Point p = new Point(x, y);
        liste.add(p);
    }
    
    @Override
    public String enSVG() {

        StringBuilder svgBuilder = new StringBuilder();

        svgBuilder.append("<svg width=\"1000\" height=\"1000\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        svgBuilder.append("<polygon points=\"");
        for (int i =0;i<liste.size();i++){
            svgBuilder.append(liste.get(i).X()).append(",").append(liste.get(i).Y()).append(" ");
        }
        svgBuilder.append("\" stroke=\"black\" fill=\"white\" />\n");
        svgBuilder.append("</svg>");

        return svgBuilder.toString();
    }

    public IForme dupliquer() {
        return new Polygone(l);
    }

}
