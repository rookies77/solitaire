// // Test d'intégration de l'interface graphique avec le backend
// import interfaceGraphique.InterfaceKlondike;
// import java.util.ArrayList;
// import java.util.List;

// public class TestInterfaceGraphique {
	
// 	public static void main(String[] args) {
// 		// Créer l'interface graphique
// 		InterfaceKlondike interfaceGraphique = new InterfaceKlondike();
		
// 		// Créer quelques cartes de test
// 		Carte as_coeur = new Carte(Carte.valeurCarte.as, Carte.symboleCarte.coeur);
// 		as_coeur.setVisible(true); // Carte visible
		
// 		Carte roi_pique = new Carte(Carte.valeurCarte.roi, Carte.symboleCarte.pique);
// 		roi_pique.setVisible(true);
		
// 		Carte dame_carreau = new Carte(Carte.valeurCarte.dame, Carte.symboleCarte.carreaux);
// 		dame_carreau.setVisible(true);
		
// 		Carte carte_cachee = new Carte(Carte.valeurCarte.valet, Carte.symboleCarte.trefle);
// 		carte_cachee.setVisible(false); // Carte cachée (dos)
		
// 		// Tester l'affichage dans la pioche (indice 0)
// 		List<Carte> pioche = new ArrayList<>();
// 		pioche.add(carte_cachee);
// 		interfaceGraphique.setTas(0, pioche);
		
// 		// Tester l'affichage dans la défausse (indice 1)
// 		List<Carte> defausse = new ArrayList<>();
// 		defausse.add(as_coeur);
// 		interfaceGraphique.setTas(1, defausse);
		
// 		// Tester l'affichage dans un pieu (indices 2-5)
// 		List<Carte> pieu1 = new ArrayList<>();
// 		pieu1.add(roi_pique);
// 		interfaceGraphique.setTas(2, pieu1);
		
// 		// Tester l'affichage dans une colonne (indices 6-12)
// 		List<Carte> colonne1 = new ArrayList<>();
// 		colonne1.add(carte_cachee);
// 		colonne1.add(carte_cachee);
// 		colonne1.add(dame_carreau);
// 		interfaceGraphique.setTas(6, colonne1);
		
// 		// Afficher un message
// 		interfaceGraphique.setMessage("Test d'affichage - Interface graphique connectée !");
		
// 		System.out.println("Interface graphique lancée avec succès !");
// 		System.out.println("Vérifiez les noms de fichiers PNG :");
// 		System.out.println("As de coeur: " + as_coeur.getNomDeFichierPNG());
// 		System.out.println("Roi de pique: " + roi_pique.getNomDeFichierPNG());
// 		System.out.println("Dame de carreau: " + dame_carreau.getNomDeFichierPNG());
// 		System.out.println("Carte cachée: " + carte_cachee.getNomDeFichierPNG());
// 	}
// }
