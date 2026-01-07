package interfaceGraphique;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

abstract class EnsembleCarte extends JPanel {
	protected int ref;
	protected String aff;
	protected ClickReporter reporter;
	protected ImageIcon jaune;
	protected InterfaceCarte ic;
	protected boolean highlighted = false;

	public EnsembleCarte(String aff, int ref, ClickReporter reporter,
			InterfaceCarte ic) {
		this.reporter = reporter;
		this.ref = ref;
		this.aff = aff;
		this.addMouseListener(new LaSouris());
		this.jaune = ic.getJaune();
		this.ic = ic;
	}

	public void setHighlighted(boolean highlighted) {
		if (highlighted != this.highlighted) {
			this.highlighted = highlighted;
			this.repaint();
		}
	}

	class LaSouris extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// System.out.println("click sur la colonne n° : " + ref + " mouseevent " );
			if (reporter != null){
				reporter.reportClick(ref);
			}
		}
	}

	public void add(ICarte[] strings) {
		throw new UIKlondikeException("not implemented");
	}

	public void set(ICarte[] strings) {
		throw new UIKlondikeException("not implemented");
	}

	public void removeCards(int nb) {
		throw new UIKlondikeException("not implemented");
	}

	public abstract void add(ICarte strings);

	public abstract void set(ICarte strings);

}
