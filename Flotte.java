import java.util.ArrayList;

import javax.swing.JPanel;

 /**
 * Wikipédia : "Une flotte est un grand groupe de navires, d'importance numérique très variable".
 * <p>
 * Dans notre programme : "Une flotte est une liste de <a href="Bateau.html">Bateaux</a> (par héritage) + une variable de type Bateau qui est le bateau actuellement selectionné (eventuellement aucun bateaux n'est seléctionné si cette variable est null)"
 * <p>
 * <img src="FlotteRusse.jpg" alt="Un exemple de Flotte" style="width:304px;height:228px">
 */

public class Flotte extends ArrayList<Bateau> {

	Bateau bateauSelectionne ;
	
	public Flotte() {
	}
	
	public boolean estEnMer() {
		return nombreDeBateauxNonPlaces() == 0;
	}

	public int nombreDeBateauxNonPlaces(){
		int i = 0;
		for(Bateau bateau : this){
			if(! bateau.estEnMer){
				i++;
			}
		}
		
		return i;
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
