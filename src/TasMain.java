// Vincent WONG 2025

import java.util.ArrayList;

public class TasMain {
	private ArrayList<Carte> paquet;

	public TasMain(ArrayList<Carte> paquet) {
		this.paquet = new ArrayList<>(paquet);
	}

	public void addCard(Carte card) {
		this.paquet.add(0, card);
	}

	public Carte pullCard() {
		if (!this.paquet.isEmpty()) {
			return this.paquet.remove(0);
		} else {
			throw new IllegalArgumentException("le paquet est vide");
		}

	}

	public Carte getSommetCard() {
		if (!this.paquet.isEmpty()) {
			return this.paquet.get(0);
		}
		return null;
	}

	public int getLongueurPaquet() {
		return this.paquet.size();
	}

	public String toString() {
		return this.paquet.toString();
	}
}
