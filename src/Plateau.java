// Vincent WONG 2025

import java.util.ArrayList;

public class Plateau {
	private Distributeur dist = new Distributeur();
	private Pioche pioche;
	private ArrayList<Colonne> listColonne = new ArrayList<>(7);
	private ArrayList<Pieux> listPieux = new ArrayList<>(4);

	public Plateau() {
		this.distribColonnes(); // distribution des cartes dans les colonnes

		pioche = new Pioche(dist); // le reste des cartes dans la pioche
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
}
