import java.util.Scanner;
import java.util.Random;
import javax.swing.JButton;

import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;

public class Affichage9 implements ClickReporter {
  // private Distributeur dist = new Distributeur();

  private InterfaceKlondike ik;
  Carte dos;
  boolean found = false;
  Plateau plat;
  Carte carteSelectionnee;
  private boolean[] tasHighlighted = new boolean[13];

  public Affichage9() {
    this.init();

  }

  private void init() {
    this.dos = new Carte(null, null);
    ik = new InterfaceKlondike(this); // this implements ClickReporter
    ik.addButton("Rejouer");
    ik.addButton("Abandonner");
    plat = new Plateau();
    Scanner scan = new Scanner(System.in);

    ik.addCard(dos, 0);
    int nombreDeColonne = plat.getNombreColonnes();
    for (int i = 1; i < nombreDeColonne; i++) {
      // System.out.println("colonnes" + plat.getColonne(i));

      for (int j = 1; j <= i; j++) {
        ik.addCard(dos, i + 6);
      }
    }

    for (int i = 0; i < 7; i++) {
      Carte carteVisible = plat.getColonne(i).getCarteVisibleAuSommet();
      if (carteVisible != null) {
        ik.addCard(carteVisible, i + 6);
      }
    }
  }

  @Override
  public void reportClick(int ident) {
    found = false;
    for (int i = 0; i < tasHighlighted.length; i++) {
      if (tasHighlighted[i] && i != ident) {
        ik.setMessage(" autre tas déjà surligné dans la zone de texte sur la colonne " + i);
        found = true;
      }
    }

    System.out.println("apres boucle" + found);
    afficheUneCarteEnPlus(ident);
    if (ident != 0 && !found) {
      ik.clearMessage();
      tasHighlighted[ident] = !tasHighlighted[ident];

      ik.setHighlighted(ident, tasHighlighted[ident]);

    }
  }

  private void afficheUneCarteEnPlus(int ident) {
    System.out.println("Affiche une carte en plus, ident: " + ident);
    if (ident == 0) {
      if (plat.getPioche().getLongueurPaquet() == 0 && plat.getPioche().getDefausse().getLongueurPaquet() == 0) {
        ik.setMessage("La pioche et la défausse sont vides");
      }
      Carte carteVisible = plat.getPioche().pullCardAndAddDefausse();
      if (carteVisible != null) {
        ik.addCard(carteVisible, 1);
      }
    }
    System.out.println("Carte selectionnee remise a null" + found);
    if (this.found) {
      carteSelectionnee = null;
      return;
    }
    switch (ident) {
      case 0:
        if (plat.getPioche().getLongueurPaquet() == 0 && plat.getPioche().getDefausse().getLongueurPaquet() == 0) {
          ik.setMessage("La pioche et la défausse sont vides");
        }
        Carte carteVisible = plat.getPioche().pullCardAndAddDefausse();
        if (carteVisible != null) {
          ik.addCard(carteVisible, 1);
        }
        break;
      case 1:
        if (!found)
          carteSelectionnee = plat.getPioche().getDefausse().getSommetCard();
        else
          ik.addCard(carteSelectionnee, ident);
        // System.out.println("Carte selectionnee dans la defausse: " + carteSelectionnee + "found : " + found);
        break;
      case 2:
        // ik.setHighlighted(ident, true);
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;
      case 6:
        if (!found)
          carteSelectionnee = plat.getColonne(ident-6).getCarteVisibleAuSommet();
        else
          ik.addCard(carteSelectionnee, ident);
        // ik.setHighlighted(ident, true);
        System.out.println("Colonne 6" + found);
        break;
      case 7:
           if (!found)
          carteSelectionnee = plat.getColonne(ident-6).getCarteVisibleAuSommet();
        else
          ik.addCard(carteSelectionnee, ident);
        // ik.setHighlighted(ident, true);
        break;
      case 8:
           if (!found)
          carteSelectionnee = plat.getColonne(ident-6).getCarteVisibleAuSommet();
        else
          ik.addCard(carteSelectionnee, ident);
        // ik.setHighlighted(ident, true);
        break;
      case 9:
           if (!found)
          carteSelectionnee = plat.getColonne(ident-6).getCarteVisibleAuSommet();
        else
          ik.addCard(carteSelectionnee, ident);
        // ik.setHighlighted(ident, true);
        break;
      case 10:
           if (!found)
          carteSelectionnee = plat.getColonne(ident-6).getCarteVisibleAuSommet();
        else
          ik.addCard(carteSelectionnee, ident);
        // ik.setHighlighted(ident, true);
        break;
      case 11:
           if (!found)
          carteSelectionnee = plat.getColonne(ident-6).getCarteVisibleAuSommet();
        else
          ik.addCard(carteSelectionnee, ident);
        // ik.setHighlighted(ident, true);
        break;
      case 12:
           if (!found)
          carteSelectionnee = plat.getColonne(ident-6).getCarteVisibleAuSommet();
        else
          ik.addCard(carteSelectionnee, ident);
        // ik.setHighlighted(ident, true);
        break;
      default:
        break;
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
    }

  }

  public static void main(String[] args) {
    new Affichage9();
  }
}
