// Vincent WONG 2025

import java.util.ArrayList;
import java.util.List;

public class Plateau {
	Distributeur dist = new Distributeur();
	Pioche pioche;
	ArrayList<Colonne> listColonne = new ArrayList<>();
	ArrayList<Pieux> listPieux = new ArrayList<>();

	public Plateau() {
		this.distribColonnes();
		pioche = new Pioche(dist);
		this.listPieux.add(new Pieux());
		this.listPieux.add(new Pieux());
		this.listPieux.add(new Pieux());
		this.listPieux.add(new Pieux());
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

	protected void addDefausseIntoColonne(int indexColonne) {
		Carte DefausseCard = pioche.defausse.getSommetCard();
		Colonne colonneCible = this.listColonne.get(indexColonne);

		try {
			colonneCible.addCard(DefausseCard);
			pioche.defausse.pullCard();
		} catch (Exception e) {
			throw e;
		}
	}

	protected boolean peutDeplacerListeCarte(int colSource, int indexDebut, int colDestination) {
		try {
			Colonne source = listColonne.get(colSource);
			Colonne destination = listColonne.get(colDestination);
			List<Carte> cartesADeplacer = new ArrayList<>();
			for (int i = indexDebut; i < source.getColonneVisible().size(); i++) {
				cartesADeplacer.add(source.getColonneVisible().get(i));
			}

			if (!destination.canAddListCardFromColonne(cartesADeplacer)) {
				return false;
			}
			destination.addListCard(cartesADeplacer);
			for (int i = source.getColonneVisible().size() - 1; i >= indexDebut; i--) {
				source.getColonneVisible().remove(i);
			}
			if (source.getColonneVisible().isEmpty() && source.getLongueurPaquet() > 0) {
				source.updateColonneVisible();
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deplacerCarteVersPieux(int colSource, int indexPieux) {
    try {
        Colonne source = listColonne.get(colSource);
        Pieux pieux = listPieux.get(indexPieux);
        
        if (source.getColonneVisible().isEmpty()) return false;
        
        Carte carte = source.getColonneVisible().get(0);
        pieux.addCard(carte);
        source.pullCardColonneVisible();
        
        if (source.getColonneVisible().isEmpty() && source.getLongueurPaquet() > 0) {
            source.updateColonneVisible();
        }
        
        return true;
        
    } catch (Exception e) {
        return false;
    }
}
}
