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
    /**
     * @return 0 car pas de hauteur que l'on peut définir pour un polygone quelconque
     */
    public double hauteur(){
        return 0;
    }
    /**
     * @return le point 0,0 car pas de centre que l'on peut définir pour un polygone quelconque
     */
    public Point centre(){
        Point p = new Point(0, 0);
        return p;
    }

    /**
     * @return 0 car pas de largeur que l'on peut définir pour un polygone quelconque
     */
    public double largeur(){
        return 0;
    }
    /**
     * @return rien car jsp comment redimensionner un polygone quelconque
     */
    public void redimensionner(double dx, double dy){

    }
    /**
     * @return rien car jsp comment déplacer un polygone quelconque
     */
    public void deplacer(double dx, double dy){

    }

    /**
     * @return Une liste de tous les sommets
     */
    public ArrayList<Point> getSommets() {
        ArrayList<Point> list = new ArrayList<Point>();
        for (int i = 0; i < liste.size(); i++) {
            list.add(liste.get(i));
        }
        return list;
    }
    /**
     * @param i entre les points
     * @return un String décrivant les coordonnées de tous les points du polygone
     */
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

    /**
     * @param p point quelconque avec coordonnées quelconques
     * @return ajoute un point au polygone
     */
    public void ajouterSommet(Point p) {
        liste.add(p);
    }
    
    /**
     * @param x coordonnée x du point que l'on veut ajouter
     * @param x coordonnée y du point que l'on veut ajouter
     * @return ajoute le point avec les coordonnées correspondantes au polygone
     */
    public void ajouterSommet(double x, double y) {
        Point p = new Point(x, y);
        liste.add(p);
    }
    
    /**
     * @return le fichier SVG créant le polygone
     */
    @Override
    public String enSVG() {

        StringBuilder svgBuilder = new StringBuilder();

        svgBuilder.append("<svg width=\"1000\" height=\"1000\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        svgBuilder.append("<polygon points=\"");
        for (int i =0;i<liste.size();i++){
            svgBuilder.append(liste.get(i).x()).append(",").append(liste.get(i).y()).append(" ");
        }
        svgBuilder.append("\" stroke=\"black\" fill=\"white\" />\n");
        svgBuilder.append("</svg>");

        return svgBuilder.toString();
    }

    public double[] getListe(){
        double[] listeDouble = new double[liste.size()];
        int e=0;
        for(int i=0;i<liste.size();i++){
            listeDouble[e]=liste.get(i).x();
            listeDouble[e+1]=liste.get(i).y();
            e=e+2;
        }
        return listeDouble;
    }
    public IForme dupliquer() {
        return new Polygone(getListe());
    }

}
