
// Vincent WONG 2025

import java.util.ArrayList;
import java.util.List;

public class Colonne extends TasMain {
	private ArrayList<Carte> colonneVisible = new ArrayList<>();

	public Colonne(ArrayList<Carte> card) {
		super(card);
		this.updateColonneVisible();
	}

	@Override
	public void addCard(Carte card) { // Ajoute une carte dans colonneVisible si possible
    // Colonne visible non-vide
    if (this.colonneVisible.size() > 0) {
        Carte lastCard = this.getColonneVisible().get(0);
        if (card.estJusteEnDessousDe(lastCard) && card.getCouleur() != lastCard.getCouleur()) {
            this.colonneVisible.add(0, card);
        } else {
            throw new Error("Carte " + card + " ne peut pas être placée sur " + lastCard);
        }
    } 
    // Colonne complètement vide (visible + cachée)
    else if (this.getLongueurPaquet() == 0) {
        if (card.getValeur() == Carte.valeurCarte.roi) {
            this.colonneVisible.add(0, card);
        } else {
            throw new Error("Seul un Roi peut être placé sur une colonne vide");
        }
    } 
    // Colonne visible vide MAIS cartes cachées présentes
    else {
        throw new Error("Impossible d'ajouter sur une colonne ");
    }
}
	protected boolean addListCard(List<Carte> listCardExt) {
		Carte derniereCarteDuPaquet = listCardExt.get(listCardExt.size() - 1);
		Carte carteVisibleColonne = this.getColonneVisible().get(0);

		if (derniereCarteDuPaquet.estJusteEnDessousDe(carteVisibleColonne)
				&& derniereCarteDuPaquet.getCouleur() != carteVisibleColonne.getCouleur()) {
			this.getColonneVisible().addAll(0,listCardExt);
			listCardExt.clear();
			if (this.colonneVisible.isEmpty() && this.getLongueurPaquet() > 0) {
				this.updateColonneVisible();
			}
			return true;
		} else {
			throw new Error("Ajout impossible methode addListCard");

		}

	}

	protected boolean canAddListCardFromColonne(List<Carte> listCardExt) {
		if (listCardExt.isEmpty()) {
			return false;
		}
		if (this.getColonneVisible().isEmpty()) {
			return listCardExt.get(listCardExt.size() - 1).getValeur() == Carte.valeurCarte.roi;
		}
		Carte carteVisibleColonne = this.getColonneVisible().get(0);
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

	protected ArrayList<Carte> getColonneVisible() { // Retourne le tableau de colonneVisible
		return this.colonneVisible;
	}

	public void updateColonneVisible() { // si la colonnevisible est vide, elle prend une carte de la pioche
		if (this.getLongueurPaquet() != 0) {
			Carte carteSommetColonneCachee = this.pullCard();
			this.getColonneVisible().add(0, carteSommetColonneCachee);
			;
		} else {
			throw new Error("il n'y a plus de carte à ajouter");
		}
	}

	public String toString() {
		return this.colonneVisible.toString();
	}

}
