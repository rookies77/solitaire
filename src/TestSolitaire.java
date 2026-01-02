// Vincent WONG 2025
import java.util.ArrayList;

public class TestSolitaire {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        testDistributeur();
        testPieux();
        testDefausse();
        testColonne();
        testPioche();
    }

    private static void testDistributeur() {
        System.out.println("--- TEST DISTRIBUTEUR ----");
        Distributeur dist = new Distributeur();
        // Consultation sans retrait
        System.out.println("Longueur initiale: " + dist.getLongueurPaquet());
        System.out.println("Consultation sommet: " + dist.getSommetCard());

        Carte carte1 = dist.pullCard();
        System.out.println("Retrait: " + carte1 + " - Longueur: " + dist.getLongueurPaquet()); 

        Carte carte2 = dist.pullCard();
        System.out.println("Retrait: " + carte2 + " - Longueur: " + dist.getLongueurPaquet());

    }

    private static void testDefausse() {
        System.out.println("--- TEST DÉFAUSSE ----");

        Defausse defausse = new Defausse();
        Distributeur dist = new Distributeur();

        Carte carte1 = dist.pullCard();
        Carte carte2 = dist.pullCard();
        defausse.addCard(carte1);
        System.out.println("Ajoute la carte1: " + carte1 + " - Longueur: " + defausse.getLongueurPaquet());
        System.out.println("lecture de la premiere carte de la defausse" + defausse.getSommetCard());

        defausse.addCard(carte2);
        System.out.println("Ajoute la carte2: " + carte2 + " - Longueur: " + defausse.getLongueurPaquet());
        System.out.println("lecture de la nouvelle carte du dessus de la defausse" + defausse.getSommetCard());

        // Retrait
        Carte carteRetiree = defausse.pullCard();
        System.out.println("Retire une carte : " + carteRetiree + " - Longueur: " + defausse.getLongueurPaquet());

        try {
            defausse.getSommetCard();
            System.out.println("Pas d'exception sur défausse vide");
        } catch (Exception e) {
            System.out.println("il y'a une erreur ");
        }

    }

    private static void testColonne() {
        System.out.println("--- TEST COLONNE ---");

        // Test 1 : Colonne vide
        Colonne colonne = new Colonne(new ArrayList<>());
        System.out.println("Création d'une Colonne vide - Longueur: " + colonne.getLongueurPaquet());

        // Test 2 : Ajouter un Roi sur une colonne vide (valide)
        Carte roi = new Carte(Carte.valeurCarte.roi, Carte.symboleCarte.coeur);
        try {
            colonne.addCard(roi);
            System.out.println("Ajout réussi: " + roi);
        } catch (Exception e) {
            System.out.println("Erreur ajout roi: " + e.getMessage());
        }

        // Test 3 : Ajouter une carte qui respecte les règles (dame noire sur roi rouge)
        Carte dame = new Carte(Carte.valeurCarte.dame, Carte.symboleCarte.pique);
        try {
            colonne.addCard(dame);
            System.out.println("Ajout réussi: " + dame);
        } catch (Exception e) {
            System.out.println("Erreur ajout dame: " + e.getMessage());
        }

        // Test 4 : Colonne avec cartes cachées
        ArrayList<Carte> cartesCachees = new ArrayList<>();
        cartesCachees.add(new Carte(Carte.valeurCarte.cinq, Carte.symboleCarte.trefle));
        cartesCachees.add(new Carte(Carte.valeurCarte.quatre, Carte.symboleCarte.coeur));
        Colonne colonne2 = new Colonne(cartesCachees);
        System.out.println("Colonne avec 2 cartes cachées - Visible: " + colonne2.getCarteVisibleAuSommet());

        System.out.println("Affiche la Colonne visible: " + colonne.toString());
    }

    private static void testPioche() {
        System.out.println("--- TEST PIOCHE ---");

        Pioche pioche = new Pioche(new Distributeur());
        System.out.println("Pioche - Longueur: " + pioche.getLongueurPaquet());

        for (int i = 0; i < 5; i++) { // test sur 5 tours
            if (pioche.getLongueurPaquet() > 0) {
                pioche.pullCardAndAddDefausse(); // Ajoute 5 fois une carte à la défausse
                System.out.println("Tirage " + (i + 1) + " - Pioche: " + pioche.getLongueurPaquet() + ", Défausse: "
                        + pioche.getDefausse().getLongueurPaquet());
                if (pioche.getDefausse().getLongueurPaquet() > 0) {
                    System.out.println("  Sommet défausse: " + pioche.getDefausse().getSommetCard());
                }
            }
        }
    }

    private static void testPieux() {
        System.out.println("--- TEST PIEUX ----");

        Pieux pieux = new Pieux();
        System.out.println("Pieux créé - Longueur: " + pieux.getLongueurPaquet());

        // Test 1 : Ajouter un As (valide)
        Carte carteAs = new Carte(Carte.valeurCarte.as, Carte.symboleCarte.coeur);
        try {
            pieux.addCard(carteAs);
            System.out.println("Ajout valide: " + carteAs + " - Longueur: " + pieux.getLongueurPaquet());
            System.out.println("Lecture de la premiere carte : " + pieux.getSommetCard());
        } catch (Exception e) {
            System.out.println("Ajout invalide: " + carteAs + " - " + e.getMessage());
        }

        // Test 2 : Ajouter un 2 de coeur (valide - même symbole, succession)
        Carte carte2 = new Carte(Carte.valeurCarte.deux, Carte.symboleCarte.coeur);
        try {
            pieux.addCard(carte2);
            System.out.println("Ajout valide: " + carte2 + " - Longueur: " + pieux.getLongueurPaquet());
        } catch (Exception e) {
            System.out.println("Ajout invalide: " + carte2 + " - " + e.getMessage());
        }

        // Test 3 : Tenter d'ajouter un 2 de pique (invalide - mauvais symbole)
        Carte carte2Pique = new Carte(Carte.valeurCarte.deux, Carte.symboleCarte.pique);
        try {
            pieux.addCard(carte2Pique);
            System.out.println("Ajout valide: " + carte2Pique);
        } catch (Exception e) {
            System.out.println("Ajout correctement refusé: " + carte2Pique + " - " + e.getMessage());
        }
    }

}
