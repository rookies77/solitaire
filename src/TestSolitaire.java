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

        Colonne colonne = new Colonne(new ArrayList<>());
        System.out.println("Création d'une Colonne " + colonne.getLongueurPaquet());

        Distributeur distributeur = new Distributeur();

        // Ajouter quelques cartes à la colonne
        for (int i = 0; i < 5; i++) {
            if (distributeur.getLongueurPaquet() > 0) {
                Carte carte = distributeur.pullCard();
                colonne.addCard(carte); // Ajoute une carte dans colonneVisible et non dans le paquet caché
                System.out.println("Ajouté: " + carte + " - Longueur colonne: " + colonne.getLongueurPaquet());
            }
        }

        System.out.println("Affiche la Colonne visible: " + colonne.getColonneVisible());

        if (colonne.getLongueurPaquet() > 0) {
            System.out.println("Sommet de la colonne: " + colonne.getSommetCard());
        }
    }

    private static void testPioche() {
        System.out.println("--- TEST PIOCHE ---");

        Pioche pioche = new Pioche(new Distributeur());
        System.out.println("Pioche - Longueur: " + pioche.getLongueurPaquet());

        for (int i = 0; i < 5; i++) { // test sur 5 tours
            if (pioche.getLongueurPaquet() > 0) {
                pioche.pullCardAndAddDefausse(); // Ajoute 5 fois une carte à la défausse
                System.out.println("Tirage " + (i + 1) + " - Pioche: " + pioche.getLongueurPaquet() + ", Défausse: "
                        + pioche.defausse.getLongueurPaquet());
                if (pioche.defausse.getLongueurPaquet() > 0) {
                    System.out.println("  Sommet défausse: " + pioche.defausse.getSommetCard());
                }
            }
        }
    }

    private static void testPieux() {
        System.out.println("--- TEST PIEUX ----");

        Pieux pieux = new Pieux();
        System.out.println("Pieux créé - Longueur: " + pieux.getLongueurPaquet());

        Carte carteAs = new Carte(Carte.valeurCarte.as, Carte.symboleCarte.coeur); // Création d'un as pour le test

        try {
            pieux.addCard(carteAs);
            System.out.println("Ajout valide: " + carteAs + " - Longueur: " + pieux.getLongueurPaquet());
            System.out.println("lecture de la premiere carte : " + pieux.getSommetCard());
        } catch (Exception | Error e) {
            System.out.println("Ajout invalide: " + carteAs + " - " + e.getMessage());
        }
    }

}
