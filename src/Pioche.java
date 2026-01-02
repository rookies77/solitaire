// Vincent WONG 2025

public class Pioche extends TasMain {
	private Defausse defausse = new Defausse();

	public Pioche(Distributeur dist) {
		super(); // Crée un paquet vide
		// Transfère toutes les cartes restantes du distributeur vers la pioche
		while (dist.getLongueurPaquet() > 0) {
			super.addCard(dist.pullCard());
		}
	}

	// Accès à la défausse
	public Defausse getDefausse() {
		return this.defausse;
	}

	protected Carte pullCardAndAddDefausse() {
		if (this.getLongueurPaquet() > 0) {
			Carte card = this.pullCard();
			defausse.addCard(card);
			System.out.println("Ajout de la carte " + defausse.getSommetCard() + " dans la defausse");
			return card;
		} else if (defausse.getLongueurPaquet() > 0) {
			// Recycler la défausse : remettre toutes les cartes dans la pioche
			int nombreCartes = defausse.getLongueurPaquet();
			for (int i = 0; i < nombreCartes; i++) {
				super.addCard(defausse.pullCard());
			}
			System.out.println("Remise de la défausse dans la pioche face cachée");
		} else {
			System.out.println("La pioche est vide");
		}
		return null;
	}
}
