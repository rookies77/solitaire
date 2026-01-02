package interfaceGraphique;

/**
 * Interface qui définit les méthodes appelées lors d'un click ou
 * de l'appui d'un bouton dans la fenêtre graphique d'un objet de
 * type InterfaceKlondike.
 */
public interface ClickReporter {
	/**
	 * Méthode appelée lors d'un click sur un tas de carte d'une 
	 * fenêtre graphique.
	 * 
	 * @param ref indice du tas dans lequel l'utilisateur a cliqué.
	 */
	void reportClick(int ref);

	/**
	 * Méthode appelée lors de l'appui sur un bouton de la fenêtre
	 * graphique.
	 * 
	 * @param identifier texte affiché sur le bouton.
	 */
	void buttonPressed(String identifier);

}
