package interfaceGraphique;

/**
 * Classe offrant une interface graphique pour afficher une patience
 * Klondike (solitaire) dans une nouvelle fenêtre. Si un objet
 * de type ClickReporter est fourni, cette interface permet également
 * d'être informé des actions de la souris dans cette fenêtre (click
 * sur un tas de carte ou appui sur un bouton).
 * 
 * La fenêtre comporte une zone recevant des boutons, une zone de texte
 * permettant d'afficher des messages et les différents tas du jeu de 
 * Klondike. Chacun des tas est identifié par un indice :
 * <ul><li> pioche :  indice 0</li>
 * <li> défausse : indice 1 </li>
 * <li> pieux : indices 2 à 5 </li>
 * <li> colonnes : indices 6 à 12 </li></ul>
 * 
 * Initialement, la fenêtre graphique ne comporte pas de boutons. Ceux-ci
 * peuvent être ajoutés au moyen de la méthode addButton.
 * 
 */

public class InterfaceKlondike{
	
	private static final long serialVersionUID = 1L;
	private InterfaceCarte ic;
	private EnsembleCarte[] tank = new EnsembleCarte[13];
	
	/**
	 * Crée un objet et affiche une nouvelle fenêtre graphique
	 * servant uniquement à l'affichage.
	 */
	public InterfaceKlondike() {
		 this(null);
	}
	
	/**
	 * Crée un objet qui crée une nouvelle fenêtre graphique 
	 * permettant de faire des affichages et d'informer l'objet
	 * de type ClickReporter donné en paramètre des actions de 
	 * la souris dans cette fenêtre graphique.
	 * 
	 * @param reporter l'objet qui sera informé des clicks et des 
	 * appuis de boutons dans la fenêtre.
	 */
	public InterfaceKlondike(ClickReporter reporter) {
		EnsembleCarte composant;
		ic = new InterfaceCarte(reporter);
		String[] idents = {" ","0","   ","A","B","C","D"};
		for (int i=0; i<7; i++) {
			if (i<3) {
				composant = new UneCarte(idents[i],i,reporter,ic);
				tank[i]=composant;
				ic.addComponent(composant);
			}else {
				composant = new UneCarte(idents[i],i-1,reporter,ic);
				ic.addComponent(composant);
				tank[i-1]=composant;
			}
		
		}
		char car = '1';
		for (int i=6; i<13; i++, car++) {
			composant=new UneColonne(""+car,i,reporter,ic);
			ic.addComponent(composant);
			tank[i]=composant;
		}
		ic.makeGrid();
		ic.pack();
		ic.setVisible(true);
	}

	/** 
	 * Ajoute une carte dans un des tas de cartes affichés.
	 * 
	 * Cette carte sera la seule visible si le tas est la pioche ou
	 * un pieu. Elle sera ajoutée à celles déjà visible si le tas est
	 * une colonne.
	 * 
	 * @param carte la carte à ajouter
	 * @param comp  l'indice du tas auquel ajouter la carte.
	 */
	public void addCard(ICarte carte, int comp) {
		//System.out.println(carte+" "+icarteToString(carte));
		tank[comp].add(carte);
		//tank[comp].repaint();
	}
	
	/**
	 * Définit le nouveau contenu d'un tas de cartes. 
	 * 
	 * S'il y avait déjà des cartes dans ce tas, celles-ci sont retirées.
	 * La carte donnée en paramètre est ajoutée. Si ce paramètre vaut null,
	 * le tas est vide.
	 * @param carte la seule carte qui sera dans le tas ou null si le tas est vide.
	 * @param comp l'indice du tas recevant la carte.
	 */
	public void setCard(ICarte carte, int comp) {
		//System.out.println("setCard " + comp + " tank "+tank[comp]);
		tank[comp].set(carte);	
	}
	
	/**
	 * Ajoute plusieurs cartes à un tas.
	 * 
	 * @param tab tableau contenant les cartes à ajouter
	 * @param comp indice du tas recevant les cartes
	 * @exception UIKlondikeException est levée si le tas n'est pas une colonne
	 */
	public void addCards(ICarte[] tab, int comp) {
		tank[comp].add(tab);	
	}
	
	/**
	 * Définit l'ensemble des cartes d'un tas.
	 * 
	 * S'il y a des cartes dans le tas à l'appel de cette méthodes, celles-ci sont
	 * retirées avant l'ajout des cartes données par le paramètre tab.
	 * @param tab tableau contenant les cartes du tas
	 * @param comp indice du tas recevant les cartes
	 * @exception UIKlondikeException est levée si le tas n'est pas une colonne
	 */
	public void setCards(ICarte[] tab, int comp) {
		tank[comp].set(tab);			
	}
	
	/**
	 * Vide le tas de numéro comp de toutes ses cartes.
	 * 
	 * @param comp indice du tas recevant les cartes
	 */
	public void clear(int comp) {
		this.setCard(null,comp);
	}
	
	/**
	 * Enlève une certain nombre de cartes du sommet d'un tas.
	 * 
	 * @param nb nombre de carte à retirer
	 * @param comp indice du tas auquel les cartes sont retirées.
	 */
	public void removeFromPlace(int nb, int comp) {
		tank[comp].removeCards(nb);
	}
	
	/**
	 * Ferme la fenêtre d'affichage.
	 */
	public void close() {
		ic.dispose();
	}

	/**
	 * Définit le caractère surligné ou non d'un tas (avec une couleur jaune)
	 * 
	 * @param comp indice du tas concerné par le surlignage
	 * @param b booléen définissant si le tas est surligné ou non
	 */
	public void setHighlighted(int comp, boolean b) {
		tank[comp].setHighlighted(b);
	}
	
	/**
	 * Affiche un message dans la zone de texte de l'affichage.
	 * 
	 * @param string texte du message à afficher
	 */
	public void setMessage(String string) {
		ic.setMessage(string);		
	}

	/**
	 * Efface le contenu de la zone de texte de l'affichage.
	 */
	public void clearMessage() {
		ic.clearMessage();		
	}

	/**
	 * Ajoute un bouton à la fenêtre graphique.
	 * 
	 * @param ident texte affiché sur le nouveau bouton
	 */
	public void addButton(String ident) {
		ic.ajouterBouton(ident);
	}
}
