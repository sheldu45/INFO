import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * JPort est une classe dérivée de JPanel servant à l'affichage d'une <a href="Flotte.html">Flotte</a>.
 * On affiche un JPort à côté d'un <a href="Plateau.html">Plateau</a> à la phase de positionnement des <a href="Bateau.html">Bateaux</a> sur le plateau afin de représenter quels bateaux restent à placer.
 * <p>
 * Dans un premier temps le JPort se contente d'écrire quel est la taille du prochain bateau à placer et le nombre de bateaux à placer restants. On place un par un, dans l'ordre, les bateaux de la flotte. 
 * <p>
 * Dans une version plus élaborée, il pourrait afficher un/des bateau(x). Eventuellement on fera intervenir les actions de la souris pour sélectionner un bateau à placer ou bien le glisser directement...
 */


public class JPort extends JPanel /*implements ActionListener*/{

	Flotte flotte ;
	

	/**
	 * On instancie un JPort en précisant la flotte qu'il représentera
	 */
	public JPort(Flotte flotte) {
		this.flotte = flotte;
		JButton bouton 	= new JButton("Valider");
		//this.add(bouton, 14, 1);
		//this.setPreferredSize(new Dimension(6*PlateauCarre.largeurCase, 10*PlateauCarre.largeurCase));
	}
	

	/**
	 * Méthode paint gérant l'affichage
	 * @param g
	 */
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
			String tailleDuBateauCourant = "Le prochain Bateau que vous devez placer fait " + flotte.bateauSelectionne.taille + " cases !" ;
			g.drawString(tailleDuBateauCourant, this.getWidth()/2 - 65 /*la moitié de la taille du message ?*/ , (this.getHeight()/10)*4);
		}
	}





	/**
	 * main de Test
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
