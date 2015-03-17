import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;


public class PlateauCarre extends Plateau {
	
	// taille de la case carree en pixels
	static int largeurCase = 50;
	// Case courante qui selectionnee ??
	Case caseSelectionne ;
	
	/** Constructeur de plateau qui ne prend rien en argument. Il qui execute 
         * le constructeur de la classe mere Plateau
	 */
	public PlateauCarre() {
		super();
		addMouseListener(this);

		//caseSelectionne = tab[5][3];

		this.setPreferredSize(new Dimension(largeurCase*Partie.DimensionDesPlateaux, largeurCase*Partie.DimensionDesPlateaux));
	}


	@Override
	/** fonction "paint" appelee par "repaint()" va permettre 	  
          * l'affichage du plateau de jeu. Elle parcourt le tableau de   	  
          * cases et dessine l'image adequoite selon s'il y a un bateau 	  
          * ou non sur la case. Si onADejaClique est true, elle affiche 	  
          * une croix sur l'image; si OnADejaCliquee est true qu'il n'y a 	  
          * plus de bateau, elle affiche une explosion par dessus.
	  * @param g objet graphique
	 */
	public void paint(Graphics g) {
		
		
			
		for(int y=0; y< Partie.DimensionDesPlateaux; y++){
			for(int x=0; x<Partie.DimensionDesPlateaux; x++){

				// la fonction getImage() de case retourne 				
	            // l'image dela mer s'il n'y a pas de bateau et l'image d'un bateau s'il y en a un.
				
				if(this.visible){
					g.drawImage(tab[x][y].getImage(), x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);
				}
				if(!this.visible){
					g.drawImage(Case.mer, x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);
				}

				if(tab[x][y].onADejaClique && tab[x][y].getBateau() == null){
					g.drawImage(Case.croix, x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);
				}

				if(tab[x][y].onADejaClique && tab[x][y].getBateau() != null){
					g.drawImage(Case.explosion, x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);
				}

				g.setColor(Color.RED);
				if (tab[x][y] == caseSelectionne) {
				    g.drawRect(largeurCase * x,largeurCase * y,largeurCase-1 , largeurCase-1 );
				    g.drawString("X",largeurCase * x + (1/2 * largeurCase ),largeurCase * y + (1/2 * largeurCase ));
				}

			}
				
		}

		
	}
	
	
	
