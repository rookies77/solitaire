// Vincent WONG 2025

public class Pioche extends TasMain {
	Defausse defausse = new Defausse();

	public Pioche(Distributeur dist) {
		super(dist.getPaquetRestant());

	}

	protected void pullCardAndAddDefausse() {
		if (this.getLongueurPaquet() > 0) {
			Carte Card = this.pullCard();
			defausse.addCard(Card);
			System.out.println("Ajout de la carte " + defausse.getSommetCard() + " dans la defausse");
		} else if (defausse.getLongueurPaquet() > 0) {
	
			for (int i = 0; i < defausse.getLongueurPaquet(); i++) {
				super.addCard(defausse.pullCard());
			}
			System.out.println("remettre la defausse dans la pioche face caché");
		} else {
			System.out.println("La pioche est vide");
		}

	}
}
