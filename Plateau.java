import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class Plateau extends JPanel implements MouseListener {


    int largeurPlateau;
	Case[][] tab ;
	
	Flotte listeDesBateaux = new Flotte();
	
	boolean phaseDePositionnement ;
	boolean visible ;
	boolean finDeLaPartie = this.listeDesBateaux.isEmpty() ;
	
	//Instancie un bateau de taille et nombre de bateaux donnés
	public Plateau(int largeurPlateau) {
		this.largeurPlateau 		= largeurPlateau;
		this.tab = new Case[largeurPlateau][largeurPlateau];
		for(int i = 0; i < largeurPlateau; i++){
			for(int j = 0; j < largeurPlateau; j++){
				tab[i][j] = new Case(this);
			}
		}
	}


	
	
	private static boolean sontVides(Case[] positions){
		for (int i = 0; i < positions.length; i++) {
			if(positions[i].getBateau() != null) return false;
		}
		return true;
	}
	
	
	//Permet d'ajouter un Bateau 
	public void ajouter(Bateau bateau){
		//on ajoute le bateau à la liste des Bateaux du Plateau
		listeDesBateaux.add(bateau);
		//on fait pointer vers le bateau chacune des cases sur lesquels est le bateau
		for(int i = 0; i<bateau.taille; i++){ 
			bateau.positions[i].setBateau(bateau);
		}
	}
	
	

	//Permet d'ajouter un Bateau à un ensemble de positions données (à condition que ces positions soient libre)
	public void ajouter(Bateau bateau, Case[] positions){
	
	    if(bateau.estEnMer == false){
	       if (sontVides(positions)){
	    	   bateau.estEnMer = true ;
	    	   for (int i = 0; i < bateau.taille; i++) {	
	    		   bateau.positions[i] = positions[i];			//on donne au bateau les positions en argument
	    		   bateau.positions[i].setBateau(bateau);		//on fait pointer vers le bateau chacune des cases sur lesquels est le bateau
	    	   }
	       }
	    }
	    System.out.println(listeDesBateaux.nombreDeBateauxNonPlaces());
	    this.listeDesBateaux.mettreAJourSelection();
	    Partie.fenetreDeJeu.repaint();
	}
	
	//ajoute si c'est possible, le bateau entre les positions X1,Y1 et X2,Y2
	public abstract void ajouter(Bateau bateau, int X1, int Y1, int X2, int Y2);
	
	//ajoute si c'est possible, le bateauSelectionne de la flotte entre les positions X1,Y1 et X2,Y2
	public abstract void ajouter(int X1, int Y1, int X2, int Y2);

	
	
	
	//la fonction tirer ne sera de toute façon appellée QUE sur des cases qu'on n'a pas deja touché
	public void tirer (int x, int y) {
		assert tab[x][y].onADejaClique == false : "la fonction tirer ne doit être appellée QUE sur des cases qu'on n'a pas deja touché";
		//modification du boolean onADejaCliqueSurCetteCase
		tab[x][y].tirer();
		//si un bateau se trouve sur cette case, "TOUCHE !"
		if(this.tab[x][y].getBateau() != null){
			Bateau bateau = this.tab[x][y].getBateau();
			Case caseTouchee = tab[x][y];
			//On cherche l'indice des positions du bateau qui correspond à la case touchée
			for(int i = 0; i < bateau.taille; i++ ){
				if(bateau.positions[i] == caseTouchee){
					//à l'indice obtenu, on change le tableau booleen touches en true
					bateau.casesTouchees[i] = true ;
				}
			}
		}


	}
	
	
	
	
	
	public abstract void paint(Graphics g);
	// abstract Listeners 

	
	
	
	
	
	public Plateau(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Plateau(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Plateau(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
