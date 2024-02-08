package fr.univrennes.istic.l2gen.geometrie;

public class Cercle implements IForme {
    
    private double x;
    private double y;
    private double r;

    public Cercle(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Cercle(Point p,double x){
        this.x = p.x();
        this.y = p.y();
    }

    public Point centre(){
        return new Point(this.x, this.y);
    }

    public String description(int indentation){
        Point centre = centre();
        String ind = "";
        for(int i =0;i<indentation;i++){
            ind += "  ";
        }
        return ind + "Cercle centre = " + centre.x() + " , " + centre.y() + " r = " + this.r;

    }

    public double hauteur(){
        return r;

    }

    public double largeur(){
        return r*2;

    }

    public IForme dupliquer(){
        Cercle copieCercle = new Cercle(this.x, this.y, this.r);
        return copieCercle;
    }

    public void redimmensionner(double px, double py){
        this.x = x * px;
        this.y = y * py;
    }

    public void deplacer(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void redimensionner(double dx, double dy) {
        this.r = this.r * Math.max(dx, dy);
    }
    
    @Override
    public String enSVG() {
        Point centre = centre();
        String svg = "<circle cx=\"" + centre.x() + "\" cy=\"" + centre.y() + "\" r=\"" + this.r + " \"fill=\"white\" stroke=\"black\" />";
        return svg;
    }
}
