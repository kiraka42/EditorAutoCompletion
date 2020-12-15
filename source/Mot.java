



import java.util.Comparator;

public class Mot implements Comparable<Mot>{

    public String mot;
    public int pertinence;

    public Mot(String mot, int pertinence){
	this.mot = mot;
	this.pertinence = pertinence;
    }

    public boolean equals(Mot m){ return this.mot.toLowerCase().equals(m.mot.toLowerCase()); }

    public boolean equals(Object o){ return this.mot.toLowerCase().equals(((Mot)(o)).mot.toLowerCase()); }

    public int compareTo(Mot m){ return this.pertinence - m.pertinence; }

    public String toString(){
	return mot;
    }

    public static Comparator<Mot> getComparator(){
	return new Comparator<Mot>(){

	    public int compare(Mot a, Mot b){ return a.compareTo(b); }

	    public boolean equals(Object o){ return true; }

	};
    }
}