	@Override
        /**ajoute si c'est possible, le bateau entre les positions (X1,Y1) et (X2,Y2)
	*@param bateau
        *@param X1 abscisse de la case de depart du click and drag
        *@param Y1 ordonnee de la case de depart du click and drag
        *@param X2 abscisse de la case d'arrivee du click and drag
        *@param Y2 ordonnee de la case d'arrivee du click and drag
        */
	public void ajouter(Bateau bateau, int X1, int Y1, int X2, int Y2) {
                // tableau de position pour le bateau place
		Case[] positions = new Case[bateau.taille];
		
		//cas d'un bateau place verticalement
		if((X1==X2 && (Math.abs(Y2 - Y1)+1 == bateau.taille))){  //si un bateau est positionné entre la case 8 et 12, sa taille est de 5 (= 12-8 +1)
		    if(Y2>Y1){
		        for(int i = 0; i<bateau.taille; i++){
				Case c = tab[X1][Y1+i];
				positions[i] = c;
			}
		    }
		    else{
			for(int i = 0; i<bateau.taille; i++){
			        Case c = tab[X1][Y1-i];
				positions[i] = c;
			}
		    }
		}

		// cas d'un bateau place horizontalement
		if((Y1==Y2 && (Math.abs(X2 - X1)+1 == bateau.taille))){
		    if(X2>X1){
			for(int i = 0; i<bateau.taille; i++){
				Case c = tab[X1+i][Y1];
				positions[i] = c;
			}
		    }
		    else{
				for(int i = 0; i<bateau.taille; i++){
				Case c = tab[X1-i][Y1];
				positions[i] = c;
			}
		    }
		}

		super.ajouter(bateau, positions);
	}

	
        /**fonction ajouter qui appelle la fonction ajouter qui prends en argument un bateau
         * (en plus des coordonnées du point d'arrivée et de départ)
	*@param X1 abscisse de la case de depart du click and drag
        *@param Y1 ordonnee de la case de depart du click and drag
        *@param X2 abscisse de la case d'arrivee du click and drag
        *@param Y2 ordonnee de la case d'arrivee du click and drag
        */
	public void ajouter(int X1, int Y1, int X2, int Y2) {
		this.ajouter(this.listeDesBateaux.bateauSelectionne, X1, Y1, X2, Y2);
	}

	
	
       
	// positions (x,y) en terme de cases (et non en terme de pixels) au moment de l'action "press"
        private int xDepart, yDepart ; 
	
        
        /**
         * Quand on clique sur une case et qu'on maintien la souris, la case cliquée est repérée par son abscisse (xDepart) et son
         * ordonnée (yDepart)
         * @param e événement "on clique sur un endroit du plateau"
         */
	public void mousePressed(MouseEvent e) {
	    //System.out.println("llaaaaaaa" + "" + e.getX() / largeurCase +"" + e.getY() / largeurCase);
		this.xDepart = e.getX() / largeurCase;
		this.yDepart = e.getY() / largeurCase;
	}
	
        
        /**
         * positions (x,y) en terme de cases (et non en terme de pixels) au moment de l'action "release"
         */
        private int xArrive, yArrive ;	
        
	
        /**
         * Quand on relâche la souris à un endroit du plateau après avoir cliqué et maintenu la souris, la case
         * d'arrivée du click and drag est repérée par ses coordonnées xArrive et yArrive et la fonction 
         * ajouter (qui prends en argument les coordonnées de la case de départ et d'arrivée ) est executée. On appelle
         * alors la fonction repaint() pour afficher le bateau qui a été ajouté sur le plateau.
         * @param e événement "on relache la souris"
         */
	public void mouseReleased(MouseEvent e) {
	    //System.out.println("à llaaaaaaa" + "" + e.getX() / largeurCase +"" + e.getY() / largeurCase);
		this.xArrive = e.getX() / largeurCase;
		this.yArrive = e.getY() / largeurCase;	

		//System.out.println();
		this.ajouter(xDepart, yDepart, xArrive, yArrive);
		this.repaint();
		
		if(this.listeDesBateaux.estEnMer()){
			this.phaseDePositionnement = false;
		}
	}



	
	
	public void mouseClicked(MouseEvent e) {
		
	    /*int x = e.getX();
		int y = e.getY();
		int col = x/largeurCase;
		int lig = y/largeurCase;
		
		caseSelectionne = tab[col][lig];
		System.out.println(col + lig);
		
		if( !  tab[col][lig].onADejaClique){
			this.tab[col][lig].tirer();
			this.repaint();
			}*/
		
		
		
	}

	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	

	
	
	
	
	
	
	

	
	
	
	
	
	
	


    public static void main(String[] gtezyhfjkg){

    JFrame f = new JFrame();
	PlateauCarre pq = new PlateauCarre();

	Bateau b1 = new Bateau (3);
	Bateau b2 = new Bateau (2);
	Bateau b3 = new Bateau (5);
	Bateau b4 = new Bateau (3);


	pq.listeDesBateaux.add(b1);
	pq.listeDesBateaux.add(b2);
	pq.listeDesBateaux.add(b3);
	pq.listeDesBateaux.add(b4);


	pq.listeDesBateaux.mettreAJourSelection();
	


	System.out.println(pq.tab[2][2].getBateau());



        f.setSize(new Dimension(Partie.DimensionDesPlateaux * largeurCase, Partie.DimensionDesPlateaux * largeurCase));
        
	f.setContentPane(pq);
	f.repaint();

	f.setResizable(true);
	
	f.setVisible(true);
    }

}
