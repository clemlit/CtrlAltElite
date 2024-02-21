package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.*;

public class EllipseTest {
    
    @Test
    public void testHauteur() {
        IForme ellipse = new Ellipse(10.0, 10.0, 20, 20.0);
        double hauteur = 20.0;
        assertEquals(hauteur, ellipse.hauteur(), 0.1);
    }

    @Test
    public void testLargeur() {
        IForme ellipse = new Ellipse(10.0, 10.0, 20.0, 20.0);
        double largeur = 20.0;
        assertEquals(largeur, ellipse.largeur(), 0.1);
    }

    @Test
    public void testCentre() {
        IForme ellipse = new Ellipse(10, 10, 20, 20);
        Point centre = new Point(10, 10);
        assertEquals(centre, ellipse.centre());
    }
    
    @Test
    public void testRedimensionner() {
        IForme ellipse = new Ellipse(10, 10, 20, 20);
        double r = 200;
        ellipse.redimensionner(7, 10);
        assertEquals(r, ellipse.hauteur(), 0.1);
    }

    @Test
    public void testDescription() {
        int indentation = 4;
        IForme ellipse = new Ellipse(80.0, 66.0, 69.0, 50.0);
        Point centre = ellipse.centre();
        String ind = "  ";
        for (int i = 0; i < indentation; i++) {
            ind += "  ";
        }
        ind = ind + "Ellipse  Centre=" + (int) centre.x() + "," + (int) centre.y() + "  rx=" + ellipse.largeur() + "  ry="
                + ellipse.hauteur() + " Angle=" + ((Ellipse)ellipse).getAngle();

        assertEquals(ind, ellipse.description(4));
        assertNotEquals(ind, ellipse.description(8));
    }


    @Test
    public void testDeplacer() {
        IForme ellipse = new Ellipse(10, 10, 20, 20);
        Point re = new Point(40, 40);
        ellipse.deplacer(30, 30);
        assertEquals(re, ellipse.centre());
    }
    
    @Test
    public void testDupliquer() {
        Ellipse ellipse = new Ellipse(0, 0, 5, 3);
        IForme copie = ellipse.dupliquer();
        assertEquals(ellipse.centre(), copie.centre());
        assertEquals(ellipse.largeur(), copie.largeur(), 0.1);
        assertEquals(ellipse.hauteur(), copie.hauteur(), 0.1);
        assertEquals(ellipse.description(0), copie.description(0));
        assertEquals(ellipse.enSVG(), copie.enSVG());
    }

    @Test
    public void testEnSVG() {
        IForme ellipse = new Ellipse(10, 10, 20, 20);
        Point centre = ellipse.centre();
        String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\"><ellipse cx=\"" + centre.x() + "\" cy=\"" + centre.y() + "\" rx=\"" + ellipse.largeur() + "\" ry=\"" + ellipse.hauteur() + "\"" 
                    + " fill=\""+ellipse.getCouleur()+"\" stroke=\"black\" />\n</svg>";
        assertEquals(svg, ellipse.enSVG());
    }

    @Test
    public void testColorier() {
        IForme ellipse = new Ellipse(10, 10, 20, 20);
        String couleur = "White";
        ellipse.colorier("White");
        assertEquals(couleur, ellipse.getCouleur());
    }

    @Test
    public void testTourner() {
        Ellipse ellipse = new Ellipse(10, 10, 20, 20);
        ellipse.tourner(90);
        assertEquals(90, ellipse.getAngle());
    }
}
