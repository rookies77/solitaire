import java.util.Scanner;

import javax.swing.JButton;

import interfaceGraphique.InterfaceKlondike;

public class Affichage6 {
  // private Distributeur dist = new Distributeur();

  public static void main(String[] args) {
    Plateau plat = new Plateau();
    InterfaceKlondike ik;
    Carte dos;
    Scanner scan = new Scanner(System.in);
    dos = new Carte(null, null);

    ik = new InterfaceKlondike();
    ik.addCard(dos, 0);
    int nombreDeColonne = plat.getNombreColonnes();
    for (int i = 1; i < nombreDeColonne; i++) {
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

    scan.nextLine();
    System.out.println("Bye");
    ik.close();
  }
}
