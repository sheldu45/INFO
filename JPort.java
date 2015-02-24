import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPort extends JPanel /*implements ActionListener*/{

	Flotte flotte ;
	
	public JPort(Flotte flotte) {
		this.flotte = flotte;
		JButton bouton 	= new JButton("Valider");
		//this.add(bouton, 14, 1);
		//this.setPreferredSize(new Dimension(6*PlateauCarre.largeurCase, 10*PlateauCarre.largeurCase));
	}
	
	public void paint (Graphics g){
		//this.setSize(new Dimension(this.getWidth() - (Partie.DimensionDesPlateaux*PlateauCarre.largeurCase), this.getHeight()));
		g.setColor(Color.yellow);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		String description = "Il vous reste " + flotte.nombreDeBateauxNonPlaces() + " bateaux à placer ...";
		g.setColor(Color.red);
		g.drawString(description, this.getWidth()/2 - 100 /*la moitié de la taille du message ?*/ , this.getHeight()/10);
		System.out.println("bla"+flotte.estEnMer());

		if(! flotte.estEnMer()){
			flotte.mettreAJourSelection();
			System.out.println("icicicicicici");
			String tailleDuBateauCourant = "Le prochain Bateau que vous devez placer fait " + flotte.bateauSelectionne.taille + " cases !" ;
			g.drawString(tailleDuBateauCourant, this.getWidth()/2 - 65 /*la moitié de la taille du message ?*/ , (this.getHeight()/10)*4);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame 	fenetreDeJeu = new JFrame();
		fenetreDeJeu.setTitle("Bataille Navale-verbale");				
		fenetreDeJeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
			fenetreDeJeu.setLocationRelativeTo(null);
			JPort pan = new	JPort(new Flotte()) ;  
			fenetreDeJeu.setContentPane(pan);	
			fenetreDeJeu.pack();
			fenetreDeJeu.setVisible(true);

	}

}
