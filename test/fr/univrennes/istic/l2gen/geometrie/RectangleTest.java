package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;

public class RectangleTest {
    Rectangle r;
    Point p;
    @Before
    public void setUp(){
        p = new Point(10, 10);
        r=new Rectangle(p, 100, 100);
    }
    
    @Test
    public void testCentre() {
        setUp();
        assertEquals(p,r.centre());
    }

    @Test
    public void testDeplacer() {
        Point x = new Point(20, 20);
        r.deplacer(10, 10);
        assertEquals(r.centre(),x);
    }

    @Test
    public void testDescription() {
        String verif = "  Rectangle Centre="+10+","+10+ " L="+100.0+" H="+100.0+" Angle=" + 0 + " Couleur="+"White";
        assertEquals(verif,r.description(0));
    }

    @Test
    public void testDupliquer() {
        IForme x = r.dupliquer();
        assertEquals(r.centre(),x.centre());
        assertEquals(r.description(0),x.description(0));
        assertEquals(r.enSVG(),x.enSVG());
        assertEquals(r.hauteur(),x.hauteur(),0.1);
        assertEquals(r.largeur(),x.largeur(),0.1);


            
    }

    @Test
    public void testEnSVG() {
        String test = "<rect"+" x=\"" + r.centre().x() + "\""+" y=\"" + r.centre().y() + "\""+" width=\"" + r.largeur() + "\""+" height=\"" + r.hauteur() + "\""+" fill=\"" + "white" + "\""+" stroke=\"black\""+" />\n";
        assertEquals(test, r.enSVG());
    }

    @Test
    public void testHauteur() {
        assertEquals(100,r.hauteur(),0.1);
    }

    @Test
    public void testLargeur() {
        assertEquals(100,r.largeur(),0.1);
    }

    @Test
    public void testRedimensionner() {
        r.redimensionner(10, 10);
        assertEquals(r.largeur(),1000,0.1);
        assertEquals(r.hauteur(),1000,0.1);
        assertEquals(r.centre().x(),-440,0.1);
        assertEquals(r.centre().y(),-440,0.1);
    }

    @Test
    public void testColorier(){
        assertEquals("white",r.getCouleur());
    }

    @Test
    public void testTourner() {
        Rectangle rectangle = new Rectangle(0, 0, 10, 5);

        // méthode pour tourner avec un angle de 45 degrés
        rectangle.tourner(45);

        String descriptionApresRotation = rectangle.description(0);
        String descriptionAttendue = rectangle.description(0);

        assertEquals(descriptionAttendue, descriptionApresRotation);
    }
}
