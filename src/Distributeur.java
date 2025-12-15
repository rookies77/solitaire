// Vincent WONG 2025

import java.util.ArrayList;
import java.util.Random;

public class Distributeur extends TasMain {
	Random random = new Random();

	public Distributeur() {
		super(creationPaquet());
		this.melangerPaquet();
	}

	public static ArrayList<Carte> creationPaquet() {
		ArrayList<Carte> listeCarte = new ArrayList<>();
		for (Carte.valeurCarte vc : Carte.valeurCarte.values()) {
			for (Carte.symboleCarte sc : Carte.symboleCarte.values()) {
				listeCarte.add(new Carte(vc, sc));
			}
		}
		return listeCarte;
	}

	public void melangerPaquet() {
		ArrayList<Carte> temp = new ArrayList<>();
		while (this.getLongueurPaquet() > 0) {
			try {
				Carte card = this.pullCard();
				temp.add(card);

			} catch (IllegalArgumentException e) {
				break;
			}

		}

		int index;
		while (!temp.isEmpty()) {
			index = random.nextInt(temp.size());
			Carte retirerCardTemp = temp.remove(index);
			this.addCard(retirerCardTemp);
		}
	}

//	public Carte getRetirerCarte() {
//		return this.pullCard();
//	}


	public ArrayList<Carte> getPaquetRestant() {
		ArrayList<Carte> temp = new ArrayList<>();
		while (this.getLongueurPaquet() > 0) {
			Carte card = this.pullCard();
			;
			temp.add(0, card);
		}
		return temp;
	}
}