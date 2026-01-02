
import java.util.Scanner;

import interfaceGraphique.InterfaceKlondike;

public class Affichage5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfaceKlondike ik;
		Carte roiCoeur, asTrefle, dos, troisPique, quatrePique, cinqPique;
		Scanner scan = new Scanner(System.in);

		roiCoeur = new Carte(Carte.valeurCarte.roi, Carte.symboleCarte.coeur);
		asTrefle = new Carte(Carte.valeurCarte.as, Carte.symboleCarte.trefle);
		dos = new Carte(null, null);
		troisPique = new Carte(Carte.valeurCarte.trois, Carte.symboleCarte.pique);
		quatrePique = new Carte(Carte.valeurCarte.quatre, Carte.symboleCarte.pique);
		cinqPique = new Carte(Carte.valeurCarte.cinq, Carte.symboleCarte.pique);

		ik = new InterfaceKlondike();
		ik.addCard(dos, 0);
		ik.addCard(roiCoeur, 1);
		ik.addCard(asTrefle, 4);
		ik.setCards(new Carte[] { dos, dos, dos }, 9);
		ik.setCards(new Carte[] { troisPique, quatrePique, cinqPique }, 10);
		ik.setHighlighted(1, true);
		ik.setHighlighted(10, true);
		ik.setMessage("Pour afficher la suite, tapez " +
				"<return> dans la console d'eclipse");
	
		scan.nextLine();
		System.out.println("Bye");
		ik.close();
	}

}
