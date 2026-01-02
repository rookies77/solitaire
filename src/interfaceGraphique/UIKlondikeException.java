package interfaceGraphique;

/**
 * Exception levée lors de certaines des erreurs détectées dans
 * des méthodes de la classe InterfaceKlondike.
 */
public class UIKlondikeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Crée une nouvelle exception contenant le message donné
	 * en paramètre.
	 * 
	 * @param message le message contenu dans l'exception
	 */
	public UIKlondikeException(String message) {
		super(message);
	}
	
}
