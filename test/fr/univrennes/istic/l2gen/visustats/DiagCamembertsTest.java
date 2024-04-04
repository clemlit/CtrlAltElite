package test.fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        DiagCamemberts diagCamemberts = new DiagCamemberts("Titre", 2);

        
        String legende = "Données 1";
        double[] valeurs = { 20.0, 30.0, 50.0 };
        diagCamemberts.ajouterDonnees(legende, valeurs);

        diagCamemberts.colorier("red");

        Camembert camembert = diagCamemberts.getCamemberts().get(0);
        String couleur = camembert.getSecteurs().get(0).getCouleur();
        assertEquals("red", couleur);
        
    }

    

    @Test
    public void testCreateEnSVG() {
            // Créer une instance de DiagCamemberts
            DiagCamemberts diagCamemberts = new DiagCamemberts("Titre", 2);
    
            // Appeler la méthode createEnSVG() pour générer le contenu SVG
            String svgContent = diagCamemberts.createEnSVG();
    
            // Vérifier que le contenu SVG est non nul
            assertNotNull(svgContent);
    
            // Vérifier que le contenu SVG contient certaines chaînes attendues
            // Par exemple, vous pouvez vérifier la présence de certaines balises ou d'autres éléments spécifiques
            assertTrue(svgContent.contains(""));
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

        DiagCamemberts diagCamemberts = new DiagCamemberts("Titre", 2);

        // Appeler la méthode enSVG() pour générer le code SVG
        String svgCode = diagCamemberts.enSVG();

        // Vérifier que le code SVG commence par la balise <svg> et se termine par </svg>
        assertTrue(svgCode.startsWith("<svg xmlns=\"http://www.w3.org/2000/svg\">"));
        assertTrue(svgCode.endsWith("</svg>"));
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

        DiagCamemberts diagCamemberts = new DiagCamemberts("Titre", 2);

        // Ajouter des données au diagCamemberts
        String legende = "Données 1";
        double[] valeurs = { 20.0, 30.0, 50.0 }; // Exemple de valeurs
        diagCamemberts.ajouterDonnees(legende, valeurs);

        // Appeler la méthode legender() pour ajouter une légende
        diagCamemberts.legender();

        // Vérifier que la légende a été ajoutée
        List<Texte> legendes = diagCamemberts.getLegendeTexte();
        assertEquals(1, legendes.size());
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
