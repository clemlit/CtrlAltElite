package test.fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.*;

public class SecteurTest {
    Secteur secteur;

    @Before
    public void setUp() {
        secteur = new Secteur(256, 256, 128, 0, 60);
    }

    @Test
    public void centre(){
        assertEquals(new Point(256,256), secteur.centre());
    }

    @Test
    public void hauteur(){
        double hauteur = 128.0;
        assertEquals(hauteur, secteur.hauteur(), 0.001);
    }

    @Test
    public void largeur(){
        double largeurExpected = 256.0;
        assertEquals(largeurExpected, secteur.largeur(), 0.001);
    }

    @Test
    public void redimensionner(){
        double facteurX = 1.5;
        double facteurY = 2.0;
        secteur.redimensionner(facteurX, facteurY);
        assertEquals(128 * 2.0, secteur.getRayon(), 0.0001);
    }

    @Test
    public void deplacer(){
        double deplacementX = -32.0;
        double deplacementY = 16.0;
        secteur.deplacer(deplacementX, deplacementY);
        Point nouveauCentre = new Point(256.0 + deplacementX, 256.0 + deplacementY);
        assertEquals(nouveauCentre, secteur.centre());
    }

    @Test
    public void enSVG(){
        String svgExpected = "<svg xmlns=\"http://www.w3.org/2000/svg\">\n<path d=\"M256.0,256.0L384.0,128.0A128.0,128.0 0 0 0 256.0,256.0Z\" fill=\"null\" stroke=\"black\"/></svg>";
        assertEquals(svgExpected, secteur.enSVG());
    }

    @Test
    public void dupliquer(){
        Secteur copieSecteur = (Secteur) secteur.dupliquer();
        assertEquals(secteur.getCentre(), copieSecteur.getCentre());
        assertEquals(secteur.getRayon(), copieSecteur.getRayon(), 0.001);
        assertEquals(secteur.getAngleDebut(), copieSecteur.getAngleDebut(), 0.001);
        assertEquals(secteur.getAngleFin(), copieSecteur.getAngleFin(), 0.001);
    }

    @Test
    public void coloriser(){
        secteur.colorier("Red");
        assertEquals("Red", secteur.getCouleur());
    }

    @Test
    public void tourner(){

        double angleDebutAttendu = 45 % 360;
        double angleFinAttendu = (60 + 45) % 360;
        secteur.tourner(45);
        assertEquals(angleDebutAttendu, secteur.getAngleDebut(), 0.001);
        assertEquals(angleFinAttendu, secteur.getAngleFin(), 0.001);
    }

    @Test
    public void testDescription() {
        String verif = "Secteur centre="+(int)secteur.centre().x()+","+(int)secteur.centre().y()+" Angle="+(double)secteur.getAngle()+" Arc="+(secteur.getAngleFin()-secteur.getAngleDebut());
        assertEquals(verif,secteur.description(0));
    }
}