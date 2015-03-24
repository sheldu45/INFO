import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Dico extends ArrayList<String>{
	
	public void chargement() {
	    String str = "Bonjour";
		File file = new File("Mots");
		try {
			FileReader fr = new FileReader(file);
			int i =0;
			str = "";
			while((i=fr.read())!= -1){
				if((char)i != '\n' ){
					str += (char)i;
				}
				else{
					this.add(str);
					str = "";
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
		      e.printStackTrace();
		    }
		
	}
	

	public boolean existe(String mot){
		return this.contains(mot);
	}
	
	public static void main(String[] args){
		Dico c = new Dico();
		c.chargement();
		System.out.println("pète : " +c.existe("pète"));
		System.out.println("papa : " +c.existe("papa"));
		System.out.println("difficile : " +c.existe("difficile"));
		System.out.println("rfer : " +c.existe("rgrdsf"));
		
		
	}
}
