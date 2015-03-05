import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class Plateau extends JPanel implements MouseListener {

    /**
       tableau bidimensionnel de cases qui représente le plateau
    */
    Case[][] tab ;
    /**
       liste des bateaux 
     */
    Flotte listeDesBateaux = new Flotte();
	
    boolean phaseDePositionnement ;
    boolean visible ;
    boolean finDeLaPartie = this.listeDesBateaux.isEmpty() ;
	
    /**

     *Instancie un bateau de taille nombre de bateaux donnés

     */
    public Plateau() {
	this.tab = new Case[Partie.DimensionDesPlateaux][Partie.DimensionDesPlateaux];
	for(int i = 0; i < Partie.DimensionDesPlateaux; i++){
	    for(int j = 0; j < Partie.DimensionDesPlateaux; j++){
		tab[i][j] = new Case(this);
	    }
	}
    }


    /**
     * sert au moment d'ajouter des bateaux si oui ou non les cases sont vides 
     * @return true s'il n'y a pas de bateau à la i-ème case 
     * @param  position tableau de positions
     */

    private static boolean sontVides(Case[] position){
	for (int i = 0; i < position.length; i++) {
	    if(position[i].getBateau() != null) return false;
	}
	return true;
		
    }
	
    /** 
     * permet d'ajouter un bateau à la liste des Bateaux de plateau 
     * on fait pointer vers le bateau chacune des cases sur lesquels est le bateau
     * @param bateau bateau à ajouter
     */
    public void ajouter(Bateau bateau){
	listeDesBateaux.add(bateau);
	for(int i = 0; i<bateau.taille; i++){ 
	    bateau.positions[i].setBateau(bateau);
	}
    }
	
	

    //Permet d'ajouter un Bateau à un ensemble de positions données (à condition que ces positions soient libre)

    /**
     * permet d'ajouter un bateau à des positions données si elles sont libres 
     * @param bateau bateau à ajouter
     * @param positions tableau de cases
     */
    protected void ajouter(Bateau bateau, Case[] positions){
	
	if(bateau.estEnMer == false){
	    if (sontVides(positions)){
		bateau.estEnMer = true ;
		for (int i = 0; i < bateau.taille; i++) {	
		    bateau.positions[i] = positions[i];		//on donne au bateau les positions en argument
		    bateau.positions[i].setBateau(bateau);		//on fait pointer vers le bateau chacune des cases sur lesquels est le bateau
		}
	    }
	    //System.out.println(listeDesBateaux.nombreDeBateauxNonPlaces());
	    this.listeDesBateaux.mettreAJourSelection();
	    Partie.fenetreDeJeu.repaint();
	}
    }
	
    //ajoute si c'est possible, le bateau entre les positions X1,Y1 et X2,Y2
    /**
     * methode abstraite qui ajoutera si possible un bateau entre les cases (X1,Y1) et (X2, Y2)
     * @param bateau bateau à ajouter 
     * @param X1 position de la case sur l'axe des abscisses (horizontal) ou l'on commence l'ajout du bateau 
     * @param X2 position de la case sur l'axe des abscisses (horizontal) ou l'on finit l'ajout du bateau 
     * @param Y1 position de la case sur l'axe des ordonnées (vertical) ou l'on commence l'ajout du bateau 
     * @param Y2 position de la case sur l'axe des ordonnées (vertical) ou l'on finit l'ajout du bateau 
     */
    public abstract void ajouter(Bateau bateau, int X1, int Y1, int X2, int Y2);
	
    //ajoute si c'est possible, le bateauSelectionne de la flotte entre les positions X1,Y1 et X2,Y2

    /**
     * méthode abstraite qui ajoutera si possible le bateauSelectionne de la flotte entre les cases (X1,Y1) et (X2,Y2)
     * @param X1 position de la case sur l'axe des abscisses (horizontal) ou l'on commence l'ajout du bateau 
     * @param X2 position de la case sur l'axe des abscisses (horizontal) ou l'on finit l'ajout du bateau 
     * @param Y1 position de la case sur l'axe des ordonnées (vertical) ou l'on commence l'ajout du bateau 
     * @param Y2 position de la case sur l'axe des ordonnées (vertical) ou l'on finit l'ajout du bateau 

     */
    public abstract void ajouter(int X1, int Y1, int X2, int Y2);

	/**
         * Fonction qui est exécutée quand on clique sur une case au cours de la partie pour viser un bateau. Elle n'est appelée
         * que sur des cases qui n'ont pas déjà été touchées. (Il faut qu'elle ne soit appelée que sur des cases qui
         * n'ont pas déjà été touchées?). Elle exécute la fonction "tirer" de la classe "Case". Si un bateau se trouve
         * sur la case cliquée (plus précisemmant une portion "i" d'un certain bateau), le boolean "caseTouchee" 
         * correspondant à la portion du bateau touchée (bateau.caseTouchee[i]) est changée en true.
         * @param x abscisse de la case cliquée
         * @param y ordonnée de la case cliquée
         */
        //la fonction tirer ne sera de toute façon appellée QUE sur des cases qu'on n'a pas deja touché ??
	public void tirer (int x, int y) {
            
		assert tab[x][y].onADejaClique == false : "la fonction tirer ne doit être appellée QUE sur des cases qu'on n'a pas deja touché";
                
                // la fonction tirer() de la case tab[x][y] change le boolean OnADejaClique de tab[x][y] en true
                // Elle execute la fonction touche() du bateau en question
                // Si la portion touchée était la dernière portion non touchée du bateau, elle coule le bateau et la retire
                // de la liste générale des bateau 
                tab[x][y].tirer();
                
                //si un bateau se trouve sur cette case, "TOUCHE !"
                if(this.tab[x][y].getBateau() != null){
                    
			Bateau bateau = this.tab[x][y].getBateau();
			Case caseTouchee = tab[x][y];
                        
			//On cherche l'indice des positions du bateau qui correspond à la case touchée
			for(int i = 0; i < bateau.taille; i++ ){
				if(bateau.positions[i] == caseTouchee){
				//à l'indice obtenu, on change le booleen de la case à l'indice i du 
                                //tableau caseTouchees en true
					bateau.casesTouchees[i] = true ;
				}
			}
		}


	}
	
	




	/**
         * Méthode abstraite "paint" de plateau implémentée dans PlateauCarre et PlateauHexagonal
         * @param g objet graphique
         */
	public abstract void paint (Graphics g);
	// abstract Listeners 
	
	
        
	

}
