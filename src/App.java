import fr.univrennes.istic.l2gen.geometrie.Cercle;
import fr.univrennes.istic.l2gen.geometrie.Ellipse;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Ligne;
import fr.univrennes.istic.l2gen.geometrie.Polygone;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Secteur;
import fr.univrennes.istic.l2gen.geometrie.Texte;
import fr.univrennes.istic.l2gen.geometrie.Triangle;
import java.io.FileWriter;


public class App {
    public static void main(String[] args) throws Exception {

        Groupe tableau = new Groupe();
tableau.ajoutGroupe(new Cercle(256, 256, 128).colorier("blue"));
tableau.ajoutGroupe(new Ellipse(256, 256, 128, 64).colorier("black"));
tableau.ajoutGroupe(new Ligne(128, 128, 128, 256, 256, 128, 256, 256).colorier("yellow"));
tableau.ajoutGroupe(new Polygone(128, 128, 128, 256, 256, 128, 256, 256).colorier("green"));
tableau.ajoutGroupe(new Rectangle(256, 256, 256, 128).colorier("white"));
tableau.ajoutGroupe(new Secteur(256, 256, 128, 0, 60).colorier("red"));
tableau.ajoutGroupe(new Triangle(192, 128, 256, 128, 256, 256).colorier("pink"));
System.out.println(tableau.enSVG());

        
    }

    // Méthode pour créer un arbre avec des groupes et des ellipses
    public static Groupe arbre(IForme figure) {
        Groupe groupe = new Groupe();
        groupe.ajoutGroupe(figure);

        IForme mini = figure.dupliquer();
        mini.redimensionner(0.5, 0.5);
        groupe.ajoutGroupe(mini);


        IForme minigroupe = groupe.dupliquer();
        minigroupe.redimensionner(0.25, 0.25);
        groupe.ajoutGroupe(minigroupe);
        return groupe;
    }
}
