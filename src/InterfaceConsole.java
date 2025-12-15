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
          plateau.pioche.pullCardAndAddDefausse();
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
    for (int i = 0; i < plateau.listColonne.size(); i++) {
      System.out.println((i + 1) + "- Colonne " + (i + 1) + ": " + plateau.listColonne.get(i));
    }
    for (int i = 0; i < plateau.listPieux.size(); i++) {
      System.out.println((i + 8) + "- Pieux " + (i + 1) + ": " + plateau.listPieux.get(i));
    }
    System.out.println("12- Défausse: " + plateau.pioche.defausse.getSommetCard());
  }

  private void prendreUneCarte() {
    this.afficherPlateau("=== PLATEAU === : Prendre une carte");
    System.out.println("\nQuel carte voulez vous deplacer ?");
    for (int i = 0; i < plateau.listColonne.size(); i++) {
      if (!plateau.listColonne.get(i).getColonneVisible().isEmpty()) {
        System.out
            .println((i + 1) + "- Colonne " + (i + 1) + " -- " + plateau.listColonne.get(i).getColonneVisible().get(0));
      }
    }
    for (int i = 0; i < plateau.listPieux.size(); i++) {
      if (plateau.listPieux.get(i).getLongueurPaquet() > 0) {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- " + plateau.listPieux.get(i).getSommetCard());
      }
    }
    if (plateau.pioche.defausse.getLongueurPaquet() > 0) {
      System.out.println("12- Defausse" + " " + plateau.pioche.defausse.getSommetCard());
    }

    System.out.println("13- revenir en arriere");

    // ------------SCANNER
    // -----------------------------------------------------------------------------------------------
    int choix = scanner.nextInt();

    Carte cardSelected;
    try {
      if (choix >= 1 && choix <= 7) {
        if (plateau.listColonne.get(choix - 1).getColonneVisible().isEmpty()) {
          this.prendreUneCarte();
        } else {
          cardSelected = plateau.listColonne.get(choix - 1).getColonneVisible().get(0);
          if(!this.placerUneCarte(cardSelected)) {
            return;
          }
          plateau.listColonne.get(choix - 1).pullCardColonneVisible();

          if (plateau.listColonne.get(choix - 1).getColonneVisible().isEmpty() &&
              plateau.listColonne.get(choix - 1).getLongueurPaquet() > 0) {
            plateau.listColonne.get(choix - 1).updateColonneVisible();
          }
          System.out.println("Ajout avec succès dans la colonne " + choix);
        }
      } else if (choix >= 8 && choix <= 11) {
        int pieuxIndex = choix - 8;
        if (plateau.listPieux.get(pieuxIndex).getLongueurPaquet() == 0) {
          this.prendreUneCarte();
          System.out.println("Le pieux " + (pieuxIndex + 1) + " est vide.");
        } else {
          cardSelected = plateau.pioche.defausse.getSommetCard();

          this.placerUneCarte(cardSelected);
          plateau.pioche.defausse.pullCard();
          System.out.println("Ajout avec succès dans le pieux " + (pieuxIndex + 1));
        }

      } else if (choix == 12) {
        if (plateau.pioche.defausse.getLongueurPaquet() == 0) {
          this.jouer();
          System.out.println("La défausse est vide.");
        } else {
          cardSelected = plateau.pioche.defausse.getSommetCard();
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
    for (int i = 0; i < plateau.listColonne.size(); i++) { // boucle d'affichage des choix
      if (!plateau.listColonne.get(i).getColonneVisible().isEmpty()) {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- "
            + plateau.listColonne.get(i).getColonneVisible().get(0));
      } else {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " []");
      }
    }
    for (int i = 0; i < plateau.listPieux.size(); i++) {
      if (plateau.listPieux.get(i).getLongueurPaquet() > 0) {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- " + plateau.listPieux.get(i).getSommetCard());
      } else {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- []");
      }
    }
    // ------------SCANNER
    // -----------------------------------------------------------------------------------------------
    int choix = scanner.nextInt();

    try {
      if (choix >= 1 && choix <= 7) {// placer dans une des colonnes
        Colonne destination = plateau.listColonne.get(choix - 1);
        if (!destination.getColonneVisible().isEmpty()
            && card.estJusteEnDessousDe(destination.getColonneVisible().get(0))
            && card.getCouleur() != destination.getColonneVisible().get(0).getCouleur()) {
          // Valider le déplacement avant de retirer la carte
          plateau.listColonne.get(choix - 1).addCard(card);

        } else if (destination.getColonneVisible().isEmpty() && card.getValeur() == Carte.valeurCarte.roi) {
          plateau.listColonne.get(choix - 1).addCard(card);

        }

        else {
          System.out.println("Déplacement invalide. Veuillez réessayer.");
          this.placerUneCarte(card);
          return false;

        }
      } else if (choix >= 8 && choix <= 11) { // placer dans un des pieux
        int pieuxIndex = choix - 8;
        plateau.listPieux.get(pieuxIndex).addCard(card);

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
    for (int i = 0; i < plateau.listColonne.size(); i++) { // boucle d'affichage des choix
      if (!plateau.listColonne.get(i).getColonneVisible().isEmpty()) {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- " + plateau.listColonne.get(i).getColonneVisible());
      }
    }
    // ------------SCANNER
    // -----------------------------------------------------------------------------------------------
    int choixColonne = scanner.nextInt();
    try {
      Colonne colonneSelected = plateau.listColonne.get(choixColonne - 1);
      System.out.println("Quelle carte de la colonne " + choixColonne + " voulez-vous prendre ?");
      for (int j = 0; j < colonneSelected.getColonneVisible().size(); j++) {
        System.out.println(j + 1 + "- " + colonneSelected.getColonneVisible().get(j));
      }
      int choix2 = scanner.nextInt();
      if (choix2 < 1 || choix2 > colonneSelected.getColonneVisible().size()) {
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
    for (int i = 0; i < plateau.listColonne.size(); i++) { // boucle d'affichage des choix
      if (!plateau.listColonne.get(i).getColonneVisible().isEmpty()) {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- "
            + plateau.listColonne.get(i).getColonneVisible().get(0));
      } else {
        System.out.println((i + 1) + "- Colonne " + (i + 1) + " -- []");
      }
    }
    for (int i = 0; i < plateau.listPieux.size(); i++) {
      if (plateau.listPieux.get(0).getLongueurPaquet() > i) {
        System.out.println((i + 8) + "- Pieux " + (i + 1) + " -- " + plateau.listPieux.get(i).getSommetCard());
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
    for (int i = 0; i < plateau.listPieux.size(); i++) {
      if (plateau.listPieux.get(i).getLongueurPaquet() != (52 / 4)) {
        return false; 
      }
    }
    System.out.println("Vous avez gagné !");
    return true; 
  }
}
