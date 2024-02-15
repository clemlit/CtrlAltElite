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

        Groupe arbre = arbre(new Rectangle(256, 256, 256, 128));
        arbre.coloriser("Red", "Green", "Orange", "Orange");
        System.out.println(arbre.description(0));

        String svgContent = "<svg xmlns=\"http://www.w3.org/2000/svg\">\n" + arbre.enSVG() + "</svg>";
        FileWriter writer = new FileWriter("rectangle.svg");

        writer.write(svgContent);
        writer.close();

        System.out.println("Le fichier .svg a été créé avec succès.");

        IForme f = new Texte (192,128,64," Istic L2GEN ");
        System.out.println(f.enSVG());
        f.deplacer(50, 50);
        System.out.println(f.enSVG());

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
