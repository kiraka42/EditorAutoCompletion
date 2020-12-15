


import java.util.*;

public class Modele{

    private Arbre a, b;
    private ControleurVue cv;

    public Modele(Arbre a){
	this.a = a;
	b = null;
	cv = new ControleurVue(this);
    }

    public Arbre getA(){ return a; }

    public ArrayList<String> correction(String prefixe){
	ArrayList<String> liste2 = new ArrayList<String>();
	char c;
	for(int i = 0; i < prefixe.length(); i++){
	    for(int j = 0; j < 39; j++){
		if(j < 26) c = (char)(j+97);
		else c = Arbre.caraSpe.get(j-26);
		liste2.add(prefixe.substring(0,i) + String.valueOf(c) + prefixe.substring(i+1,prefixe.length()));
		liste2.add(prefixe.substring(0,i) + String.valueOf(c) + prefixe.substring(i,prefixe.length()));
	    }
	    liste2.add(prefixe.substring(0,i) + prefixe.substring(i+1));
	    if(i != (prefixe.length()-1)) liste2.add(prefixe.substring(0,i) + prefixe.charAt(i+1) + prefixe.charAt(i) + prefixe.substring(i+2,prefixe.length()));
	}
	return liste2;
    }

    public void autoCompletion(String s, boolean bool){
	if(s.length() == 0) return;
	b = a.sousArbre(s.toLowerCase());
	if(b == null) return;
	if(bool) cv.liste.addAll(b.liste(s.toLowerCase()));
	else cv.listeCorrigee.addAll(b.liste(s.toLowerCase()));
    }
}
