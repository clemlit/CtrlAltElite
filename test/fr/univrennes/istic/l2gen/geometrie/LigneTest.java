package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.*;

public class LigneTest {
    @Test
    public void testAjouterSommet() {

        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);

        int nombreSommetsAvant = ligne.getSommets().size();
      
        ligne.ajouterSommet(new Point(3, 3));
      
        int nombreSommetsApres = ligne.getSommets().size();
      
        assertEquals(nombreSommetsAvant + 1, nombreSommetsApres);
      
        Point dernierSommet = ligne.getSommets().get(nombreSommetsApres - 1);
        assertEquals(3, dernierSommet.x(), 0.001);
        assertEquals(3, dernierSommet.y(), 0.001);
    }
      

    @Test
    public void testAjouterSommetCor() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        ligne.ajouterSommetCor(3, 1);
        assertEquals(4, ligne.getSommets().size());
        assertEquals(3, ligne.getSommets().get(3).x(), 0.001);
        assertEquals(1, ligne.getSommets().get(3).y(), 0.001);
       
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
        // Create a new Ligne instance with appropriate parameters
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);

        String expectedDescription = "  Ligne 0.0,0.0 1.0,1.0 2.0,0.0 ";

        String actualDescription = ligne.description(0);

        assertEquals(expectedDescription, actualDescription);
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
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        assertEquals(1, ligne.hauteur(), 0.001);

    }

    @Test
    public void testLargeur() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        assertEquals(2, ligne.largeur(), 0.001);

    }

    @Test
    public void testRedimensionner() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        double ancienneLargeur = ligne.largeur();
        double ancienneHauteur = ligne.hauteur();
        ligne.redimensionner(2, 0.5);
        double nouvelleLargeur = ligne.largeur();
        double nouvelleHauteur = ligne.hauteur();

        // Vérification de la hauteur et largueur multiplié par 2
        assertEquals(ancienneLargeur * 2, nouvelleLargeur, 0.001);

        assertEquals(ancienneHauteur * 0.5, nouvelleHauteur, 0.001);
    }
    
   
    @Test
    public void testTourner() {
        Ligne ligne = new Ligne(0, 0, 1, 1, 2, 0);
        ligne.tourner(90);
        assertEquals(90, ligne.getAngle());
    }
}
