
import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;



public class T105{

public static void main(String[] args){

	Arbre a = null;
	if(args.length == 0){
	    a = new Arbre(null);
	    try{
		FileChannel inChannel = new RandomAccessFile("../doc/Arbre.arb", "r").getChannel();
		MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
		byte[] tab = new byte[buffer.remaining()];
		buffer.get(tab);
		ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(tab));
		a = (Arbre)(o.readObject());
		inChannel.close();
		o.close(); 

		/* FileInputStream fis = new FileInputStream("../doc/Arbre.arb");
      	ObjectInputStream ois = new ObjectInputStream(fis);
      	   a = (Arbre) ois.readObject();
      	    ois.close(); */
	    }
	    catch(Exception e){
		System.out.println("Le fichier n'a pas pu etre charge");
		System.exit(1);
	    }
	}
	else{
	    if(args[0].equals("-c")){
		Dictionnaire d = new Dictionnaire();
		a = Arbre.dico(d.dico, d.pertinence);
		try{
		    ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("../doc/Arbre.arb"));
		    o.writeObject(a);
		    o.close();
		}
		catch(Exception e){ e.printStackTrace(); }
	    }
	    else{
		System.out.println("Mauvaise option");
		System.exit(1);
	    }
	}
	new Modele(a);
    }
}
