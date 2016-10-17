package source.core.utils ;

import source.core.utils.Memoire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class Memoire {


	/*
	 * Creation d'un fichier  : gestion de la sauvegarde
	 * nomF = nom du fichier Vide
	 */
	public Memoire (String nomF) {
		File fic = new File(nomF);
	}

	public Memoire(){}

	/*
	*Fonction qui recupere la chaine de caractere contenu dans le fichier selectionne
	*  return String
	*/
	public String af (){
		String chaine="";
		String fichier = "";
			JFileChooser JFC = new JFileChooser();
		int returnVal = JFC.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				String chemin=JFC.getSelectedFile().toString();
				File fw = new File(chemin);
				fichier=chemin;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		//lecture du fichier texte
		try{
			InputStream ips=new FileInputStream(fichier);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){

				chaine+=ligne+"\n";
			}
			br.close();
		}
		catch (Exception e){

			 JOptionPane	jop3 ;
				jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "Le fichier n'existe pas !", "Erreur",
				JOptionPane.ERROR_MESSAGE);
			return "" ;
		}
		System.out.println(chaine);




		return chaine ;

	}


	/*
	*Fonction qui prends en paramettre le contenu de l'editeur et qui l'enregistre ensuite dans un fichier texte a l'emplacementindique
	*arg : String
	*return void
	*/


	   public void ecrire(String texte)
		{
				JFileChooser JFC = new JFileChooser();
		int returnVal = JFC.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				String chemin=JFC.getSelectedFile().toString();
				File fw = new File(chemin);

				FileWriter w=new FileWriter(fw);
				w.write(texte);
				w.flush();
		    		w.close();


			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		}



   }
