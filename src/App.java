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

        IForme f = new Triangle(192, 128, 128, 256, 256, 256);
        System.out.println(f.description(1));
        
        Triangle triangle = new Triangle(192, 128, 128, 256, 256, 256);
        Groupe arbre = arbre(triangle);
        System.out.println(arbre.enSVG());

        String svgContent = "<svg xmlns=\"http://www.w3.org/2000/svg\">\n" +arbre.enSVG() +"</svg>";

        // Sauvegarder dans un fichier
        FileWriter writer = new FileWriter("Triangle.svg");
        writer.write(svgContent);
        writer.close();

        System.out.println("Le fichier Triangle.svg a été sauvegardé avec succès.");



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
