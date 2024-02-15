package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

//import java.beans.Transient;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.Cercle;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;

public class CercleTest {

    @Test
    public void testHauteur() {
        IForme cercle = new Cercle(10, 10, 20);
        double hauteur = 20;
        assertEquals(hauteur, cercle.hauteur(), 0.1);
    }

    @Test
    public void testLargeur() {
        IForme cercle = new Cercle(10.0, 10.0, 20.0);
        double largeur = 40.0;
        assertEquals(largeur, cercle.largeur(), 0.1);
    }

    @Test
    public void testCentre() {
        IForme cercle = new Cercle(10, 10, 20);
        Point centre = new Point(10, 10);
        assertEquals(centre, cercle.centre());
    }

    @Test
    public void testDeplacer() {
        IForme cercle = new Cercle(10, 10, 20);
        Point rc = new Point(40, 40);
        cercle.deplacer(30, 30);
        assertEquals(rc, cercle.centre());
    }

    @Test
    public void testRedimensionner() {
        IForme cercle = new Cercle(10, 10, 20);
        double r = 200;
        cercle.redimensionner(7, 10);
        assertEquals(r, cercle.hauteur(), 0.1);
    }

    @Test
    public void testDescription() {
        int indentation = 4;
        IForme cercle = new Cercle(80.0, 66.0, 69.0);
        Point centre = cercle.centre();
        String ind = "";
        for (int i = 0; i < indentation; i++) {
            ind += "  ";
        }
        ind = ind + "Cercle centre = " + centre.x() + " , " + centre.y() + " r = " + cercle.hauteur();

        assertEquals(ind, cercle.description(4));
        assertNotEquals(ind, cercle.description(8));
    }

    @Test
    public void testDupliquer() {
        IForme cercle = new Cercle(30.0, 25.0, 15.0);
        IForme copie = cercle.dupliquer();
        assertEquals(cercle.centre(), copie.centre());
        assertEquals(cercle.largeur(), copie.largeur(), 0.1);
        assertEquals(cercle.hauteur(), copie.hauteur(), 0.1);
        assertEquals(cercle.description(0), copie.description(0));
        assertEquals(cercle.enSVG(), copie.enSVG());
    }

    @Test
    public void testEnSVG() {
        IForme cercle = new Cercle(60.0, 80.0, 60.0);
        Point centre = cercle.centre();
        String svg = "<circle cx=\"" + centre.x() + "\" cy=\"" + centre.y() + "\" r=\"" + cercle.hauteur()
                + "\" fill=\""
                + cercle.getCouleur() + "\" stroke=\"black\" />";
        assertEquals(svg, cercle.enSVG());
    }

    @Test
    public void testColorier() {
        IForme cercle = new Cercle(60.0, 70.0, 28.0);
        String couleur = "blanc";
        cercle.colorier("blanc");
        assertEquals(couleur, cercle.getCouleur());
    }
}
