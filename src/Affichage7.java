import java.util.Scanner;
import java.util.Random;
import javax.swing.JButton;

import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;

public class Affichage7 implements ClickReporter {
  // private Distributeur dist = new Distributeur();

  private InterfaceKlondike ik;
  Carte dos;
  Carte carteTest;
  Plateau plat;
  private Random random = new Random();

  public Affichage7() {
    ik = new InterfaceKlondike(this);
    ik.addButton("carte");
    plat = new Plateau();
    Scanner scan = new Scanner(System.in);
    this.init();
    scan.nextLine();
    System.out.println("Bye");
    ik.close();
  }

  private void init() {
    this.dos = new Carte(null, null);
    this.carteTest = new Carte(Carte.valeurCarte.values()[random.nextInt(Carte.valeurCarte.values().length)],
        Carte.symboleCarte.coeur);

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
    ik.setMessage(" click reporté ==" + ident + "==");
    afficheUneCarteEnPlus(ident);
  }

  private void afficheUneCarteEnPlus(int ident) {

    if (ident == 0) {
      if (plat.getPioche().getLongueurPaquet() == 0 && plat.getPioche().getDefausse().getLongueurPaquet() == 0) {
        ik.setMessage("La pioche et la défausse sont vides");
      }
      Carte carteVisible = plat.getPioche().pullCardAndAddDefausse();
      if (carteVisible != null) {
        ik.addCard(carteVisible, 1);
      }
    }

    for (int i = 6; i <= 12; i++) { // retire le hightlight de toutes les colonnes
      ik.setHighlighted(i, false);
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
        break;
      case 2:
        ik.setHighlighted(ident, true);
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;
      case 6:
        ik.setHighlighted(ident, true);
        break;
      case 7:
        ik.setHighlighted(ident, true);
        break;
      case 8:
        ik.setHighlighted(ident, true);
        break;
      case 9:
        ik.setHighlighted(ident, true);
        break;
      case 10:
        ik.setHighlighted(ident, true);
        break;
      case 11:
        ik.setHighlighted(ident, true);
        break;
      case 12:
        ik.setHighlighted(ident, true);
        break;
      default:
        break;
    }
  }

  @Override
  public void buttonPressed(String identifier) {
    // TODO a supprimer apres les test
    this.carteTest = new Carte(Carte.valeurCarte.values()[random.nextInt(Carte.valeurCarte.values().length)],
        Carte.symboleCarte.values()[random.nextInt(Carte.symboleCarte.values().length)]);
    ik.setMessage(" bouton " + identifier + " pressed");
    ik.addCard(this.carteTest, 6);
  }

  public static void main(String[] args) {
    new Affichage7();
  }
}
