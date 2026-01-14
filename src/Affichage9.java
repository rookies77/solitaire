import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;

public class Affichage9 implements ClickReporter {
  private InterfaceKlondike ik;
  Carte dos;
  Carte lastCardofList;
  ArrayList<Carte> listeDesCartesSelectionnees = new ArrayList<>();
  Plateau plat;
  Carte carteSelectionnee;
  Scanner scan;
  private Integer indexDepart = null; // null = aucune sélection

  public Affichage9() {
    scan = new Scanner(System.in);
    this.init();

  }

  private void init() {
    this.dos = new Carte(null, null);
    ik = new InterfaceKlondike(this); // this implements ClickReporter
    ik.addButton("Déplacer plusieurs cartes");
    ik.addButton("Rejouer");
    ik.addButton("Abandonner");
    plat = new Plateau();
    miseAJourInterface();
  }

  @Override
  public void reportClick(int ident) {
    if (ident == 0) { // Cas spécial : clic sur la pioche
      plat.getPioche().pullCardAndAddDefausse();
      miseAJourInterface();
      return;
    }

    if (indexDepart == null) { // Premier clic : sélectionner le départ et récupérer la carte
      carteSelectionnee = lectureDeLaCarteDuTas(ident);
      if (carteSelectionnee == null) {
        ik.setMessage("Aucune carte à déplacer ici !");
        return;
      }
      indexDepart = ident;
      ik.setHighlighted(ident, true);
      ik.setMessage("Colonne sélectionnée, cliquez sur la destination");
    } else {
      if (indexDepart == ident) { // Annuler la sélection car le second clique est sur le meme tas
        ik.setHighlighted(indexDepart, false);
        indexDepart = null;
        carteSelectionnee = null;
        ik.setMessage("Sélection annulée");
      } else {

        this.PlaceUneOuPlusieursCarte(ident);// Second clic : effectue le déplacement vers un pieux ou une colonne
      }
    }
  }

  private Carte lectureDeLaCarteDuTas(int ident) {
    if (ident == 1) {
      return plat.getPioche().getDefausse().getSommetCard();
    } else if (ident >= 2 && ident <= 5) {
      return plat.getPieux(ident - 2).getSommetCard();
    } else if (ident >= 6 && ident <= 12) {
      Colonne col = plat.getColonne(ident - 6);
      if (!col.estColonneVisibleVide()) {
        return col.getCarteVisibleAuSommet();
      }
    }
    return null;
  }

  private void PlaceUneOuPlusieursCarte(int ident) {
    if (ident >= 2 && ident <= 5) {
      if (this.peutDeplacerDansLePieux(carteSelectionnee, ident)) {
        retirerCarteDeindexDepart();
        plat.getPieux(ident - 2).addCard(carteSelectionnee);
        ik.setMessage("Carte " + carteSelectionnee.getValeur() + " " + carteSelectionnee.getSymbole()
            + " déplacée vers le pieux n° : " + obtenirNomPieux(ident));
        if (this.estVictoire()) {
          JOptionPane.showMessageDialog(null, "Félicitations ! Vous avez gagné la partie !");
        }
      }
      ik.setHighlighted(indexDepart, false);
      indexDepart = null;
      carteSelectionnee = null;
    } else if (ident >= 6 && ident <= 12) {
      if (listeDesCartesSelectionnees.isEmpty()) {
        placeUneCarte(ident);
      } else {
        placePlusieursCartes(listeDesCartesSelectionnees, ident);
      }
    }
    miseAJourInterface();
  }

  private String obtenirNomPieux(int ident) {
    switch (ident) {
      case 2:
        return " A ";
      case 3:
        return " B ";
      case 4:
        return " C ";
      case 5:
        return " D ";
      default:
        return "";
    }
  }

  private void placeUneCarte(int ident) {
    // Déplacement d'une seule carte
    if (this.peutDeplacerUneCarteDansLaColonne(carteSelectionnee, ident)) {
      retirerCarteDeindexDepart();
      ik.setMessage("Carte " + carteSelectionnee.getValeur() + " " + carteSelectionnee.getSymbole()
          + " déplacée vers la colonne n° : " + (ident - 5));
      plat.getColonne(ident - 6).addCard(carteSelectionnee);
    }
    ik.setHighlighted(indexDepart, false);
    indexDepart = null;
    carteSelectionnee = null;
  }

