// Vincent WONG 2025

import java.util.ArrayList;
import java.util.Random;

public class Distributeur extends TasMain {

	public Distributeur() {
		super(); // Crée un paquet vide
		this.creerPaquetComplet(); // Ajoute les 52 cartes
		this.melangerPaquet(); // Mélange
	}

	private void creerPaquetComplet() {
		// Crée les 52 cartes et les ajoute au paquet
		for (Carte.valeurCarte vc : Carte.valeurCarte.values()) {
			for (Carte.symboleCarte sc : Carte.symboleCarte.values()) {
				this.addCard(new Carte(vc, sc));
			}
		}
	}

	public void melangerPaquet() {
		Random random = new Random(); // Variable locale
		ArrayList<Carte> temp = new ArrayList<>();
		
		// Vide le paquet dans une liste temporaire
		while (this.getLongueurPaquet() > 0) {
			Carte card = this.pullCard();
			temp.add(card);
		}

		// Remet les cartes dans un ordre aléatoire
		while (!temp.isEmpty()) {
			int index = random.nextInt(temp.size());
			Carte retirerCardTemp = temp.remove(index);
			this.addCard(retirerCardTemp);
		}
	}
}