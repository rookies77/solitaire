// Vincent WONG 2025

import java.util.ArrayList;
import java.util.List;

public class Plateau {
	private Distributeur dist = new Distributeur();
	private Pioche pioche;
	private ArrayList<Colonne> listColonne = new ArrayList<>();
	private ArrayList<Pieux> listPieux = new ArrayList<>();

	public Plateau() {
		this.distribColonnes();

		pioche = new Pioche(dist);
		this.listPieux.add(new Pieux());
		this.listPieux.add(new Pieux());
		this.listPieux.add(new Pieux());
		this.listPieux.add(new Pieux());
	}

	// Méthodes d'accès sécurisé pour les colonnes
	public int getNombreColonnes() {
		return this.listColonne.size();
	}

	public Colonne getColonne(int index) {
		if (index >= 0 && index < this.listColonne.size()) {
			return this.listColonne.get(index);
		}
		throw new IndexOutOfBoundsException("Index de colonne invalide: " + index);
	}

	// Méthodes d'accès sécurisé pour les pieux
	public int getNombrePieux() {
		return this.listPieux.size();
	}

	public Pieux getPieux(int index) {
		if (index >= 0 && index < this.listPieux.size()) {
			return this.listPieux.get(index);
		}
		throw new IndexOutOfBoundsException("Index de pieux invalide: " + index);
	}

	// Accès à la pioche
	public Pioche getPioche() {
		return this.pioche;
	}

	public void distribColonnes() {
		for (int i = 0; i < 7; i++) {
			ArrayList<Carte> ListCard = new ArrayList<>();
			for (int j = 0; j <= i; j++) {
				Carte card = this.dist.pullCard();
				ListCard.add(0, card);
			}
			this.listColonne.add(new Colonne(ListCard));
		}

	}

	protected void addDefausseIntoColonne(int indexColonne) {
		Carte DefausseCard = pioche.getDefausse().getSommetCard();
		Colonne colonneCible = this.listColonne.get(indexColonne);

		try {
			colonneCible.addCard(DefausseCard);
			pioche.getDefausse().pullCard();
		} catch (Exception e) {
			throw e;
		}
	}

	protected boolean peutDeplacerListeCarte(int colSource, int indexDebut, int colDestination) {
		try {
			Colonne source = listColonne.get(colSource);
			Colonne destination = listColonne.get(colDestination);
			List<Carte> cartesADeplacer = new ArrayList<>();
			for (int i = indexDebut; i < source.getTailleColonneVisible(); i++) {
				cartesADeplacer.add(source.getCarteVisibleAt(i));
			}

			if (!destination.canAddListCardFromColonne(cartesADeplacer)) {
				return false;
			}
			destination.addListCard(cartesADeplacer);
			for (int i = source.getTailleColonneVisible() - 1; i >= indexDebut; i--) {
				source.getColonneVisibleInterne().remove(i);
			}
			if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
				source.updateColonneVisible();
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deplacerCarteVersPieux(int colSource, int indexPieux) {
		try {
			Colonne source = listColonne.get(colSource);
			Pieux pieux = listPieux.get(indexPieux);

			if (source.estColonneVisibleVide())
				return false;

			Carte carte = source.getCarteVisibleAuSommet();
			pieux.addCard(carte);
			source.pullCardColonneVisible();

			if (source.estColonneVisibleVide() && source.getLongueurPaquet() > 0) {
				source.updateColonneVisible();
			}

			return true;

		} catch (Exception e) {
			return false;
		}
	}
}
