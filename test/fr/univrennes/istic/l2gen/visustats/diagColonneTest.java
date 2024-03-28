package test.fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.univrennes.istic.l2gen.visustats.DiagColonnes;
import fr.univrennes.istic.l2gen.visustats.Faisceau;

public class diagColonneTest {

    @Test
    public void testEnSVG() {
        DiagColonnes test = new DiagColonnes("test", 0);
        String svgReprésentation = test.createEnSVG();
        assertNotNull(svgReprésentation);
    }

    @Test
    public void testAjouterDonnees() {
        DiagColonnes test = new DiagColonnes("rien", 0);

    }

}
