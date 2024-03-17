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
import fr.univrennes.istic.l2gen.visustats.Camembert;
import fr.univrennes.istic.l2gen.visustats.DiagCamemberts;
import fr.univrennes.istic.l2gen.visustats.DiagColonnes;
import fr.univrennes.istic.l2gen.visustats.Faisceau;

import java.io.FileWriter;


public class App {
    public static void main(String[] args) throws Exception {   

        //AFFICHAGE FAISCEAU H
        DiagColonnes visualiseurfh = new DiagColonnes("Emissions de CO2 (en Mt)", 5);
        visualiseurfh.legender("Afrique", "Amerique", "Asie", "Europe", "Oceanie");
        visualiseurfh.ajouterDonnees("2010", 1600, 6800, 16000, 4300, 300);
        visualiseurfh.ajouterDonnees("2015", 1900, 6600, 17500, 3800, 330);
        visualiseurfh.ajouterDonnees("2020", 2100, 6200, 17800, 3600, 340);
        visualiseurfh.colorier("Blue", "Green", "Red", "Yellow", "Maroon");
        FileWriter writer00 = new FileWriter("DiagrammeColonnes.svg");
        writer00.write(visualiseurfh.agencer().enSVG());
        writer00.close();

        //AFFICHAGE CAMEMBERT
        DiagCamemberts visualiseur = new DiagCamemberts("Emissions de CO2 (en Mt)", 5);
        visualiseur.legender("Afrique", "Amerique", "Asie", "Europe", "Oceanie");
        visualiseur.ajouterDonnees("2010", 1600, 6800, 16000, 4300, 300);
        visualiseur.ajouterDonnees("2015", 1900, 6600, 17500, 3800, 330);
        visualiseur.ajouterDonnees("2020", 2100, 6200, 17800, 3600, 340);
        visualiseur.colorier("Blue", "Green", "Red", "Yellow", "Maroon");
        FileWriter writer0 = new FileWriter("DiagrammeCamembert.svg");
        writer0.write(visualiseur.agencer().enSVG());
        writer0.close();              


        //AFFICHAGE DANS LE SVG
        //AFFICHAGE CAMEMBERT
        Camembert ca = new Camembert(210, 210, 100);
        ca.ajouterSecteur("red", 0.15); 
        ca.ajouterSecteur("blue", 0.40); 
        ca.ajouterSecteur("Yellow", 0.45);
        ca.redimensionner(2, 2);
        ca.deplacer(100, 100);
        String camembertSVG = ca.createEnSVG(); 
        FileWriter writer1 = new FileWriter("camembert.svg");
        writer1.write(camembertSVG);
        writer1.close();


        // AFFICHAGE CERCLE
        Groupe cerclGroupe = arbre(new Cercle(256, 256, 128));   
        cerclGroupe.tourner(10).colorier("Blue", "Red");
        String cercleSVG = cerclGroupe.createEnSVG();
        FileWriter writer2 = new FileWriter("Cercle.svg");
        writer2.write(cercleSVG);
        writer2.close();
        

        // AFFICHAGE Ligne
        Groupe ligneGroupe = arbre(new Ligne(128, 128, 128, 256, 256, 128, 256, 256));
        ligneGroupe.tourner(10).colorier("Blue", "Red", "Green");;
        String ligneSVG = ligneGroupe.createEnSVG();
        FileWriter writer3 = new FileWriter("Ligne.svg");
        writer3.write(ligneSVG);
        writer3.close();

        // AFFICHAGE Rectangle
        Groupe rectangleGroup = arbre(new Rectangle(256, 256, 256, 128));
        rectangleGroup.tourner(10).colorier("Blue", "Red", "Pink", "Yellow");;
        String rectangleSVG = rectangleGroup.createEnSVG();
        FileWriter writer4 = new FileWriter("Rectangle.svg");
        writer4.write(rectangleSVG);
        writer4.close();

        // AFFICHAGE Triangle
        Groupe triangleGroupe = arbre(new Triangle(192, 128, 256, 128, 256, 256));
        triangleGroupe.tourner(10).colorier("Pink", "Green");;
        String triangleSVG = triangleGroupe.createEnSVG();
        FileWriter writer5 = new FileWriter("Triangle.svg");
        writer5.write(triangleSVG);
        writer5.close();

        // AFFICHAGE Ellipse
        Groupe ellipseGroupe = arbre(new Ellipse(256, 256, 128, 64));
        ellipseGroupe.tourner(10).colorier("Orange", "Red", "Green", "Brown");;
        String ellipseSVG = ellipseGroupe.createEnSVG();
        FileWriter writer6 = new FileWriter("Ellipse.svg");
        writer6.write(ellipseSVG);
        writer6.close();

        // AFFICHAGE Polygone
        Groupe polygoneGroupe = arbre(new Polygone(128, 128, 128, 256, 256, 128, 256, 256));
        polygoneGroupe.tourner(10).colorier("Green", "Red", "Purple", "Orange");;
        String polygoneSVG = polygoneGroupe.createEnSVG();
        FileWriter writer7 = new FileWriter("Polygone.svg");
        writer7.write(polygoneSVG);
        writer7.close();

        // AFFICHAGE Secteur
        Groupe secteurGroup = arbre(new Secteur(256, 256, 128, 0, 60));
        secteurGroup.tourner(10).colorier("Green", "Red", "Orange");;
        String secteurSVG = secteurGroup.createEnSVG();
        FileWriter writer8 = new FileWriter("Secteur.svg");
        writer8.write(secteurSVG);
        writer8.close();


        // AFFICHAGE Figure

        Groupe tableau = new Groupe();
        tableau.ajoutGroupe(new Cercle(256, 256, 128));
        tableau.ajoutGroupe(new Ellipse(256, 256, 128, 64));
        tableau.ajoutGroupe(new Ligne(128, 128, 128, 256, 256, 128, 256, 256));
        tableau.ajoutGroupe(new Polygone(128, 128, 128, 256, 256, 128, 256, 256));
        tableau.ajoutGroupe(new Rectangle(256, 256, 256, 128));
        tableau.ajoutGroupe(new Secteur(256, 256, 128, 0, 60));
        tableau.ajoutGroupe(new Triangle(192, 128, 256, 128, 256, 256));
        System.out.println(tableau.enSVG());
        tableau.colorier("Red", "Yellow", "Blue", "Pink", "Purple", "Green", "Orange");
        tableau.createEnSVG();

        // AFFICHAGE Faisceau

            Faisceau fh = new Faisceau(" Exemple de Faisceau horizontal ", 100, 200, 500);
            fh.colorier(" blue ", " red ", " green ");
            fh.agencer(20, 250, 100, 0.2, false);

        String svgFaisceau = fh.createEnSVG();
        FileWriter write = new FileWriter("Faisceau.svg");
        write.write(svgFaisceau);
        write.close();

        System.out.println("---------------------------------------");

        Faisceau fg = new Faisceau ( " Exemple de Faisceau vertical " , 100 , 200 , 500);
        fg . colorier ( " cyan " ," purple " ," yellow " );
        fg . agencer (20 , 250 , 100 , 0.2 , true );
        String svgFaisceau2 = fg.createEnSVG();


        FileWriter write2 = new FileWriter("Faisceau2.svg");
        write2.write(svgFaisceau2);
        write2.close();

        System.out.println("---------------------------------------");

        String tableauSVG = tableau.createEnSVG(); // Use createEnSVG() method for the group

        FileWriter writer = new FileWriter("figure.svg");
        writer.write(tableauSVG);
        writer.close();

        System.out.println("Les fichiers .svg ont été créés avec succès.");

        IForme f = new Texte(192, 128, 64, " Istic L2GEN ");
        System.out.println(f.enSVG());
        f.deplacer(50, 50);
        System.out.println(f.enSVG());

        IForme ce = new Ligne(50, 50);
        System.out.println(ce.enSVG());
        ce.redimensionner(2, 2).deplacer(20, 20);
        System.out.println(ce.enSVG());


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
