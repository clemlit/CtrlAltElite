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
    public void centre() {
        setUp();
        po.centre();
        assertEquals(p, po.centre());
    }

    @Test
    public void hauteur(){
        double hauteurExpected = 2.0;
        assertEquals(hauteurExpected, po.hauteur(), 0.001);
    }

    @Test
    public void redimensionner(){
        double nouvelleLargeur = 3.0;
        double nouvelleHauteur = 4.0;
        po.redimensionner(nouvelleLargeur / po.largeur(), nouvelleHauteur / po.hauteur());
        assertEquals(nouvelleLargeur, po.largeur(), 0.001);
        assertEquals(nouvelleHauteur, po.hauteur(), 0.001);
    }

    @Test
    public void deplacer(){
        double deplacementX = 1.0;
        double deplacementY = -1.0;
        po.deplacer(deplacementX, deplacementY);
        Point nouveauCentre = new Point(2.0 + deplacementX, 2.0 + deplacementY);
        assertEquals(nouveauCentre, po.centre());
    }

    @Test
    public void enSVG(){
        String svgExpected = "<svg xmlns=\"http://www.w3.org/2000/svg\"> <g>\n<polygon points=\"1.0 2.0 3.0 1.0 2.0 3.0 \" fill=\"null\" stroke=\"black\" />\n</g>\n</svg>";
        assertEquals(svgExpected, po.enSVG());
    }

    @Test
    public void ajouterSommetPoint(){
        Point nouveauPoint = new Point(4.0, 4.0);
        po.ajouterSommetPoint(nouveauPoint);
        assertEquals(nouveauPoint, po.getSommets().get(po.getSommets().size() - 1));
    }

    @Test
    public void ajouterSommet(){
        po.ajouterSommet(5.0, 5.0);
        assertEquals(new Point(5.0, 5.0), po.getSommets().get(po.getSommets().size() - 1));
    }

    @Test
    public void dupliquer(){
        Polygone copiePolygone = (Polygone) po.dupliquer();
        assertEquals(po.getSommets(), copiePolygone.getSommets());
        assertEquals(po.getCouleur(), copiePolygone.getCouleur());
        assertEquals(po.getAngle(), copiePolygone.getAngle());
    }

    @Test
    public void coloriser(){
        po.colorier("Red");
        assertEquals("Red", po.getCouleur());
    }

    @Test
    public void tourner(){
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
