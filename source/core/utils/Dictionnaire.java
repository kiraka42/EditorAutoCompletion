package source.core.utils ;

import source.core.utils.Dictionnaire;

import java.io.*;
import java.util.ArrayList;

public class Dictionnaire {

    public final String path = "../doc/liste_mots.txt" ;
    public ArrayList<String> dico = new ArrayList<String>();
    public ArrayList<Integer> pertinence  = new ArrayList<Integer>();

    public Dictionnaire(){
	try{
	    BufferedReader buff = new BufferedReader(new FileReader(path));
	    String line;
	    while ((line = buff.readLine()) != null)
		dico.add(line);
	}
	catch(IOException e){ e.printStackTrace(); }
	for(int i = 0; i < dico.size(); i++){
	    int j = 0;
	    for(int k = 0; k < dico.get(i).length(); k++){
		if(dico.get(i).charAt(k) == 9){
		    j = k;
		    break;
		}
	    }
	    pertinence.add(new Integer((int)(Double.parseDouble(dico.get(i).substring(j+1))*100)));
	    dico.set(i,dico.get(i).substring(0,j));
	}
    }
}
