import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;


public class PlateauCarre extends Plateau {

	static int largeurCase = 50;
	Case caseSelectionne ;
	
	public PlateauCarre() {
		super();
		addMouseListener(this);
		//caseSelectionne = tab[5][3];
		this.setPreferredSize(new Dimension(largeurCase*Partie.DimensionDesPlateaux, largeurCase*Partie.DimensionDesPlateaux));
	}


	@Override
	public void paint(Graphics g) {

		for(int y=0; y< Partie.DimensionDesPlateaux; y++){

			for(int x=0; x<Partie.DimensionDesPlateaux; x++){

				g.drawImage(tab[x][y].getImage(), x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);

				if(tab[x][y].onADejaClique && tab[x][y].getBateau() == null){
					g.drawImage(Case.croix, x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);
				}

				if(tab[x][y].onADejaClique && tab[x][y].getBateau() != null){
					g.drawImage(Case.explosion, x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);
				}

				/*g.setColor(Color.RED);
				if (tab[x][y] == caseSelectionne) {
				    g.drawRect(largeurCase * x,largeurCase * y,largeurCase-1 , largeurCase-1 );
				    g.drawString("X",largeurCase * x + (1/2 * largeurCase ),largeurCase * y + (1/2 * largeurCase ));
				}*/

			}
		}	
	}
	
	
	
	@Override
	//ajoute si c'est possible, le bateau entre les positions (X1,Y1) et (X2,Y2)
	public void ajouter(Bateau bateau, int X1, int Y1, int X2, int Y2) {

		Case[] positions = new Case[bateau.taille];
		
		//bateau vertical
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

		//bateau horizontal
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

	@Override
	public void ajouter(int X1, int Y1, int X2, int Y2) {
		this.ajouter(this.listeDesBateaux.bateauSelectionne, X1, Y1, X2, Y2);
	}

	
	
	
	
	
	
	
       
	

    private int xDepart, yDepart ; // positions (x,y) en terme de cases (et non en terme de pixels) au moment de l'action "press"
	@Override
	public void mousePressed(MouseEvent e) {
	    //System.out.println("llaaaaaaa" + "" + e.getX() / largeurCase +"" + e.getY() / largeurCase);
		this.xDepart = e.getX() / largeurCase;
		this.yDepart = e.getY() / largeurCase;
	}
	
	private int xArrive, yArrive ;	// positions (x,y) en terme de cases (et non en terme de pixels) au moment de l'action "release"
	@Override
	public void mouseReleased(MouseEvent e) {
	    //System.out.println("à llaaaaaaa" + "" + e.getX() / largeurCase +"" + e.getY() / largeurCase);
		this.xArrive = e.getX() / largeurCase;
		this.yArrive = e.getY() / largeurCase;	

		//System.out.println();
		this.ajouter(xDepart, yDepart, xArrive, yArrive);
		this.repaint();
	}



	
	@Override
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

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	

	
	
	
	
	
	
	

	
	
	
	
	
	
	

	public PlateauCarre(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public PlateauCarre(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public PlateauCarre(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
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
