// package interfaceGraphique;
// import java.util.Scanner;

// import javax.swing.JButton;

// import interfaceGraphique.Carte.symboleCarte;
// import interfaceGraphique.Carte.valeurCarte;
// import testInterfaceGraphique.DemoCarte;
// public class AffichageKlondike {

// 	public static void main(String[] args) {
// 		// TODO Auto-generated method stub
// 		InterfaceKlondike ik;
// 		Carte as1, as2, as3;
// 		Scanner scan = new Scanner(System.in);
// 		int[] tab1 = {12};
// 		as1 = new Carte(valeurCarte.as, symboleCarte.pique);
// 		as2 =new Carte(valeurCarte.dame, symboleCarte.trefle);
// 		as3 = new Carte(valeurCarte.dos, symboleCarte.dos);

// 		ik = new InterfaceKlondike();
// 		ik.addCard(as3,1);
// 		ik.setMessage("Pour afficher la suite, tapez " +
// 				"<return> dans la console d'eclipse");
// 		scan.nextLine();
// 		ik.addCard(as1,8);
// 		ik.addCards(new Carte[] {as3,as3,as2},11);

// 		scan.nextLine();
// 		ik.setHighlighted(8, true);
// 		ik.addCard(new Carte(valeurCarte.valet, symboleCarte.dos),11);
		
// 		scan.nextLine();
// 		ik.clearMessage();
// 		ik.setCards(new Carte[] {as3,as3,as2},8);
// 		ik.removeFromPlace(3, 11);
// 		scan.nextLine();
// 		System.out.println("Bye");
// 		ik.close();
// 	}

// }
