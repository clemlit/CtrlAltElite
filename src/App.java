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
import fr.univrennes.istic.l2gen.geometrie.visustats.Camembert;

import java.io.FileWriter;


public class App {
    public static void main(String[] args) throws Exception {
        Camembert ca = new Camembert(210, 210, 100);
        ca.ajouterSecteur("red", 0.15); 
        ca.ajouterSecteur("blue", 0.40); 
        ca.ajouterSecteur("green", 0.45);
        ca.redimensionner(2, 2);
        ca.deplacer(100, 100);


        IForme f1 = new Texte(192, 128, 64, " Istic L2GEN ");   
        f1.colorier("Red").tourner(10);

        System.out.println(f1.enSVG());


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

        String svgContent = "<svg xmlns=\"http://www.w3.org/2000/svg\">\n" + ca.enSVG() + "</svg>";
        FileWriter writer = new FileWriter("Camembert.svg");

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

        Cercle c = new Cercle(1,1, 2);
        System.out.println(c.enSVG());
        c.deplacer(1,1).redimensionner(2, 2);
        System.out.println(c.enSVG());
        System.out.println();

        Ellipse e = new Ellipse(1, 1 , 4, 2);
        System.out.println(e.enSVG());
        e.deplacer(1, 1).redimensionner(2, 2);
        System.out.println(e.enSVG());

        Secteur sec = new Secteur(0, 0,1,2,2);
        System.out.println(sec.enSVG());
        sec.deplacer(1, 1).redimensionner(2, 2);
        System.out.println(sec.enSVG());

        Triangle t = new Triangle(2,1,1,2,3,1);
        System.out.println(t.enSVG());
        t.deplacer(1, 1).redimensionner(2, 2);
        System.out.println(t.enSVG());




        System.out.println("--------------------------");

        Rectangle rec = new Rectangle(100, 100, 200, 100);
        System.out.println("Rectangle avant rotation :\n" + rec.enSVG());

        // Rotation du rectangle de 45 degrés
        rec.tourner(45);
        System.out.println("Rectangle après rotation de 45 degrés :\n" + rec.enSVG());
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
