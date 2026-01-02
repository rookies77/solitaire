
import interfaceGraphique.ICarte;

public class Carte implements ICarte {
	private final valeurCarte valeur;
	private final couleurCarte couleur;
	private final symboleCarte symbole;

	public enum valeurCarte {
		as("as"), deux("2"), trois("3"), quatre("4"), cinq("5"), six("6"), sept("7"), huit("8"), neuf("9"), dix("10"),
		valet("valet"), dame("dame"), roi("roi");

		private final String nom;

		valeurCarte(String nom) {
			this.nom = nom;
		}

		public String getNom() {
			return this.nom;
		}
	}

	public enum symboleCarte {
		pique, trefle, coeur, carreau
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
		// Vérifie que this précède carteExt : même symbole ET valeurs successives
		return this.getSymbole() == carteExt.getSymbole()
				&& this.estJusteEnDessousDe(carteExt);
	}

	@Override
	public String getNomDeFichierPNG() {
		if(this.valeur == null) {
			return "dos.png";
		}
		return this.valeur.getNom() + "_de_" + this.symbole.toString() + ".png";
	}
	
	@Override
	public String toString() {
		if (this.valeur == null || this.symbole == null) {
			return "Carte de dos";
		}
		return this.valeur.getNom() + " de " + this.symbole.toString();
	}
}
