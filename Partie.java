import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Partie {
	
	/*
	 * 
	 */
	
	public static JFrame fenetreDeJeu	; 	//C'est la fenetre de jeu, à tout moment, partout, on pourra utiliser "Partie.fenetreDeJeu.repaint()"

	
	
	static int 		DimensionDesPlateaux  = 10 ;	
		
	private int 	typeDePartie 	; 	// '1' <=> vs ordinateur 	; '2' <=> joueur1 vs joueur2	//mais il serait aussi possible d'imaginer une classe abstraite Partie et deux sous classes		
	
	private int 	typeDeCases		;	//	'4' <=> carré 			; '6' <=> hexagonal
	
	private int[]	typeDeFlotte	;	//tableau d'entiers : à la i-ème case est indiqué le nombre de bateaux de taille i par joueur

	private int     phaseDeLaPartie	;	//0 durant la phase du placement 1 durant la phase de jeu et 2 durant la phase de partie terminée
	
	private int		tourDuJoueur	; 	//1 au tour du Joueur1 et 2 au tour du Joueur2
	
	private Plateau plateauDuJoueur1	;	//plateau du joueur1
	
	private Plateau plateauDuJoueur2	;	//plateau du joueur2
		
	public String 	nomDuJoueur1	;	//nom du Joueur1
	
	public String 	nomDuJoueur2	;	//nom du Joueur2
	
	
	public Partie(int typeDePartie, int typeDeCases, int[] typeDeFlotte){

		assert typeDeFlotte[0]  == 0 : 	"car il doit y avoir 0 bateaux de taille 0 ! typeDeFlotte est un tableau qui à chaque indice i indique le nombre de bateaux de taille i par joueur ";
	    assert typeDeFlotte[1]  == 0 : 	"car il doit y avoir 0 bateaux de taille 1 ! ";
		
		this.typeDePartie 		= typeDePartie;
		this.typeDeCases 		= typeDeCases;
		this.typeDeFlotte 		= typeDeFlotte;
		this.phaseDeLaPartie	= 0;
		this.tourDuJoueur 		= 1;
		
		switch(typeDeCases){			// 4 pour des cases carrées	;	6 pour des cases hexagonales 
			case 4 :
				plateauDuJoueur1 = new PlateauCarre();
				plateauDuJoueur2 = new PlateauCarre();
				break;
				
			case 6 :
				plateauDuJoueur1 = new PlateauHexagonal();
				plateauDuJoueur2 = new PlateauHexagonal();
				break;
		}
		
		for(int i = 0; i < typeDeFlotte.length; i++){ 	// on parcours toutes les valeurs int du tableau. A un indice i, on a le nombre k de bateau de taille i par joueur
			
			for(int kiemeBateauDeTaillei = 0 ; kiemeBateauDeTaillei < typeDeFlotte[i] ; kiemeBateauDeTaillei++){	//on ajoute k fois un bateau de taille i à chacune des deux flottes
				plateauDuJoueur1.listeDesBateaux.add(new Bateau(i));
				plateauDuJoueur2.listeDesBateaux.add(new Bateau(i));
			}
		}
		this.commencerLaPartie();
	}
	
	private void commencerLaPartie() {

		/*
	*  (0)		On instancie la fenêtre de jeu 
	* 	  (a)			on lui donne un titre 
	* 	  (b)			une croix rouge en haut à droite
	* 	  (c)			des dimensions de 2/3 de l'écran, 
	*	  (d)			on place la fenêtre au milieu 
	*	  (e)			on la rend visible
	*/
		
	/*(0)*/		fenetreDeJeu = new JFrame();
		/*(a)*/			fenetreDeJeu.setTitle("Bataille Navale-verbale");				
		/*(b)*/			fenetreDeJeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
			
								//on  reccupère les dimensions de l'écran pour dimensionner la fenêtre de jeu à 2/3 de l'écran
								Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
								int largeurDeLEcran = (int)tailleEcran.getWidth();
								int hauteurDeLEcran = (int)tailleEcran.getHeight();
								int largeurDeLaFenêtreDeJeu = (largeurDeLEcran/3)*2; 
								int hauteurDeLaFenêtreDeJeu = (hauteurDeLEcran/3)*2; 
							
		/*(c)*/			fenetreDeJeu.setSize(largeurDeLaFenêtreDeJeu, hauteurDeLaFenêtreDeJeu);	
		/*(d)*/			fenetreDeJeu.setLocationRelativeTo(null);
		/*(e)*/			fenetreDeJeu.setVisible(true);
						fenetreDeJeu.setResizable(false);
						fenetreDeJeu.setLayout(new BorderLayout());
					
		
		
		
		switch(typeDePartie){
		
			case 2 :
				
				miseEnPlace(1);
				miseEnPlace(2);
				this.phaseDeLaPartie = 1;
				
				break;
				
				
		}
						fenetreDeJeu.setBackground(Color.BLACK);
						GridLayout gl = new GridLayout(1, 2);
						gl.setHgap(10);
						fenetreDeJeu.setLayout(gl);
		//quand PhaseDeLaPartie sera 2, on passera à la phase de partie terminée ; tant qu'elle est 1, c'est la phase de jeu
		while(phaseDeLaPartie == 1){
			tour(1);
		}
		
		//c'est la fin de la partie, voulez-vous rejouer?
		while(phaseDeLaPartie == 2){
			
		}
		
	}

	public void miseEnPlace(int joueur){
		
		if (joueur == 1){ 
			plateauDuJoueur1.visible = true ;
		    plateauDuJoueur1.setMinimumSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			plateauDuJoueur1.setPreferredSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			
			JPort  port    = new JPort(plateauDuJoueur1.listeDesBateaux);
			port.setMinimumSize(new Dimension(fenetreDeJeu.getWidth(), 150));
			port.setPreferredSize(new Dimension(fenetreDeJeu.getWidth(), 150));
			port.setBackground(Color.red);
			
			fenetreDeJeu.add(plateauDuJoueur1, BorderLayout.CENTER);
			fenetreDeJeu.add(port, BorderLayout.SOUTH);
	
		    fenetreDeJeu.pack();
		    fenetreDeJeu.repaint();
		    
		    while(plateauDuJoueur1.phaseDePositionnement){
		    	System.out.println();
			}

			fenetreDeJeu.remove(plateauDuJoueur1);
			fenetreDeJeu.remove(port);
		}
		
		if (joueur == 2){ 
			plateauDuJoueur2.visible = true ;
		    plateauDuJoueur2.setMinimumSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			plateauDuJoueur2.setPreferredSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			
			JPort  port2    = new JPort(plateauDuJoueur2.listeDesBateaux);
			port2.setMinimumSize(new Dimension(fenetreDeJeu.getWidth(), 150));
			port2.setPreferredSize(new Dimension(fenetreDeJeu.getWidth(), 150));
			port2.setBackground(Color.red);
			
			fenetreDeJeu.add(plateauDuJoueur2, BorderLayout.CENTER);
			fenetreDeJeu.add(port2, BorderLayout.SOUTH);
	
		    fenetreDeJeu.pack();
		    fenetreDeJeu.repaint();
		    
		    while(plateauDuJoueur2.phaseDePositionnement){
		    	System.out.println();	
			}

			fenetreDeJeu.remove(plateauDuJoueur2);
			fenetreDeJeu.remove(port2);
		}
		
	}
	
	
	public void tour(int joueur){
		
		if (tourDuJoueur == 1){ 
			plateauDuJoueur1.visible = true ;
			plateauDuJoueur2.visible = false ;
			
			plateauDuJoueur1.setMinimumSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			plateauDuJoueur1.setPreferredSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			plateauDuJoueur2.setMinimumSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			plateauDuJoueur2.setPreferredSize(new Dimension(Partie.DimensionDesPlateaux*PlateauCarre.largeurCase, Partie.DimensionDesPlateaux*PlateauCarre.largeurCase));
			
			fenetreDeJeu.add(plateauDuJoueur1);
			fenetreDeJeu.add(plateauDuJoueur2);
			
			fenetreDeJeu.pack();
		    fenetreDeJeu.repaint();
		}
	}
	


	public static void main(String[] args) {
		Partie p = new Partie(2, 4, new int[] {0, 0, 2});
	}

}
