package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.*;

public class LigneTest {
    @Test
    public void testAjouterSommet() {
    
    }

    @Test
    public void testAjouterSommetCor() {
       
    }

    @Test
    public void testCentre() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        Point centre = new Point(1, 0.333);
        assertEquals(centre.x(), ligne.centre().x(), 0.001);
        assertEquals(centre.y(), ligne.centre().y(), 0.001);
    }

    @Test
    public void testColorier() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        ligne.colorier("rouge");
        assertEquals("rouge", ligne.getCouleur());
    }

    @Test
    public void testDeplacer() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        ligne.deplacer(1, 1);
        assertEquals(1, ligne.getSommets().get(0).x(), 0.001);
        assertEquals(1, ligne.getSommets().get(0).y(), 0.001);
        assertEquals(2, ligne.getSommets().get(1).x(), 0.001);
        assertEquals(2, ligne.getSommets().get(1).y(), 0.001);
        assertEquals(3, ligne.getSommets().get(2).x(), 0.001);
        assertEquals(1, ligne.getSommets().get(2).y(), 0.001);

    }

    @Test
    public void testDescription() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        String description = "Ligne : \n" + "  - sommet 1 : (0.0, 0.0)\n" + "  - sommet 2 : (1.0, 1.0)\n"
                + "  - sommet 3 : (2.0, 0.0)\n";
        assertEquals(description, ligne.description());

    }

    @Test
    public void testDupliquer() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        Ligne ligne2 = (Ligne) ligne.dupliquer();
        assertEquals(ligne.getSommets().get(0).x(), ligne2.getSommets().get(0).x(), 0.001);
        assertEquals(ligne.getSommets().get(0).y(), ligne2.getSommets().get(0).y(), 0.001);
        assertEquals(ligne.getSommets().get(1).x(), ligne2.getSommets().get(1).x(), 0.001);
        assertEquals(ligne.getSommets().get(1).y(), ligne2.getSommets().get(1).y(), 0.001);
        assertEquals(ligne.getSommets().get(2).x(), ligne2.getSommets().get(2).x(), 0.001);
        assertEquals(ligne.getSommets().get(2).y(), ligne2.getSommets().get(2).y(), 0.001);

    }

    @Test
    public void testEnSVG() {
        
    }

    @Test
    public void testGetSommets() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        assertEquals(3, ligne.getSommets().size());
        assertEquals(0, ligne.getSommets().get(0).x(), 0.001);
        assertEquals(0, ligne.getSommets().get(0).y(), 0.001);
        assertEquals(1, ligne.getSommets().get(1).x(), 0.001);
        assertEquals(1, ligne.getSommets().get(1).y(), 0.001);
        assertEquals(2, ligne.getSommets().get(2).x(), 0.001);
        assertEquals(0, ligne.getSommets().get(2).y(), 0.001);

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
