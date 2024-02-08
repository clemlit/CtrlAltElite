package fr.univrennes.istic.l2gen.geometrie;

public class Point {
    //ATTRIBUTS
    private double x;
    private double y;

    //CONSTRUCTEUR
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true; // Les objets sont identiques, pas besoin de comparer plus loin
        }
        if (object == null || getClass() != object.getClass()) {
            return false; // L'objet n'est pas une instance de la classe Point
        }
        Point otherPoint = (Point) object;

        return Double.compare(otherPoint.x, this.x) == 0 && Double.compare(otherPoint.y, this.y) == 0;
    }

    public Point plus(Point point){
        double newX = this.x + point.x;
        double newY = this.y + point.y;

        return new Point(newX, newY);
    }

    public Point plus(double x, double y) {
        this.x += x;
        this.y += y;
        return this; 
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void redimensionner(double px, double py) {
        this.x *= px;
        this.y *= py;
    }




}
