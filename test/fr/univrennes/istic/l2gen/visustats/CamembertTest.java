package test.fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.visustats.Camembert;

public class CamembertTest {
    @Test
    public void testAjouterSecteur() {
        Camembert camembert = new Camembert(0,0,1);
        camembert.ajouterSecteur("Red", 0.5);
        camembert.ajouterSecteur("Blue", 0.5);
        assertEquals(2, camembert.getSecteurs().size());
    }

    @Test
    public void testCentre() {
        Camembert camembert = new Camembert(0, 0, 1);
        assertEquals(new Point(0, 0), camembert.centre());
    }

    @Test
    public void testColorier() {
        Camembert camembert = new Camembert(0, 0, 1);
        camembert.ajouterSecteur("Red", 0.5);
        camembert.colorier("Black");
        assertEquals("Black", camembert.getCouleur());
    }
    

    @Test
    public void testDeplacer() {
        Camembert camembert = new Camembert(0, 0, 1);
        camembert.deplacer(2, 2);
        assertEquals(new Point(2, 2), camembert.centre());
    }

    @Test
    public void testDescription() {
        Camembert camembert = new Camembert(0, 0, 1);
        camembert.ajouterSecteur("Red", 0.2);
        camembert.ajouterSecteur("Blue", 0.8);
        String description = camembert.description(2);

        assertTrue(description.contains("Secteur de couleur Red avec un pourcentage de 20.0%"));
        assertTrue(description.contains("Secteur de couleur Blue avec un pourcentage de 80.0%"));

    }

    @Test
    public void testDupliquer() {
        Camembert camembert = new Camembert(0, 0, 10);
        Camembert copie = (Camembert) camembert.dupliquer();
        assertNotSame(camembert, copie);
    }

    @Test
    public void testEnSVG() {
        Camembert camembert = new Camembert(0, 0, 10);
        camembert.ajouterSecteur("Red", 0.5);
        String svgRepresentation = camembert.enSVG();
        assertNotNull(svgRepresentation);

    }

    @Test
    public void testHauteur() {
        Camembert camembert = new Camembert(0, 0, 1);
        assertEquals(2, camembert.hauteur(), 0.00001);
    }

    @Test
    public void testLargeur() {
        Camembert camembert = new Camembert(0, 0, 1);
        assertEquals(2, camembert.largeur(), 0.00001);
    }

    @Test
    public void testRedimensionner() {
        Camembert camembert = new Camembert(0, 0, 2);
        camembert.redimensionner(2, 1);

        assertEquals(4, camembert.getRayon(), 0.00001);
    }

    @Test
    public void testTourner() {
        Camembert camembert = new Camembert(0, 0, 1);
        camembert.tourner(45);
        assertEquals(45, camembert.getAngle(), 0.00001);
    }
}
