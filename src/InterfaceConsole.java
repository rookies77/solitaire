// Vincent WONG 2025

import java.util.Scanner;

public class InterfaceConsole {
  private Plateau plateau;
  private Scanner scanner;

  public InterfaceConsole() {
    this.plateau = new Plateau();
    this.scanner = new Scanner(System.in);
    this.afficherPlateau("Plateau initial:");
  }

  public void jouer() {
    while (true) {
      System.out.println("\nQue voulez-vous faire ?");
      System.out.println("1. Piocher");
      System.out.println("2. Afficher plateau");
      System.out.println("3. Déplacer une carte");
      System.out.println("4. Déplacer une une liste de cartes");
      System.out.println("5. Quitter");

      int choix = scanner.nextInt();
      switch (choix) {
        case 1:
          plateau.getPioche().pullCardAndAddDefausse();
          break;
        case 2:
          this.afficherPlateau("=== PLATEAU ===");
          break;
        case 3:
          this.prendreUneCarte();
          break;
        case 4:
          this.prendreUneListeCartes();
          break;
        case 5:
          System.out.println("Au revoir !");
          return;
      }
    }
  }

  private void afficherPlateau(String text) {
    System.out.println(text);
    for (int i = 0; i < plateau.getNombreColonnes(); i++) {
      System.out.println((i + 1) + "- Colonne " + (i + 1) + ": " + plateau.getColonne(i));
    }
    for (int i = 0; i < plateau.getNombrePieux(); i++) {
      System.out.println((i + 8) + "- Pieux " + (i + 1) + ": " + plateau.getPieux(i));
    }
    System.out.println("12- Défausse: " + plateau.getPioche().getDefausse().getSommetCard());
  }

  private void prendreUneCarte() {
    this.afficherPlateau("=== PLATEAU === : Prendre une carte");
    System.out.println("\nQuel carte voulez vous deplacer ?");
    for (int i = 0; i < plateau.getNombreColonnes(); i++) {
      if (!plateau.getColonne(i).estColonneVisibleVide()) {
        System.out
            .println((i + 1) + "- Colonne " + (i + 1) + " -- " + plateau.getColonne(i).getCarteVisibleAuSommet());
      }
    }
    for (int i = 0; i < plateau.getNombrePieux(); i++) {
      if (plateau.getPieux(i).getLongueurPaquet() > 0) {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- " + plateau.getPieux(i).getSommetCard());
      }
    }
    if (plateau.getPioche().getDefausse().getLongueurPaquet() > 0) {
      System.out.println("12- Defausse" + " " + plateau.getPioche().getDefausse().getSommetCard());
    }

    System.out.println("13- revenir en arriere");

    // ------------SCANNER
    // -----------------------------------------------------------------------------------------------
    int choix = scanner.nextInt();

    Carte cardSelected;
    try {
      if (choix >= 1 && choix <= 7) {
        if (plateau.getColonne(choix - 1).estColonneVisibleVide()) {
          this.prendreUneCarte();
        } else {
          cardSelected = plateau.getColonne(choix - 1).getCarteVisibleAuSommet();
          if(!this.placerUneCarte(cardSelected)) {
            return;
          }
          plateau.getColonne(choix - 1).pullCardColonneVisible();

          if (plateau.getColonne(choix - 1).estColonneVisibleVide() &&
              plateau.getColonne(choix - 1).getLongueurPaquet() > 0) {
            plateau.getColonne(choix - 1).updateColonneVisible();
          }
          System.out.println("Ajout avec succès dans la colonne " + choix);
        }
      } else if (choix >= 8 && choix <= 11) {
        int pieuxIndex = choix - 8;
        if (plateau.getPieux(pieuxIndex).getLongueurPaquet() == 0) {
          this.prendreUneCarte();
          System.out.println("Le pieux " + (pieuxIndex + 1) + " est vide.");
        } else {
          cardSelected = plateau.getPioche().getDefausse().getSommetCard();

          this.placerUneCarte(cardSelected);
          plateau.getPioche().getDefausse().pullCard();
          System.out.println("Ajout avec succès dans le pieux " + (pieuxIndex + 1));
        }

      } else if (choix == 12) {
        if (plateau.getPioche().getDefausse().getLongueurPaquet() == 0) {
          this.jouer();
          System.out.println("La défausse est vide.");
        } else {
          cardSelected = plateau.getPioche().getDefausse().getSommetCard();
          this.placerUneCarte(cardSelected);
          System.out.println("Ajout avec succès");
        }

      } else if (choix >= 13) {
        this.jouer();
        return;
      } else {
        System.out.println("Choix invalide. Veuillez réessayer.");
        this.jouer();
        return;
      }
    } catch (Exception e) {
      System.out.println("choix invalide. Veuillez réessayer." + e);
      this.jouer();
      return;
    }
  }

