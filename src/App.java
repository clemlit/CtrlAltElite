import fr.univrennes.istic.l2gen.geometrie.Cercle;
import fr.univrennes.istic.l2gen.geometrie.Ellipse;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Ligne;
import fr.univrennes.istic.l2gen.geometrie.Polygone;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Secteur;
import fr.univrennes.istic.l2gen.geometrie.Triangle;
import java.io.FileWriter;


public class App {
    public static void main(String[] args) throws Exception {
        Groupe tableau = new Groupe();

        tableau.ajoutGroupe(new Triangle(192 , 128 , 256 , 128 , 256 , 256l));

        System.out.println(tableau.enSVG());

        String svgContent = "<svg xmlns=\"http://www.w3.org/2000/svg\">\n" +tableau.enSVG() +"</svg>";
        FileWriter writer = new FileWriter("figure.svg");

        writer.write(svgContent);
        writer.close();

        System.out.println("Le fichier Cercle.svg a été créé avec succès.");
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
