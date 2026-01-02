package interfaceGraphique;

/**
 * Cette interface est le type des cartes utilisé par les méthodes de
 * la classe InterfaceKlondike.
 * 
 * Les noms de fichier des images de cartes doit correspondre aux
 * fichiers présents dans le dossier cards du projet.
 */
public interface ICarte{
    /**
     * Renvoie le nom du fichier correspondant à la carte. 
     * 
     * Si celle-ci est visible, le nom comporte la valeur suivie de
     * "_de_" suivie de la couleur suivie de ".png". Par exemple
     * "roi_de_coeur.png". Si la carte n'est pas visible, le nom de
     * fichier est "dos.png".
     * 
     * @return le nom d'un fichier d'image de carte
     */
    String getNomDeFichierPNG();
}