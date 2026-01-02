package testInterfaceGraphique;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;

import interfaceGraphique.ClickReporter;
import interfaceGraphique.InterfaceKlondike;
public class TestIGKlondike implements ClickReporter{

	private InterfaceKlondike ik;
	private Random random = new Random();
	private DemoCarte[] tab = new DemoCarte[20];

			
	public TestIGKlondike() {
		ik = new InterfaceKlondike(this);
		ik.addButton("surligner");
		ik.addButton("carte");
		int i = 0;
		for (String st1 : new String[] {"as","roi","dame","valet","10"})
			for (String st2 : new String[] {"pique","coeur","carreau","trefle"}){
				tab[i] = new DemoCarte(st1 + "_de_" + st2 + ".png");
				i++;
			}
	}
	
	@Override
	public void reportClick(int ident) {
		ik.setMessage(" click reporté =="+ident+"==");	
		afficheUneCarteEnPlus(ident);
	}

	private void afficheUneCarteEnPlus(int ident) {
		int indiceCarte = random.nextInt(20);
		System.out.println("afficheUneCarteEnPlus : indiceCarte = " + indiceCarte);
		ik.addCard(tab[indiceCarte], ident);
	}

	@Override
	public void buttonPressed(String identifier) {
		ik.setMessage(" bouton " + identifier + " pressed");
		if (identifier.equals("surligner"))
			ik.setHighlighted(random.nextInt(13), true);
		else
			afficheUneCarteEnPlus(random.nextInt(13));
	}

		

	public static void main(String[] args) {
		new TestIGKlondike();
	}

}
