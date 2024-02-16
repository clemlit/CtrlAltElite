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

        Groupe arbre = arbre(new Rectangle (256 , 256 , 256 , 128));
        System.out.println(arbre.enSVG());
        System.out.println(arbre.description(0));

        System.out.println("-------------------------------------");


        Groupe tableau = new Groupe();
        tableau.ajoutGroupe(new Cercle(256, 256, 128));
        tableau.ajoutGroupe(new Ellipse(256, 256, 128, 64));
        tableau.ajoutGroupe(new Ligne(128, 128, 128, 256, 256, 128, 256, 256));
        tableau.ajoutGroupe(new Polygone(128, 128, 128, 256, 256, 128, 256, 256));
        tableau.ajoutGroupe(new Rectangle(256, 256, 256, 128));
        tableau.ajoutGroupe(new Secteur(256, 256, 128, 0, 60));
        tableau.ajoutGroupe(new Triangle(192, 128, 256, 128, 256, 256));
        System.out.println(tableau.enSVG());

        String svgContent = "<svg xmlns=\"http://www.w3.org/2000/svg\">\n" + tableau.enSVG() + "</svg>";
        FileWriter writer = new FileWriter("figure.svg");

        writer.write(svgContent);
        writer.close();

        System.out.println("Le fichier .svg a été créé avec succès.");

        IForme f = new Texte(192, 128, 64, " Istic L2GEN ");
        System.out.println(f.enSVG());
        f.deplacer(50, 50);
        System.out.println(f.enSVG());

        IForme ce = new Ligne(50, 50);
        System.out.println(ce.enSVG());
        ce.redimensionner(2, 2).deplacer(20, 20);
        System.out.println(ce.enSVG());

        Rectangle r = new Rectangle(1, 1, 100, 100);
        System.out.println(r.enSVG());
        r.deplacer(1, 1).redimensionner(2, 2);
        System.out.println(r.enSVG());
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
