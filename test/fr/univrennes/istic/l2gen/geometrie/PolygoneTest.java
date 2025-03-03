package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.*;

public class PolygoneTest {
    Polygone po;
    Point p = new Point(2, 2);
    @Before
    public void setUp(){
        po=new Polygone(1,2,3,1,2,3);
    }

    @Test
    public void testCentre() {
        setUp();
        po.centre();
        assertEquals(p.x(), po.centre().x(),0.001);
        assertEquals(p.y(), po.centre().y(),0.001);
 }

    @Test
    public void testLargeur(){
        double largeurExpected = 2.0;
        assertEquals(largeurExpected, po.largeur(), 0.001);
    }

    @Test
    public void testHauteur(){
        double hauteurExpected = 2.0;
        assertEquals(hauteurExpected, po.hauteur(), 0.001);
    }

    @Test
    public void testRedimensionner(){
        double nouvelleLargeur = 3.0;
        double nouvelleHauteur = 4.0;
        po.redimensionner(nouvelleLargeur / po.largeur(), nouvelleHauteur / po.hauteur());
        assertEquals(nouvelleLargeur, po.largeur(), 0.001);
        assertEquals(nouvelleHauteur, po.hauteur(), 0.001);
    }

    @Test
    public void testDeplacer(){
        double deplacementX = 1.0;
        double deplacementY = -1.0;
        po.deplacer(deplacementX, deplacementY);
        Point nouveauCentre = new Point(2.0 + deplacementX, 2.0 + deplacementY);
        assertEquals(nouveauCentre, po.centre());
    }

    @Test
    public void testEnSVG(){
        String svgExpected = "<g>\n<polygon points=\"1.0 2.0 3.0 1.0 2.0 3.0 \" fill=\"null\" stroke=\"black\" />\n</g>\n";
        assertEquals(svgExpected, po.enSVG());
    }

    @Test
    public void ajouterSommetPoint(){
        Point nouveauPoint = new Point(4.0, 4.0);
        po.ajouterSommetPoint(nouveauPoint);
        assertEquals(nouveauPoint, po.getSommets().get(po.getSommets().size() - 1));
    }

    @Test
    public void testAjouterSommet(){
        po.ajouterSommet(5.0, 5.0);
        assertEquals(new Point(5.0, 5.0), po.getSommets().get(po.getSommets().size() - 1));
    }

    @Test
    public void testDupliquer(){
        Polygone copiePolygone = (Polygone) po.dupliquer();
        assertEquals(po.getSommets(), copiePolygone.getSommets());
        assertEquals(po.getCouleur(), copiePolygone.getCouleur());
        assertEquals(po.getAngle(), copiePolygone.getAngle());
    }

    @Test
    public void testColoriser(){
        po.colorier("Red");
        assertEquals("Red", po.getCouleur());
    }

    @Test
    public void testTourner(){
        int angleRotation = 45;
        int angleAttendu = 45;
        po.tourner(angleRotation);
        assertEquals(angleAttendu, po.getAngle());
    }
    
    @Test
    public void testDescription() {
        String verif = "Polygone 1,2 3,1 2,3";
        assertEquals(verif,po.description(0));
    }
    
    @Test
    public void testAligner(){

    }
}
