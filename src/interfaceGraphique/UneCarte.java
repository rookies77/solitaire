package interfaceGraphique;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

class UneCarte extends EnsembleCarte{
	private ImageIcon image;

	public UneCarte(String aff,int ref, ClickReporter reporter,
			InterfaceCarte ic) {
		super(aff,ref,reporter,ic);
		//System.out.println("ic "+ic);
		this.setPreferredSize(new Dimension(86,150));
		Font font = this.getFont();
		this.setFont(new Font(font.getFontName(),Font.BOLD, //font.getStyle(),
					font.getSize()*2));
	}

	public void add(ICarte st) {
		//System.out.println("st "+st+" "+ic);
		if (st == null)
			image = null;
		else
			image = ic.getIconFromCard(st);
		this.repaint();		
	}
	public void set(ICarte st) {
		this.add(st);
	}

	public void paintComponent(Graphics g) {
		//g.setFont(ic.font);
		g.drawString(""+this.aff, 35, 20);	
		if (image == null) {
			g.setColor(Color.GREEN);
			g.fillRect(0, 30, 86, 120);
		}else {
			image.paintIcon(this,g,0,30);
			if (this.highlighted)
				jaune.paintIcon(this,g,0,30);
		}
	}

	class LaSouris extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseCLicked : "+e.toString());
			if (reporter != null )
				reporter.reportClick(ref);
		}	
	}
}
