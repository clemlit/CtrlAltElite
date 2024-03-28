package test.fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.Texte;
import fr.univrennes.istic.l2gen.visustats.Camembert;
import fr.univrennes.istic.l2gen.visustats.DiagCamemberts;
import fr.univrennes.istic.l2gen.visustats.IDataVisualiseur;

public class DiagCamembertsTest {
    @Test
    public void testAgencer() {

    }

    @Test
    public void testAjouterDonnees() {
        DiagCamemberts diagCamemberts = new DiagCamemberts(null, 0);

        String legende = "Données 1";
        double[] valeurs = { 20.0, 30.0, 50.0 }; // Exemple de valeurs

        IDataVisualiseur result = diagCamemberts.ajouterDonnees(legende, valeurs);

        assertNotNull(result);

        List<Camembert> camemberts = diagCamemberts.getCamemberts();
        List<Texte> legendes = diagCamemberts.getLegendeTexte();

        assertEquals(1, camemberts.size());
        assertEquals(1, legendes.size());

        Camembert nouveauCamembert = camemberts.get(0);
        assertEquals(3, nouveauCamembert.getSecteurs().size());
        Texte texteLegende = legendes.get(0);
        assertEquals("Données 1", texteLegende.getTexte());
    }

    @Test
    public void testCentre() {
        // Déjà testé dans CamembertTest.java

    }

    @Test
    public void testColorier() {

    }

    @Test
    public void testCreateEnSVG() {

    }

    @Test
    public void testDeplacer() {
        // Déjà testé dans CamembertTest.java

    }

    @Test
    public void testDescription() {
        // Déjà testé dans CamembertTest.java

    }

    @Test
    public void testDupliquer() {
        // Déjà testé dans CamembertTest.java
    }

    @Test
    public void testEnSVG() {

    }

    @Test
    public void testHauteur() {
        // Déjà testé dans CamembertTest.java

    }

    @Test
    public void testLargeur() {
        // Déjà testé dans CamembertTest.java

    }

    @Test
    public void testLegender() {

    }

    @Test
    public void testRedimensionner() {
        // Déjà testé dans CamembertTest.java

    }

    @Test
    public void testTourner() {
        // Déjà testé dans CamembertTest.java

    }
}
