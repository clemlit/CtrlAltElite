package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Triangle;

public class TriangleTest {

    @Test
    public void testDescription() {
        int indentation = 0;
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);

        String ind = "  ";
        for (int i = 0; i < indentation; i++) {
            ind += "  ";
        }

        String expectedDescription = ind + "Triangle " + t.X.x() + "," + t.X.y() + "," + t.Y.y() + "," + t.Y.x() + "," + t.Z.x() + "," + t.Z.y();

        assertEquals(expectedDescription, t.description(indentation));
        
    }

    @Test
    public void testDupliquer() {
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);
        IForme x = t.dupliquer();
        assertEquals(t.centre(),x.centre());
        assertEquals(t.description(0),x.description(0));
        assertEquals(t.enSVG(),x.enSVG());
        assertEquals(t.hauteur(),x.hauteur(),0.1);
        assertEquals(t.largeur(),x.largeur(),0.1);
    }

    @Test
    public void testEnSVG() {
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);
        String svg = "<polygon points=\"" + t.X.x() + " " + t.X.y() + " " + t.Y.x() + " " + t.Y.y() + " "
        + t.Z.x()
        + " " + t.Z.y() + "\"";
        svg += " fill=\"" + t.getCouleur() + "\"";
        svg += " stroke=\"black\" />\n";        
        assertEquals(svg, t.enSVG());
    }

    @Test
    public void testCentre() {
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);
        double centreX = (t.X.x() + t.Y.x() + t.Z.x()) / 3.0;
        double centreY = (t.X.y() + t.Y.y() + t.Z.y()) / 3.0;
        Point centre = new Point(centreX, centreY);
        assertEquals(centre,t.centre());
    }

    @Test
    public void testColorier() {
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);
        t.colorier("white");
        assertEquals("white",t.getCouleur());
    }

    @Test
    public void testDeplacer() {
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);
        t.deplacer(10, 10);
        Triangle test = new Triangle(202 , 138 , 266 , 138 , 266 , 266);
        assertEquals(test.centre(), t.centre());
    }


    @Test
    public void testHauteur() {
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);
        assertEquals(114,t.hauteur(),0.1);
    }

    @Test
    public void testLargeur() {
        Triangle t = new Triangle(192 , 128 ,  256,128,  256 , 256);
        assertEquals(64,t.largeur(),0.1);
    }

    @Test
    public void testRedimensionner() {
        Triangle t = new Triangle(192 , 128 , 256 , 128 , 256 , 256);
        t.redimensionner(0.5, 0.5);
        Triangle tri = new Triangle(192, 171, 235,  160, 224, 235);
        assertEquals(tri.description(0), t.description(0));
        t.redimensionner(0.5, 0.5);
    }

    @Test
    public void testTourner() {
        Triangle triangle = new Triangle(192, 128, 256, 128, 256, 256);
        triangle.tourner(90);
        assertEquals(90, triangle.getAngle(), 0.0001);
    }
}
