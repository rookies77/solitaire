
// Vincent WONG 2025

import java.util.ArrayList;
import java.util.List;

public class Colonne extends TasMain {
	private ArrayList<Carte> colonneVisible = new ArrayList<>();

	public Colonne(ArrayList<Carte> cartesCachees) {
		super(); // Crée un paquet vide
		// Ajoute les cartes cachées au paquet
		for (Carte carte : cartesCachees) {
			super.addCard(carte);
		}
		// Ne retourne une carte que s'il y en a
		if (this.getLongueurPaquet() > 0) {
			this.updateColonneVisible();
		}
	}

	@Override
	public void addCard(Carte card) { // Ajoute une carte dans colonneVisible si possible
    // Colonne visible non-vide
    if (this.colonneVisible.size() > 0) {
        Carte lastCard = this.colonneVisible.get(0);
        if (card.estJusteEnDessousDe(lastCard) && card.getCouleur() != lastCard.getCouleur()) {
            this.colonneVisible.add(0, card);
        } else {
            throw new IllegalArgumentException("Carte " + card + " ne peut pas être placée sur " + lastCard);
        }
    } 
    // Colonne complètement vide (visible + cachée)
    else if (this.getLongueurPaquet() == 0) {
        if (card.getValeur() == Carte.valeurCarte.roi) {
            this.colonneVisible.add(0, card);
        } else {
            throw new IllegalArgumentException("Seul un Roi peut être placé sur une colonne vide");
        }
    } 
    // Colonne visible vide MAIS cartes cachées présentes
    else {
        throw new IllegalStateException("Impossible d'ajouter sur une colonne avec cartes cachées sans carte visible");
    }
}
	protected boolean addListCard(List<Carte> listCardExt) {
		Carte derniereCarteDuPaquet = listCardExt.get(listCardExt.size() - 1);
		Carte carteVisibleColonne = this.colonneVisible.get(0);

		if (derniereCarteDuPaquet.estJusteEnDessousDe(carteVisibleColonne)
				&& derniereCarteDuPaquet.getCouleur() != carteVisibleColonne.getCouleur()) {
			this.colonneVisible.addAll(0,listCardExt);
			listCardExt.clear();
			if (this.colonneVisible.isEmpty() && this.getLongueurPaquet() > 0) {
				this.updateColonneVisible();
			}
			return true;
		} else {
			throw new IllegalArgumentException("Ajout impossible : les cartes ne respectent pas l'ordre");

		}

	}

	protected boolean canAddListCardFromColonne(List<Carte> listCardExt) {
		if (listCardExt.isEmpty()) {
			return false;
		}
		if (this.colonneVisible.isEmpty()) {
			return listCardExt.get(listCardExt.size() - 1).getValeur() == Carte.valeurCarte.roi;
		}
		Carte carteVisibleColonne = this.colonneVisible.get(0);
		Carte derniereCarte = listCardExt.get(listCardExt.size() - 1);

		return derniereCarte.estJusteEnDessousDe(carteVisibleColonne)
				&& derniereCarte.getCouleur() != carteVisibleColonne.getCouleur();
	}

	protected Carte pullCardColonneVisible() {
		return this.colonneVisible.remove(0);
	}

	protected void deleteListCard(List<Carte> cards) {
		this.colonneVisible.removeAll(cards);
		// Si la colonne visible est vide , on retourne la prochaine
		if (this.colonneVisible.isEmpty() && this.getLongueurPaquet() > 0) {
			this.updateColonneVisible();
		}
	}

	// Méthodes d'accès sécurisé (ne retournent pas la liste directement)
	
	public boolean estColonneVisibleVide() {
		return this.colonneVisible.isEmpty();
	}
	
	public int getTailleColonneVisible() {
		return this.colonneVisible.size();
	}
	
	public Carte getCarteVisibleAuSommet() {
		if (this.colonneVisible.isEmpty()) {
			return null;
		}
		return this.colonneVisible.get(0);
	}
	
	public Carte getCarteVisibleAt(int index) {
		if (index >= 0 && index < this.colonneVisible.size()) {
			return this.colonneVisible.get(index);
		}
		return null;
	}
	
	// Méthode interne pour usage dans la classe (protected)
	protected ArrayList<Carte> getColonneVisibleInterne() {
		return this.colonneVisible;
	}

	public void updateColonneVisible() { // si la colonnevisible est vide, elle prend une carte de la pioche
		if (this.getLongueurPaquet() != 0) {
			Carte carteSommetColonneCachee = this.pullCard();
			this.colonneVisible.add(0, carteSommetColonneCachee);
		} else {
			throw new IllegalStateException("Il n'y a plus de carte cachée à retourner");
		}
	}

	public String toString() {
		return this.colonneVisible.toString();
	}

}
