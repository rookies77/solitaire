// Vincent WONG 2025

public class Pieux extends TasMain{
	
	public Pieux() {
		super();
	}
	
	@Override
	public void addCard(Carte card) {
		if(this.getLongueurPaquet() == 0) {
			if(card.getValeur() == Carte.valeurCarte.as) {
				super.addCard(card);
				System.out.println("on Ajoute dans le pieux du symbole " + card.getSymbole());
			}else {
				 throw new IllegalArgumentException("Un pieux doit commencer par un As, pas par " + card.getValeur());
			}
		}else {
			Carte lastCarte = this.getSommetCard();
			if(lastCarte.precedeMemeCouleur(card)) {
				super.addCard(card);
				System.out.println("on Ajoute dans le pieux numero " + card.getValeur() +" " +card.getSymbole());
			}else {
				throw new IllegalArgumentException("Carte " + card + " ne peut pas suivre " + lastCarte + " dans ce pieux");
			}
		}
	}
	


}
