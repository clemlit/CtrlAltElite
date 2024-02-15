package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.Cercle;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Triangle;

public class TriangleTest {

    @Test
    public void testDescription() {
        int indentation = 4;
        IForme triangle = new Triangle(120.0, 66.0, 69.0);
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

    }

    @Test
    public void testEnSVG() {

    }

    @Test
    public void testCentre() {

    }

    @Test
    public void testColorier() {

    }

    @Test
    public void testDeplacer() {

    }

    @Test
    public void testDescription2() {

    }

    @Test
    public void testDupliquer2() {

    }

    @Test
    public void testEnSVG2() {

    }

    @Test
    public void testGetCouleur() {

    }

    @Test
    public void testHauteur() {

    }

    @Test
    public void testLargeur() {

    }

    @Test
    public void testRedimensionner() {

    }
}
