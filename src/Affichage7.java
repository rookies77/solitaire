import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;

public class Affichage7 implements ClickReporter {
  private InterfaceKlondike ik;
  Carte dos;
  Plateau plat;
  private Integer indexDepart = null;  // null = aucune sélection

  public Affichage7() {
    ik = new InterfaceKlondike(this);
    ik.addButton("carte");
    plat = new Plateau();
    this.init();

  }

  private void init() {
    this.dos = new Carte(null, null);
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
  }

  @Override
  public void reportClick(int ident) {
    if (ident == 0) {
      // Pioche : action spéciale
      plat.getPioche().pullCardAndAddDefausse();
      updateInterfaceByBack();
      return;
    }
    
    if (indexDepart == null) {
      // Premier clic : surligner le point de départ
      indexDepart = ident;
      ik.setHighlighted(ident, true);
      ik.setMessage("Tas sélectionné, cliquez sur la destination");
    } else {
      // Deuxième clic : tenter le déplacement
      if (indexDepart == ident) {
        // Annuler la sélection
        ik.setHighlighted(indexDepart, false);
        indexDepart = null;
        ik.setMessage("Sélection annulée");
      } else {
        // Tenter le déplacement
        tentarDeplacement(indexDepart, ident);
      }
    }
  }

  private void tentarDeplacement(int depart, int arrivee) {
    try {
      // Retirer la carte du point de départ
      Carte carte = retirerCarte(depart);
      
      if (carte == null) {
        ik.setMessage("Erreur : aucune carte à déplacer");
        ik.setHighlighted(indexDepart, false);
        indexDepart = null;
        return;
      }
      
      // Ajouter la carte au point d'arrivée
      ajouterCarte(carte, arrivee);
      
      // Enlever le surlignage
      ik.setHighlighted(indexDepart, false);
      indexDepart = null;
      ik.setMessage("Déplacement effectué");
      
    } catch (Exception e) {
      ik.setMessage("Erreur : " + e.getMessage());
      ik.setHighlighted(indexDepart, false);
      indexDepart = null;
    }
    
    updateInterfaceByBack();
  }

  private Carte retirerCarte(int ident) {
    if (ident == 1) {
      return plat.getPioche().getDefausse().pullCard();
    } else if (ident >= 2 && ident <= 5) {
      return plat.getPieux(ident - 2).pullCard();
    } else if (ident >= 6 && ident <= 12) {
      Colonne source = plat.getColonne(ident - 6);
      Carte carte = source.pullCardColonneVisible();
      if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
        source.updateColonneVisible();
      }
      return carte;
    }
    return null;
  }
  
  private void ajouterCarte(Carte carte, int ident) {
    if (ident >= 2 && ident <= 5) {
      plat.getPieux(ident - 2).addCard(carte);
    } else if (ident >= 6 && ident <= 12) {
      plat.getColonne(ident - 6).addCard(carte);
    }
  }

  private void updateInterfaceByBack() {
    for (int i = 0; i < plat.getNombreColonnes(); i++) {
      int longueurDuPaquet = plat.getColonne(i).getLongueurPaquet();
      int tailleColonneVisible = plat.getColonne(i).getTailleColonneVisible();
      Carte[] cartes = new Carte[longueurDuPaquet + tailleColonneVisible];

      for (int j = 0; j < tailleColonneVisible; j++) {
        cartes[j] = plat.getColonne(i).getCarteVisibleAt(j);
      }

      for (int j = 0; j < longueurDuPaquet; j++) {
        cartes[tailleColonneVisible + j] = dos;
      }

      ik.setCards(cartes, i + 6);
    }

    for (int i = 0; i < plat.getNombrePieux(); i++) {
      Carte carte = plat.getPieux(i).getSommetCard();
      if (carte != null) {
        ik.setCard(carte, i + 2);
      } else {
        ik.clear(i + 2);
      }
    }
    
    Carte card = plat.getPioche().getDefausse().getSommetCard();
    if (card != null) {
      ik.setCard(card, 1);
    } else {
      ik.clear(1);
    }

    if (plat.getPioche().getLongueurPaquet() > 0) {
      ik.setCard(dos, 0);
    } else {
      ik.clear(0);
    }
  }

  @Override
  public void buttonPressed(String identifier) {
    ik.setMessage("Bouton " + identifier + " pressé");
  }

  public static void main(String[] args) {
    new Affichage7();
  }
}
