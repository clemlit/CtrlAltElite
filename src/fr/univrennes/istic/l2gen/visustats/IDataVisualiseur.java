package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.IForme;

public interface IDataVisualiseur extends IForme{
    public IDataVisualiseur agencer();

    public IDataVisualiseur ajouterDonnees(String donnees, double... x);

    public IDataVisualiseur legender(String... legendes);

    public IDataVisualiseur setOptions(String... options);
}