  private void placePlusieursCartes(List<Carte> listeDesCartesSelectionnees, int ident) {
    if (this.peutDeplacerUneListeDansLaColonne(listeDesCartesSelectionnees, ident)) {
      Colonne source = plat.getColonne(indexDepart - 6);
      List<Carte> cardToMove = new ArrayList<>();
      for (int j = 0; j < listeDesCartesSelectionnees.size(); j++) {
        cardToMove.add(source.pullCardColonneVisible());
      }
      Colonne destination = plat.getColonne(ident - 6);
      destination.addListCard(cardToMove);
      if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
        source.updateColonneVisible();
      }
      ik.setMessage("Cartes déplacées !");
    } else {
      ik.setMessage("Déplacement invalide !");
    }
    listeDesCartesSelectionnees.clear();
    ik.setHighlighted(indexDepart, false);
    indexDepart = null;
    carteSelectionnee = null;

  }

  private void retirerCarteDeindexDepart() { // Retire la carte du tas de depart selctionné
    if (indexDepart == 1) {
      plat.getPioche().getDefausse().pullCard();
    } else if (indexDepart >= 2 && indexDepart <= 5) {
      plat.getPieux(indexDepart - 2).pullCard();
    } else if (indexDepart >= 6 && indexDepart <= 12) {
      Colonne source = plat.getColonne(indexDepart - 6);
      source.pullCardColonneVisible();
      if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
        source.updateColonneVisible();
      }
    }
  }

  private boolean peutDeplacerDansLePieux(Carte card, int ident) {
    if (indexDepart != null) {
      Pieux destination = plat.getPieux(ident - 2);
      if (destination.getLongueurPaquet() == 0) {
        if (card.getValeur() == Carte.valeurCarte.as) {
          return true;
        } else {
          ik.setMessage("Le pieux est vide, seule un as peut y être placé !");
          return false;
        }
      }
      if (destination.getLongueurPaquet() > 0) {
        Carte lastCard = destination.getSommetCard();
        if (lastCard.precedeMemeCouleur(card)) {
          return true;
        } else {
          ik.setMessage("La carte ne peut pas être placée sur le pieux !");
          return false;
        }
      }
    }
    return false;
  }

  private boolean peutDeplacerUneCarteDansLaColonne(Carte card, int ident) {
    if (indexDepart != null) {
      Colonne destination = plat.getColonne(ident - 6);
      // Vérifier si le déplacement est valide, mais NE PAS ajouter la carte ici
      if (!destination.estColonneVisibleVide()
          && card.estJusteEnDessousDe(destination.getCarteVisibleAuSommet())
          && card.getCouleur() != destination.getCarteVisibleAuSommet().getCouleur()) {
        return true; // Déplacement valide

      } else if (destination.estColonneVisibleVide() && card.getValeur() == Carte.valeurCarte.roi) {
        return true; // Déplacement valide (roi sur colonne vide)

      } else {
        ik.setMessage("Déplacement invalide !");
        return false; // Déplacement invalide
      }
    } else {
      return false;
    }

  }

  private boolean peutDeplacerUneListeDansLaColonne(List<Carte> listCard, int ident) {
    Colonne destination = plat.getColonne(ident - 6);
    return destination.canAddListCardFromColonne(listCard);
  }

  private void miseAJourInterface() {
    affichageDesColonnes();
    affichageDesPieux();
    affichageDesPieuxEtPioche();

  }

  private void affichageDesColonnes() {
    for (int i = 0; i < plat.getNombreColonnes(); i++) { // Affiche les cartes dans les colonnes
      int longueurDuPaquet = plat.getColonne(i).getLongueurPaquet();
      int tailleColonneVisible = plat.getColonne(i).getTailleColonneVisible();
      Carte[] cartes = new Carte[longueurDuPaquet + tailleColonneVisible];

      // Ajouter les cartes visibles
      for (int j = 0; j < tailleColonneVisible; j++) {
        cartes[j] = plat.getColonne(i).getCarteVisibleAt(j);
      }

      // Ajouter les cartes cachées (dos)
      for (int j = 0; j < longueurDuPaquet; j++) {
        cartes[tailleColonneVisible + j] = dos;
      }

      ik.setCards(cartes, i + 6);
    }
  }

  private void affichageDesPieux() {
    for (int i = 0; i < plat.getNombrePieux(); i++) { // Affiche les cartes dans les pieux
      Carte carte = plat.getPieux(i).getSommetCard();
      if (carte != null) {
        ik.setCard(carte, i + 2);
      } else {
        ik.clear(i + 2);
      }
    }
  }

  private void affichageDesPieuxEtPioche() {
    Carte card = plat.getPioche().getDefausse().getSommetCard(); // Affichage de la carte au sommet de la défausse

    if (card != null) {
      ik.setCard(card, 1);
    } else {
      ik.clear(1);
    }

    if (plat.getPioche().getLongueurPaquet() > 0) { // Affichage de la pioche
      ik.setCard(dos, 0);
    } else {
      ik.clear(0);
    }

  }

  private void prendreUneListeDeCarte(String message) {
    ik.setMessage(message);
    Colonne source = validerColonneSource();
    if (source == null) {
      return;
    }

    String listeDesCartes = construireListeCartes(source);
    String input = demanderIndexUtilisateur(listeDesCartes);
    
    if (input == null) {
      annulerSelection();
      return;
    }

    traiterChoixUtilisateur(input, source);
  }

  private Colonne validerColonneSource() {
    if (plat.getColonne(indexDepart - 6).getTailleColonneVisible() == 1) {
      ik.setMessage("Action invalide, \n il n'y a qu'une seule carte dans la colonne sélectionnée !");
      return null;
    }
    if (indexDepart != null && indexDepart >= 6 && indexDepart <= 12) {
      return plat.getColonne(indexDepart - 6);
    }
    ik.setMessage("Il faut d'abord sélectionner une colonne valide !");
    return null;
  }

  private String construireListeCartes(Colonne source) {
    String listeDesCartes = "";
    for (int i = 0; i < source.getTailleColonneVisible(); i++) {
      listeDesCartes += i + " : =>  " + source.getCarteVisibleAt(i) + "\n";
    }
    return listeDesCartes;
  }

  private String demanderIndexUtilisateur(String listeDesCartes) {
    String input = JOptionPane.showInputDialog(
        null,
        "Sélectionnez jusqu'où déplacer :\n" +
            "(Toutes les cartes du sommet jusqu'à celle choisie seront déplacées)\n" +
            "Entrez le numéro de la dernière carte à déplacer :\n\n" +
            listeDesCartes,
        "Déplacement de plusieurs cartes",
        JOptionPane.QUESTION_MESSAGE);

    if (input == null || input.isEmpty()) {
      ik.setMessage("Aucun index sélectionné.");
      return null;
    }
    return input;
  }

  private void traiterChoixUtilisateur(String input, Colonne source) {
    try {
      int choix = Integer.parseInt(input);
      if (choix < 0 || choix >= source.getTailleColonneVisible()) {
        ik.setMessage("Index hors limites. Veuillez choisir un index valide.");
        annulerSelection();
        return;
      }
      System.out.println(choix + " : -> " + source.getCarteVisibleAt(choix));
      lastCardofList = source.getCarteVisibleAt(choix);

      for (int i = 0; i <= choix; i++) {
        listeDesCartesSelectionnees.add(source.getCarteVisibleAt(i));
      }
    } catch (NumberFormatException e) {
      ik.setMessage("Entrée invalide. Veuillez entrer un nombre valide.");
      annulerSelection();
    }
  }

  private void annulerSelection() {
    if (indexDepart != null) {
      ik.setHighlighted(indexDepart, false);
    }
    indexDepart = null;
  }

  @Override
  public void buttonPressed(String identifier) {
    if (identifier.equals("Rejouer")) {
      ik.close();
      ik.setMessage("Rejouer la partie");
      this.init();
    } else if (identifier.equals("Abandonner")) {
      ik.setMessage("Abandonner la partie");
      ik.close();
    } else if (identifier.equals("Déplacer plusieurs cartes")) {

      if (indexDepart == null || indexDepart < 6 || indexDepart > 12) {
        ik.setMessage("Action invalide, Il faut sélectionner une colonne \n de départ avant de déplacer plusieurs cartes !");
        return;
      }
      prendreUneListeDeCarte(
          "- Choisir une carte par son index : " + "\n" + "- Puis cliquer sur la colonne de destination");

    }

  }

  private boolean estVictoire() {
    for (int i = 0; i < plat.getNombrePieux(); i++) {
      if (plat.getPieux(i).getLongueurPaquet() != (52 / 4)) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    new Affichage9();
  }
}
