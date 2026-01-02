package interfaceGraphique;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InterfaceCarte extends JFrame {
	private HashMap<String,ImageIcon> cartes = new HashMap<String,ImageIcon>();
	private JPanel boardPanel = new JPanel(new SpringLayout());
	private JPanel content = new JPanel(new GridBagLayout());
	private JPanel boutons = new JPanel();
	private JTextArea message = new JTextArea(2,34); 
	private int nbCols = 7, nbLigs = 2;
	private ButtonListener butlist;
	protected Font font;
	private ClickReporter reporter;
	public InterfaceCarte(ClickReporter report) {
		this.reporter = report;
		butlist = new ButtonListener();
		String[] vals = {"as", "2", "3","4", "5", "6","7","8","9",
				"10", "valet", "dame", "roi"};
		String[] couls = {"pique", "coeur", "carreau", "trefle"};
		Font font;
		for(String val: vals)
			for (String coul: couls) {
				String st = val + "_de_" + coul;
				cartes.put(st+".png", new ImageIcon("cards/" + st + ".png"));
			}
		cartes.put("dos.png",new ImageIcon("cards/dos.png"));
		cartes.put("jaune.png",new ImageIcon("cards/jaune.png"));
		boardPanel.setBackground(Color.green);
		boutons.setBackground(Color.green);
		font = boardPanel.getFont();
		//System.out.println(font.getFamily() + " " + font.getFontName() + " "
		//		+ font.getSize());
		boardPanel.setFont(new Font(font.getFontName(),Font.BOLD, //font.getStyle(),
					font.getSize()*2));
		boutons.setFont(new Font(font.getFontName(),Font.BOLD, //font.getStyle(),
				font.getSize()*2));
		message.setFont(new Font(font.getFontName(),Font.PLAIN, 
				font.getSize()*2));
		//this.ajouterBouton("toto");
		boutons.setPreferredSize(new Dimension(boardPanel.getWidth(),50));
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 0;
		cons.gridy = 0;
		content.add(boutons,cons);
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 0;
		cons.gridy = 1;
		content.add(message,cons);
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 0;
		cons.gridy = 2;
		content.add(boardPanel,cons);
		this.setContentPane(content);
	}
	public void addComponent(JComponent comp) {
		this.boardPanel.add(comp);
	}
	public ImageIcon getIconFromCard(ICarte st) {
		return cartes.get(st.getNomDeFichierPNG());
	}
	public void makeGrid() {
		SpringUtilities.makeCompactGrid(boardPanel, nbLigs, nbCols, 10,
				10, 10, 10);
	}

	public void setMessage(String string) {
		message.setText(string);		
	}
	public void clearMessage() {
		message.setText("");		
	}

	public void ajouterBouton(String ident) {
		JButton newbutton = new JButton(ident);
		newbutton.setFont(boardPanel.getFont());
		newbutton.addActionListener(butlist);
		//System.out.println("boutons getsize " + boutons.getSize());
		boutons.add(newbutton);
		boutons.updateUI();
	}
	protected class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("button pressed ");
			System.out.println(arg0);
			reporter.buttonPressed(arg0.getActionCommand());
		}

	}
	public ImageIcon getJaune() {
		return cartes.get("jaune.png");
	}
}
