import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;

public class Affichage9 implements ClickReporter {
  // private Distributeur dist = new Distributeur();

  private InterfaceKlondike ik;
  Carte dos;
  boolean columnSelected = false;
  boolean listCardsSelected = false;
  Carte lastCardofList;
  ArrayList<Carte> listeDesCartesSelectionnees = new ArrayList<>();
  Plateau plat;
  Carte carteSelectionnee;
  Scanner scan;
  private boolean[] tasHighlighted = new boolean[13];

  public Affichage9() {
    scan = new Scanner(System.in);
    this.init();

  }

  private void init() {
    this.dos = new Carte(null, null);
    ik = new InterfaceKlondike(this); // this implements ClickReporter
    ik.addButton("Pile de carte");
    ik.addButton("Rejouer");
    ik.addButton("Abandonner");
    plat = new Plateau();

    ik.addCard(dos, 0);
    updateInterfaceByBack();
  }

  @Override
  public void reportClick(int ident) {
    this.afficheUneCarteEnPlus(ident);
    if (ident != 0 && !columnSelected) {
      for (int i = 0; i < tasHighlighted.length; i++) {
        if (tasHighlighted[i] && i != ident) {
          ik.setHighlighted(i, false);
          tasHighlighted[i] = false;
          return;
        }
      }
      ik.clearMessage();
      tasHighlighted[ident] = !tasHighlighted[ident];
      ik.setHighlighted(ident, tasHighlighted[ident]);
      columnSelected = true;

    } else {
      ik.clearMessage();
      columnSelected = false;
      this.resetHighlight();
    }
  }

  private void resetHighlight() {
    for (int i = 0; i < tasHighlighted.length; i++) {
      if (tasHighlighted[i]) {
        ik.setHighlighted(i, false);
        tasHighlighted[i] = false;
      }
    }
  }

