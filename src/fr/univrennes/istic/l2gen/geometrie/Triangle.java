package fr.univrennes.istic.l2gen.geometrie;

 public class Triangle implements IForme {

    /* coordonnées du premier point, x */
    private double x1;
    private double x2;
    private Point x;

    /* coordonnées du deuxième point, y */
    private double y1;
    private double y2;
    private Point y;

    /* coordonnées du troisième point, z */
    private double z1;
    private double z2;
    private Point z;

    public Triangle(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public Triangle(Point x, Point y, Point z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point centre() {
        double centreX = (this.x.x() + this.y.x() + this.z.x()) / 3;

        double centreY = (this.x.y() + this.y.y() + this.z.y()) / 3;

        Point centre = new Point(centreX, centreY);

        return centre;
    }

    public double hauteur() {
        double numerateur = Math.abs((y1 - x1) * (x1 - z1) - (x1 - z1) * (y1 - x1));

        double denominateur = Math.sqrt(Math.pow(y1 - x1, 2) + Math.pow(y1 - x1, 2));

        double hauteur = numerateur / denominateur;

        return hauteur;
    }

    /* Détermine le coté le plus petit du triangle pour déterminer la largeur */
    public double largeur() {
        double res = Math.sqrt(Math.pow(y1 - x1, 2) + Math.pow(y2 - x2, 2));

        if (res > Math.sqrt(Math.pow(y1 - z1, 2) + Math.pow(y2 - z2, 2))) {
            res = Math.sqrt(Math.pow(y1 - z1, 2) + Math.pow(y2 - z2, 2));
        }

        else if (res > Math.sqrt(Math.pow(x1 - z1, 2) + Math.pow(x2 - z2, 2))) {
            res = Math.sqrt(Math.pow(x1 - z1, 2) + Math.pow(x2 - z2, 2));
        }

        return res;
    }

    public String description(int indentation) {
        String ali = "";
        for (int i = 0; i < indentation; i++) {
            ali += " ";
        }
        return ali + "Triangle " + x1 + "," + x2 + " " + y1 + "," + y2 + " " + z1 + "," + z2;
    }

    public void redimensionner(double dx, double dy) {
        this.x1 *= dx;
        this.x2 *= dy;
        this.y1 *= dx;
        this.y2 *= dy;
        this.z1 *= dx;
        this.z2 *= dy;
    }

    public void deplacer(double dx, double dy) {
        // Déplacement des coordonnées des points du triangle
        this.x1 += dx;
        this.x2 += dy;
        this.y1 += dx;
        this.y2 += dy;
        this.z1 += dx;
        this.z2 += dy;
    }

    public IForme dupliquer() {
        return new Triangle(x1, x2, y1, y2, z1, z2);
    }
    
    public String enSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"100\" height=\"100\">\n");
        svg.append("<polygon points=\"" + x1 + "," + x2 + " " + y1 + "," + y2 + " " + z1 + "," + z2 + "\" />\n");
        svg.append("</svg>");
        return svg.toString();
    }
        
}

