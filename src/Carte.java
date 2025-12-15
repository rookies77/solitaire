// Vincent WONG 2025

public class Carte {
	private final valeurCarte valeur;
	private final couleurCarte couleur;
	private final symboleCarte symbole;

	public enum valeurCarte {
		as, deux, trois, quatre, cinq, six, sept, huit, neuf, dix, valet, dame, roi
	}

	public enum symboleCarte {
		pique, trefle, coeur, carreaux
	}

	public enum couleurCarte {
		rouge, noir
	}

	public Carte(valeurCarte valeur, symboleCarte symbole) {

		this.valeur = valeur;
		this.symbole = symbole;

		this.couleur = (this.symbole == symboleCarte.pique || this.symbole == symboleCarte.trefle) ? couleurCarte.noir
				: couleurCarte.rouge;

	}

	public valeurCarte getValeur() {
		return this.valeur;
	}

	public symboleCarte getSymbole() {
		return this.symbole;
	}

	public couleurCarte getCouleur() {
		return this.couleur;
	}

	boolean estJusteEnDessousDe(Carte carteExt) {
		return this.valeur.ordinal() + 1 == carteExt.valeur.ordinal();
	}

	boolean precedeMemeCouleur(Carte carteExt) {
		return this.getSymbole() == carteExt.getSymbole();
 	}
	
	public String toString() {
		return  valeur + " de " + symbole + " " + couleur;
	}
}
