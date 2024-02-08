import fr.univrennes.istic.l2gen.geometrie.Cercle;
import fr.univrennes.istic.l2gen.geometrie.Ellipse;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Ligne;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Triangle;

public class App {
    public static void main(String[] args) throws Exception {
        //Groupe arbre = arbre(new Ellipse(256, 256, 128, 64));
        //System.out.println(arbre.description(1));

        Groupe tableau = new Groupe();
        tableau.ajoutGroupe(new Cercle(256, 256, 128));
        tableau.ajoutGroupe(new Ellipse(256, 256, 128, 64));
        tableau.ajoutGroupe(new Ligne(128,128,128,256,256,128,256,256));
        tableau.ajoutGroupe(new Rectangle(256,256,256,128));
        tableau.ajoutGroupe(new Triangle(192,128,256,128,256,256));
        System.out.println(tableau.description(1));
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
