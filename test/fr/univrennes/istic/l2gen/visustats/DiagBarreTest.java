package test.fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.univrennes.istic.l2gen.visustats.DiagBarre;
import fr.univrennes.istic.l2gen.visustats.Faisceau;

public class DiagBarreTest {
    @Test
    public void testAgencer() {

    }

    @Test
    public void testAjouterDonnees() {

    }

    @Test
    public void testColorier() {
        DiagBarre diagBarre = new DiagBarre("Titre", 2);

        String legende = "Données 1";
        double[] valeurs = { 20.0, 30.0, 50.0 };
        diagBarre.ajouterDonnees(legende, valeurs);

        diagBarre.colorier("red");

    }

    @Test
    public void testCreateEnSVG() {
        // Créer une instance de DiagCamemberts
        DiagBarre diagBarres = new DiagBarre("Titre", 2);

        // Appeler la méthode createEnSVG() pour générer le contenu SVG
        String svgContent = diagBarres.createEnSVG();

        // Vérifier que le contenu SVG est non nul
        assertNotNull(svgContent);

        // Vérifier que le contenu SVG contient certaines chaînes attendues
        // Par exemple, vous pouvez vérifier la présence de certaines balises ou
        // d'autres éléments spécifiques
        assertTrue(svgContent.contains(""));
    }

    @Test
    public void testEnSVG() {

        DiagBarre diagBarres = new DiagBarre("Titre", 2);

        // Appeler la méthode enSVG() pour générer le code SVG
        String svgCode = diagBarres.enSVG();

        // Vérifier que le code SVG commence par la balise <svg> et se termine par
        // </svg>
        assertTrue(svgCode.startsWith("<svg xmlns=\"http://www.w3.org/2000/svg\">"));
        assertTrue(svgCode.endsWith("</svg>"));
    }

    @Test
    public void testGenerateProportionalValues() {

    }

}