  private void afficheUneCarteEnPlus(int ident) {
    switch (ident) {
      case 0:
        if (plat.getPioche().getLongueurPaquet() == 0 && plat.getPioche().getDefausse().getLongueurPaquet() == 0) {
          ik.setMessage("La pioche et la défausse sont vides");
        }
        plat.getPioche().pullCardAndAddDefausse();
        this.resetHighlight();
        break;
      case 1:
        if (!columnSelected)
          carteSelectionnee = plat.getPioche().getDefausse().getSommetCard();
        break;
      case 2:
      case 3:
      case 4:
      case 5:
        if (!columnSelected) {
          carteSelectionnee = plat.getPieux(ident - 2).getSommetCard();
        } else {
          for (int i = 0; i < tasHighlighted.length; i++) {
            if (tasHighlighted[i]) {
              if (i == ident) {
                ik.setMessage("Impossible de déplacer une carte sur elle-même !");
                ik.setHighlighted(i, false);
                tasHighlighted[i] = false;
                continue;
              }
              if (this.peutDeplacerDansLePieux(carteSelectionnee, ident)) {
                if (i == 1) {
                  carteSelectionnee = plat.getPioche().getDefausse().pullCard();
                } else if (i >= 2 && i <= 5) {

                  carteSelectionnee = plat.getPieux(i - 2).pullCard();

                } else if (i >= 6 && i <= 12) {
                  Colonne source = plat.getColonne(i - 6);
                  carteSelectionnee = source.pullCardColonneVisible();
                  // Retourner une carte cachée si nécessaire
                  if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
                    source.updateColonneVisible();
                  }
                }
                ik.setHighlighted(i, false);
                tasHighlighted[i] = false;
                plat.getPieux(ident - 2).addCard(carteSelectionnee);
              }
              if (this.estVictoire()) {
                System.out.println("VICTOIRE !");
                JOptionPane.showMessageDialog(null, "Félicitations ! Vous avez gagné la partie !");

              }
            }
          }
        }

        break;
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
        if (!columnSelected) {
          Colonne col = plat.getColonne(ident - 6);
          if (!col.estColonneVisibleVide()) {
            carteSelectionnee = col.getCarteVisibleAuSommet();
          } else {
            ik.setMessage("Cette colonne est vide !");
          }
        } else if (this.peutDeplacerUneCarteDansLaColonne(carteSelectionnee, ident) && !listCardsSelected) {
          for (int i = 0; i < tasHighlighted.length; i++) {
            if (tasHighlighted[i]) {

              if (i == ident) {
                ik.setMessage("Impossible de déplacer une carte sur elle-même !");
                ik.setHighlighted(i, false);
                tasHighlighted[i] = false;
                continue;
              }

              if (i == 1) {
                carteSelectionnee = plat.getPioche().getDefausse().pullCard();
              } else if (i >= 2 && i <= 5) {
                carteSelectionnee = plat.getPieux(i - 2).pullCard();

              } else if (i >= 6 && i <= 12) {
                Colonne source = plat.getColonne(i - 6);
                carteSelectionnee = source.pullCardColonneVisible();
                // Retourner une carte cachée si nécessaire
                if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
                  source.updateColonneVisible();
                }
              }
              ik.setHighlighted(i, false);
              tasHighlighted[i] = false;
              plat.getColonne(ident - 6).addCard(carteSelectionnee);
            }
          }
        } else if (listCardsSelected) {
          boolean memeColonne = false;
          for (int i = 0; i < tasHighlighted.length; i++) {
            if (tasHighlighted[i]) {
              if (i == ident) {

                // ik.setMessage("Impossible de déplacer une carte sur elle-même !");
                ik.setHighlighted(i, false);
                tasHighlighted[i] = false;
                memeColonne = true;
                listeDesCartesSelectionnees.clear();
                continue;
              }
              if (memeColonne) {
                ik.setMessage("Impossible de déplacer sur la même colonne !");
                return; // ← Ou ne rien faire
              }
              if (this.peutDeplacerUneListeDansLaColonne(listeDesCartesSelectionnees, ident)) {
                Colonne source = plat.getColonne(i - 6);
                List<Carte> cardToMove = new ArrayList<>();

                for (int j = 0; j < listeDesCartesSelectionnees.size(); j++) {
                  cardToMove.add(source.pullCardColonneVisible());
                }
                System.out.println("Cartes à déplacer : " + (ident - 6));
                System.out.println("cardToMove : " + cardToMove);
                Colonne destination = plat.getColonne(ident - 6);
                destination.addListCard(cardToMove);
                listCardsSelected = false;
                listeDesCartesSelectionnees.clear();
                ik.setHighlighted(i, false);
                tasHighlighted[i] = false;
                if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
                  source.updateColonneVisible();
                }
              } else {
                ik.setMessage("Déplacement invalide !");
              }

            }
          }
        }

        break;
      default:
        break;
    }

    updateInterfaceByBack();
  }

  private boolean peutDeplacerDansLePieux(Carte card, int ident) {
    if (columnSelected) {
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
    if (columnSelected) {
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

  private void updateInterfaceByBack() {
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

    for (int i = 0; i < plat.getNombrePieux(); i++) { // Affiche les cartes dans les pieux
      Carte carte = plat.getPieux(i).getSommetCard();
      if (carte != null) {
        ik.setCard(carte, i + 2);
      } else {
        ik.clear(i + 2);
      }
    }
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
    Colonne source = null; // ← Initialiser à null
    for (int i = 0; i < tasHighlighted.length; i++) {
      if (tasHighlighted[i]) {
        source = plat.getColonne(i - 6);
      }
    }

    String listeDesCartes = "";
    if (source != null) { // ← Vérifier que source existe
      System.out.println("Sélectionnez une carte parmi les suivantes :");
      for (int i = 0; i < source.getTailleColonneVisible(); i++) {
        listeDesCartes += i + " : =>  " + source.getCarteVisibleAt(i) + "\n";
      }
    }

    String input = JOptionPane.showInputDialog(
        null,
        "Sélectionnez un index " + "\n" + listeDesCartes,
        "Choix de carte",
        JOptionPane.QUESTION_MESSAGE);

    if (input == null || input.isEmpty()) { // si champs vide ou annulation
      ik.setMessage("Aucun index sélectionné.");
      columnSelected = false;
      this.resetHighlight();
      return;
    }
    try {
      int choix = Integer.parseInt(input);
      if (choix < 0 || choix >= source.getTailleColonneVisible()) {
        ik.setMessage("Index hors limites. Veuillez choisir un index valide.");
        columnSelected = false;
        this.resetHighlight();
        return;
      }
      System.out.println(choix + " : -> " + source.getCarteVisibleAt(choix));
      lastCardofList = source.getCarteVisibleAt(choix);
      listCardsSelected = true;

      for (int i = 0; i <= choix; i++) {
        listeDesCartesSelectionnees.add(source.getCarteVisibleAt(i));
      }
    } catch (NumberFormatException e) {
      ik.setMessage("Entrée invalide. Veuillez entrer un nombre valide.");
      columnSelected = false;
      this.resetHighlight();
      return;
    }

  }

  @Override
  public void buttonPressed(String identifier) {
    // TODO a supprimer apres les test
    if (identifier.equals("Rejouer")) {
      ik.close();
      ik.setMessage("Rejouer la partie");
      this.init();
    } else if (identifier.equals("Abandonner")) {
      ik.setMessage("Abandonner la partie");
      ik.close();
    } else if (identifier.equals("Pile de carte")) {

      if (!columnSelected) {
        ik.setMessage("Veuillez jusqu'a quelle carte prendre ");
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
