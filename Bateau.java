/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jsimoe53
 */

public class Bateau {

	
    int 		taille;		    
    int 		tailleRestante ;
    String 		mot;				// la taille du mot doit être égale à  la taille du bateau
    Case[] 		positions;			// tableau de Cases qui indique oÃ¹ se trouve le bateau sur le plateau
    boolean[]	casesTouchees;		// tableau de la taille du bateau qui indique Ã  chaque morceu du bateau s'il ce morceau est touchÃ© (true) ou non (false)
   
    boolean		estEnMer;			// le bateau est placé sur la carte
    boolean 	estCoule;
    
    public Bateau(int taille) {

        assert mot.length() == taille : "la taille du mot ne correspond pas à la taille du bateau!";

        this.taille = taille;
        this.tailleRestante = taille;
        this.positions = new Case[taille];
        this.casesTouchees = new boolean[taille];


		for(int i = 0; i<this.taille; i++){ 
			this.casesTouchees[i] = false ; 		// aucune case n'est touchée
		}


        for (int i = 0; i < taille-1; i++) {
            this.positions[i] = positions[i];
            this.casesTouchees[i] = false;
        }

        
    }
    
    //en argument la reference à la case touchée
    public void touche (Case caseTouchee) {
    	
    	assert taille != 0 : "on a touché un bateau déjà coulé";
    	
    	for (int i = 0; i <= taille-1; i++) {	
            if(this.positions[i] == caseTouchee){
            	this.casesTouchees[i] = true;		//on modifie le tableau décrivant quelles cases ont été touchée ou non
            }
        }
    	this.taille -- ;							//on diminue la taille
    }


   public boolean estEnMer(){
	   return this.estEnMer ;
   }
   
   public boolean estCoule(){
	   this.estCoule = tailleRestante == 0 ;
	   return estCoule;
   }
    



    public static void main (String [] zghadjzevhafzea){
	Bateau b = new Bateau (1);
	

    }

}
