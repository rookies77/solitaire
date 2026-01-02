package testInterfaceGraphique;

import interfaceGraphique.ICarte;

public class DemoCarte implements ICarte {

	private String nomFichier;
	
	public DemoCarte(String nf) {
		nomFichier = nf;
	}

	@Override
	public String getNomDeFichierPNG() {
		// System.out.println("getNomDeFichierPNG appelé pour " + nomFichier);
		return nomFichier;
	}
	
}
