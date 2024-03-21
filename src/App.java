import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.visustats.*;
import java.io.FileWriter;

public class App {
    public static void main(String[] args) throws Exception {   

        // AFFICHAGE FAISCEAU BARRE
        DiagBarre visualiseurfv = new DiagBarre("Emissions de CO2 (en Mt)", 5);
        visualiseurfv.legender("Afrique", "Amerique", "Asie", "Europe", "Oceanie");
        visualiseurfv.ajouterDonnees("2010", 1600, 6800, 16000, 4300, 300);
        visualiseurfv.ajouterDonnees("2015", 1900, 6600, 17500, 3800, 330);
        visualiseurfv.ajouterDonnees("2020", 2100, 6200, 17800, 3600, 340);
        visualiseurfv.colorier("Blue", "Green", "Red", "Yellow", "Maroon");
        FileWriter writer01 = new FileWriter("DiagrammeBarres.svg");
        writer01.write(visualiseurfv.agencer().enSVG());
        writer01.close();

        //AFFICHAGE FAISCEAU COLONNE
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
