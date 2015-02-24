import java.util.ArrayList;

import javax.swing.JPanel;


public class Flotte extends ArrayList<Bateau> {

	Bateau bateauSelectionne ;

	public int nombreDeBateauxNonPlaces(){
		int i = 0;
		for(Bateau bateau : this){
			if(! bateau.estEnMer){
				i++;
			}
		}
		
		return i;
	}
	
	public Flotte() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean estEnMer() {
		return nombreDeBateauxNonPlaces() == 0;
	}
	
	public JPort getJPort(){
		return null;
	}

	void mettreAJourSelection(){
		
	    	for (int i = 0; i<this.size(); i++){
			if(! this.get(i).estEnMer())	{
			    bateauSelectionne = this.get(i);
			}
		}
	}



}
