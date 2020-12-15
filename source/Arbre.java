


import java.util.*;
import java.io.*;
import java.lang.*;

public class Arbre implements Serializable{

    public boolean bool;
    public int pertinence;
    public Arbre[] tab;
    public Arbre parent;

    public static final ArrayList<Character> caraSpe = new ArrayList<Character>(Arrays.asList(new Character[]{'\u00e0','\u00e2','\u00e7','\u00e9','\u00e8','\u00ea','\u00eb','\u00ee','\u00ef','\u00f4','\u00f9','\u00fb',(char)(39)}));

    private static final long serialVersionUID = 42L;

    public Arbre(Arbre b){
	bool = false;
	pertinence = 0;
	tab = new Arbre[39];
	parent = b;
    }

    public Arbre sousArbre(String s){
	Arbre b = this;
	int c = 0;
	for(int i = 0; i < s.length(); i++){
	    c = (int)(s.charAt(i)) - 97;
	    if(c < 0 || c > 25){
		boolean a = false;
		for(int j = 0; j < 13; j++)
		    if(caraSpe.get(j).compareTo(s.charAt(i)) == 0){
			c = 26 + j;
			a = true;
		    }
		if(!a) return null;
	    }
	    if(b.tab[c] == null) return null;
	    b = b.tab[c];
	}
	return b;
    }

    public ArrayList<Mot> liste(String mot){
	ArrayList<Mot> liste = new ArrayList<Mot>();
	String s = mot;
	if(this.bool) liste.add(new Mot(mot,pertinence));
	for(int i = 0; i < 39; i++){
	    if(this.tab[i] != null){
		if(i < 26) mot += String.valueOf((char)(i+97));
		else mot += String.valueOf(caraSpe.get(i-26));
		liste.addAll(this.tab[i].liste(mot.toLowerCase()));
	    }
	    mot = s;
	}
	return liste;
    }

    public static Arbre dico(ArrayList<String> liste, ArrayList<Integer> liste2){
	Arbre a = new Arbre(null), b;
	int c;
	while(!liste.isEmpty()){
	    System.out.println(liste.size());
	    b = a;
	    for(int i = 0; i < liste.get(0).length(); i++){
		c = liste.get(0).charAt(i)-97;
		if(c < 0 || c > 25){
		    boolean d = false;
		    for(int j = 0; j < 13; j++)
			if(caraSpe.get(j).compareTo(liste.get(0).charAt(i)) == 0){
			    c = 26 + j;
			    d = true;
			}
		    if(!d) break;
		}
		if(b.tab[c] == null) b.tab[c] = new Arbre(b);
		b = b.tab[c];

		if(i == liste.get(0).length()-1){
		    b.bool = true;
		    b.pertinence = liste2.get(0);
		}
	    }
	    liste.remove(0);
	    liste2.remove(0);
	}
	return a;
    }
}
