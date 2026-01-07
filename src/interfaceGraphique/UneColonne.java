package interfaceGraphique;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
class UneColonne extends EnsembleCarte{
	private ArrayList<ImageIcon> tab;

	public UneColonne(String aff, int ref, ClickReporter reporter,
			InterfaceCarte ic) {
		super(aff,ref,reporter,ic);
		tab = new ArrayList<ImageIcon>();
		this.setPreferredSize(new Dimension(86,120+(25*20)));
		Font font = this.getFont();
		this.setFont(new Font(font.getFontName(),Font.BOLD, //font.getStyle(),
					font.getSize()*2));
	}

	@Override
	public void add(ICarte st) {
		// System.out.println("uneColonne nom fichier : "+st.getNomDeFichierPNG());
		tab.add(0,ic.getIconFromCard(st));
		this.repaint();
	}

	@Override
	public void set(ICarte st) {
		tab.clear();
		tab.add(ic.getIconFromCard(st));
		this.repaint();		
	}

	@Override
	public void add(ICarte[] strings) {		
		for (int i=0; i<strings.length; i++)
			tab.add(i,ic.getIconFromCard(strings[i]));
		this.repaint();			
	}

	@Override
	public void set(ICarte[] strings) {	
		tab.clear();
		for (ICarte st: strings)
			tab.add(ic.getIconFromCard(st));
		this.repaint();			
	}

	@Override
	public void removeCards(int nb) {
		for (int i=0; i<nb; i++) {
			tab.remove(0);
		}
		this.repaint();			
	}

	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		//g.setFont(ic.font);
		int y;
		if (tab.isEmpty()) 
			y=30;
		else
			y = 30+tab.size()*25+95;		
		g.drawString(""+this.aff, 35, 20);	
		for (int i=tab.size()-1, j=0; i>=0; i--, j++) {
			// System.out.println("uneColonne icon "+tab.get(i));
			tab.get(i).paintIcon(this,g,0,30+j*25);	
			if (this.highlighted)
				jaune.paintIcon(this,g,0,30+j*25);	
		}
		g.setColor(Color.GREEN);
		g.fillRect(0, y, 86, 120+(25*20)-y);
	}

}