  private boolean placerUneCarte(Carte card) {
    this.afficherPlateau("=== PLATEAU ===: Placer la carte " + card);
    System.out.println("\nOù voulez-vous placer la carte -- " + card + " ?");
    for (int i = 0; i < plateau.getNombreColonnes(); i++) { // boucle d'affichage des choix
      if (!plateau.getColonne(i).estColonneVisibleVide()) {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- "
            + plateau.getColonne(i).getCarteVisibleAuSommet());
      } else {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " []");
      }
    }
    for (int i = 0; i < plateau.getNombrePieux(); i++) {
      if (plateau.getPieux(i).getLongueurPaquet() > 0) {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- " + plateau.getPieux(i).getSommetCard());
      } else {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- []");
      }
    }
    // ------------SCANNER
    // -----------------------------------------------------------------------------------------------
    int choix = scanner.nextInt();

    try {
      if (choix >= 1 && choix <= 7) {// placer dans une des colonnes
        Colonne destination = plateau.getColonne(choix - 1);
        if (!destination.estColonneVisibleVide()
            && card.estJusteEnDessousDe(destination.getCarteVisibleAuSommet())
            && card.getCouleur() != destination.getCarteVisibleAuSommet().getCouleur()) {
          // Valider le déplacement avant de retirer la carte
          plateau.getColonne(choix - 1).addCard(card);

        } else if (destination.estColonneVisibleVide() && card.getValeur() == Carte.valeurCarte.roi) {
          plateau.getColonne(choix - 1).addCard(card);

        }

        else {
          System.out.println("Déplacement invalide. Veuillez réessayer.");
          this.placerUneCarte(card);
          return false;

        }
      } else if (choix >= 8 && choix <= 11) { // placer dans un des pieux
        int pieuxIndex = choix - 8;
        plateau.getPieux(pieuxIndex).addCard(card);

        if (this.estVictoire()) {
          System.out.println("VICTOIRE !"); 
          System.exit(0); // Arrete le jeu
        }

      } else {
        System.out.println("Choix invalide. Veuillez réessayer.");
        this.placerUneCarte(card);
        return false;
      }
      return true;
    } catch (Exception e) {
      System.out.println("Déplacement invalide. Veuillez réessayer.");
      this.placerUneCarte(card);
      return false;
    }
  }

  private void prendreUneListeCartes() {
    System.out.println("\nQuel colonne voulez choisir pour prendre une liste de cartes ?");
    for (int i = 0; i < plateau.getNombreColonnes(); i++) { // boucle d'affichage des choix
      if (!plateau.getColonne(i).estColonneVisibleVide()) {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- " + plateau.getColonne(i).toString());
      }
    }
    // ------------SCANNER
    // -----------------------------------------------------------------------------------------------
    int choixColonne = scanner.nextInt();
    try {
      Colonne colonneSelected = plateau.getColonne(choixColonne - 1);
      System.out.println("Quelle carte de la colonne " + choixColonne + " voulez-vous prendre ?");
      for (int j = 0; j < colonneSelected.getTailleColonneVisible(); j++) {
        System.out.println(j + 1 + "- " + colonneSelected.getCarteVisibleAt(j));
      }
      int choix2 = scanner.nextInt();
      if (choix2 < 1 || choix2 > colonneSelected.getTailleColonneVisible()) {
        throw new IllegalArgumentException("choix invalide");
      } else {
        placerUneListeCartes(choixColonne - 1, choix2 - 1);
      }
    } catch (Exception e) {
      System.out.println("Choix invalide. Veuillez réessayer. catch dans prendreUneListeCartes");
      this.prendreUneListeCartes(); 
      return;
    }

  }

  private void placerUneListeCartes(int colSource, int indexDebut) {
    // Affichage des choix...
    System.out.println("------\nQuel colonne ou pieux voulez vous placer ?-------");
    for (int i = 0; i < plateau.getNombreColonnes(); i++) { // boucle d'affichage des choix
      if (!plateau.getColonne(i).estColonneVisibleVide()) {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- "
            + plateau.getColonne(i).getCarteVisibleAuSommet());
      } else {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- []");
      }
    }
    for (int i = 0; i < plateau.getNombrePieux(); i++) {
      if (plateau.getPieux(0).getLongueurPaquet() > i) {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- " + plateau.getPieux(i).getSommetCard());
      } else {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- []");
      }
    }

    // ------------SCANNER
    // -----------------------------------------------------------------------------------------------
    int choix = scanner.nextInt();

    if (choix >= 1 && choix <= 7) {
      boolean succes = plateau.peutDeplacerListeCarte(colSource, indexDebut, choix - 1);
      if (!succes) {
        System.out.println("Déplacement impossible, recommencons ");

        this.prendreUneListeCartes();
      }
    }
  }

  private boolean estVictoire() {
    for (int i = 0; i < plateau.getNombrePieux(); i++) {
      if (plateau.getPieux(i).getLongueurPaquet() != (52 / 4)) {
        return false; 
      }
    }
    System.out.println("Vous avez gagné !");
    return true; 
  }
}
