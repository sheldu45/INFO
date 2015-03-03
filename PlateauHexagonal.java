import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;


public class PlateauHexagonal extends Plateau {

static int largeurCase = 50;
	Case caseSelectionne ;	

	public PlateauHexagonal() {
	
	}

       
	@Override
	public void paint(Graphics g) {
		for(int y=0; y< Partie.DimensionDesPlateaux; y++){
			for(int x=0; x<Partie.DimensionDesPlateaux; x++){
				g.drawImage(tab[x][y].getImage(), x*largeurCase, y*largeurCase, largeurCase, largeurCase, null);

				g.setColor(Color.RED);
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
	//ajoute si c'est possible, le bateau entre les positions X1,Y1 et X2,Y2
	public void ajouter(Bateau bateau, int X1, int Y1, int X2, int Y2) {
		Case[] positions = new Case[bateau.taille];
		if((X1==X2 && (Math.abs(Y2 - Y1)+1 == bateau.taille))){
			for(int i = 0; i<bateau.taille; i++){
				Case c = tab[X1][Y1+i];
				positions[i] = c;
			}
		}
		if((Y1==Y2 && (Math.abs(X2 - X1)+1 == bateau.taille))){
			for(int i = 0; i<bateau.taille; i++){
				Case c = tab[X1+i][Y1];
				positions[i] = c;
			}
		}

		super.ajouter(bateau, positions);
	}

	@Override
	public void ajouter(int X1, int Y1, int X2, int Y2) {
		this.ajouter(this.listeDesBateaux.bateauSelectionne, X1, Y1, X2, Y2);
	}

        
	private int xDepart, yDepart ;
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("llaaaaaaa" + "" + e.getX() / largeurCase +"" + e.getY() / largeurCase);
		this.xDepart = e.getX() / largeurCase;
		this.yDepart = e.getY() / largeurCase;
	}
	
	private int xArrive, yArrive ;	
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("à llaaaaaaa" + "" + e.getX() / largeurCase +"" + e.getY() / largeurCase);
		this.xArrive = e.getX() / largeurCase;
		this.yArrive = e.getY() / largeurCase;	
		System.out.println();
		this.ajouter(xDepart, yDepart, xArrive, yArrive);
		this.repaint();
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		int col = x/largeurCase;
		int lig = y/largeurCase;
		
		caseSelectionne = tab[col][lig];
		System.out.println(col + lig);
		
		if( !  tab[col][lig].onADejaClique){
			this.tab[col][lig].tirer();
			this.repaint();
		}
		
		
		
	}

	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
