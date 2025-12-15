// Vincent WONG 2025

import java.util.ArrayList;

public class Pieux extends TasMain{
	
	public Pieux() {
		super(new ArrayList<Carte>());
	}
	
	@Override
	public void addCard(Carte card) {
		if(this.getLongueurPaquet() == 0) {
			if(card.getValeur() == Carte.valeurCarte.as) {
				super.addCard(card);
				System.out.println("on Ajoute dans le pieux du symbole " + card.getSymbole());
			}else {
				 throw new Error("Carte incorrecte");
			}
		}else {
			Carte lastCarte = this.getSommetCard();
			if(lastCarte.precedeMemeCouleur(card) && lastCarte.estJusteEnDessousDe(card)) {
				super.addCard(card);
				System.out.println("on Ajoute dans le pieux du symbole" + card.getSymbole());
			}else {
				throw new Error("Carte incorrecte au pieux");
			}
		}
	}
	


}
