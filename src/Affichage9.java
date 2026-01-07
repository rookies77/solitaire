import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JButton;

import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;

public class Affichage9 implements ClickReporter {
  // private Distributeur dist = new Distributeur();

  private InterfaceKlondike ik;
  Carte dos;
  boolean columnSelected = false;
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
    Scanner scan = new Scanner(System.in);

    ik.addCard(dos, 0);
    updateInterfaceByBack();
  }

  @Override
  public void reportClick(int ident) {
    // columnSelected = false;

    System.out.println("tasHighlighted 1: " + Arrays.toString(tasHighlighted));
    System.out.println("ident clicked: " + ident);
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
    System.out.println("tasHighlighted 2: " + Arrays.toString(tasHighlighted));
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
            System.out.println("Carte selectionnee  " + carteSelectionnee + " columnSelected : " + columnSelected);
          } else {
            ik.setMessage("Cette colonne est vide !");
          }
        } else if (this.peutDeplacerDansLaColonne(carteSelectionnee, ident)) {
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

  private boolean peutDeplacerDansLaColonne(Carte card, int ident) {
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
  }

  private void prendreUneListeDeCarte(String message) {
    ik.setMessage(message);
    Colonne source = null; // ← Initialiser à null
    for (int i = 0; i < tasHighlighted.length; i++) {
      if (tasHighlighted[i]) {
        source = plat.getColonne(i - 6);
        System.out.println("Déplacement de la carte choisie vers le tas " + source);
        System.out.println("Déplacement de la carte choisie vers le tas " + source.getColonneVisibleInterne());
      }
    }

    if (source != null) { // ← Vérifier que source existe
      System.out.println("Sélectionnez une carte parmi les suivantes :");
      for (int i = 0; i < source.getTailleColonneVisible(); i++) {
        System.out.println((i ) + " : -> " + source.getCarteVisibleAt(i));
      }
    }
    int choix = scan.nextInt(); 

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
        ik.setMessage("Veuillez selectionner une colonne avant ");
        return;
      }
      prendreUneListeDeCarte(
          "Choisir dans la console du terminal");

    }

  }

  public static void main(String[] args) {
    new Affichage9();
  }
}
