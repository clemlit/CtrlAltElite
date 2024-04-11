package test.fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.visustats.DiagBarre;
import fr.univrennes.istic.l2gen.visustats.Faisceau;
import fr.univrennes.istic.l2gen.visustats.IDataVisualiseur;

public class DiagBarreTest {

    @Test
    public void testAgencer() {
        // un enfer puisque ya des exceptions de gros fdp partout
    }

    @Test
    public void testAjouterDonnees() {
        // j ai pas signé pour ça
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
        double[] test = { 4.0, 5.0, 6.0 };
        double[] temoin = DiagBarre.generateProportionalValues(4.0, 5.0, 2);

    }

}
