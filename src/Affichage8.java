import java.util.Scanner;
import java.util.Random;
import javax.swing.JButton;

import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;

public class Affichage8 implements ClickReporter {
  // private Distributeur dist = new Distributeur();

  private InterfaceKlondike ik;
  Carte carteDuPaquet;
  int colSelected;
  Plateau plat;
  private Random random = new Random();
  private boolean[] tasHighlighted = new boolean[13];

  public Affichage8() {
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
    for (int i = 0; i <= 12; i++) {
      this.carteDuPaquet = plat.getPioche().pullCardAndAddDefausse();
      ik.addCard(carteDuPaquet, i);
    }
    System.out.println("tasHighlighted length: " + tasHighlighted[1]);
  }

  @Override
  public void reportClick(int ident) {
    boolean found = false;
    for (int i = 0; i < tasHighlighted.length; i++) {
      if (tasHighlighted[i] && i != ident) {
        ik.setMessage(" autre tas déjà surligné dans la zone de texte sur la colonne " + i);
        found = true;
      }
    }
    if(found){
      return;
    }
    ik.setMessage(" " );
    tasHighlighted[ident] = !tasHighlighted[ident];
    ik.setHighlighted(ident, tasHighlighted[ident]);

  }

  @Override
  public void buttonPressed(String identifier) {
    // TODO a supprimer apres les test

    ik.setMessage(" autre tas déjà surligné dans la zone de texte sur la colonne " + identifier);
  }

  public static void main(String[] args) {
    new Affichage8();
  }
}
