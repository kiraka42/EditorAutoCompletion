package source.core.controllerView ;

import source.core.controllerView.ControleurVue ;
import source.core.utils.Memoire;
import source.core.modele.Modele;
import source.core.utils.*;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



public class ControleurVue extends JFrame{
    private Container c;
    private JTextArea area;
    public ArrayList<Mot> liste = new ArrayList<Mot>(), listeCorrigee = new ArrayList<Mot>();
    private Modele m;
    private String mot;
    private Point posCurseur;
    private JPopupMenu menu;
    private JList<Mot> jlist;
    private DefaultListModel<Mot> dlm;
    private boolean bool, maj;
    private int borneInf, borneSup, listIndex;
    private JMenuBar bar = new JMenuBar();
    private JMenu jmenu = new JMenu("Fichier") ;
    private JMenuItem item = new JMenuItem("nouveau document");
    private JMenuItem item2 = new JMenuItem("Ouvrir un fichier");
    private JMenuItem item3 = new JMenuItem("Sauvegarde d'un fichier");
    private Memoire memoire = new Memoire() ;


    public ControleurVue(Modele m){
	jmenu.add(item);
	jmenu.add(item2);
	jmenu.add(item3);
	item.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
		 area.setText("");
		}
	 });

	item2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){

		area.setText(memoire.af());
		}
	 });
	item3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
		/* JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		    String name = jop.showInputDialog(null, "Nom du fichier a sauvegarder: ",
						      "Information",
						      JOptionPane.QUESTION_MESSAGE);
		 try{
				new FileWriter(new File(name)).close();
		    }catch (IOException e) {
			e.printStackTrace();
		    }
		    File file= new File(name);

	    		*/
		    memoire.ecrire( area.getText());

		}
	 });


	bar.add(jmenu);
	setJMenuBar(bar);
	c = getContentPane();
	mot = "";
	this.m = m;
	borneInf = 0; borneSup = 0;
	listIndex = 0;
	menu = new JPopupMenu();
	dlm = new DefaultListModel<Mot>();
	jlist = new JList<Mot>(dlm);
	bool = false; maj = false;
	menu.add(jlist);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(600,600);
	area = new JTextArea();
	Font police = new Font("Arial", Font.BOLD, 15);
	area.setFont(police);
	area.setSize(600,600);
	area.setLineWrap(true);
	area.setWrapStyleWord(true);
	posCurseur = new Point(0,0);
	c.add(new JScrollPane(area));
	area.addFocusListener(new FocusAdapter(){
		public void focusLost(FocusEvent e){
		    menu.setVisible(false);
		    bool = false;
		}
	    });
	area.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
		    if(!bool) menu.setVisible(false);
		}
	    });
	jlist.addMouseListener(new MouseAdapter(){
		public void mouseEntered(MouseEvent e){
		    listIndex = jlist.locationToIndex(e.getPoint());
		    bool = true;
		    jlist.setSelectedIndex(listIndex);
		}
		public void mouseExited(MouseEvent e){
		    bool = false;
		    jlist.clearSelection();
		}
		public void mouseReleased(MouseEvent e){
		    area.replaceRange((dlm.get(jlist.getSelectedIndex()).mot + " "), borneInf, borneSup);
		    bool = false;
		    menu.setVisible(false);
		}
	    });
	jlist.addMouseMotionListener(new MouseMotionAdapter(){
		public void mouseMoved(MouseEvent e){
		    int i = jlist.locationToIndex(e.getPoint());
		    if(listIndex != i) jlist.setSelectedIndex(i);
		    listIndex = i;
		}
	    });
	area.addKeyListener(new KeyAdapter(){
		public void keyPressed(KeyEvent e){
		    char c = e.getKeyChar();
		    int i = e.getKeyCode();
		    if((c > 96 && c < 123) || (c > 64 && c < 91) || Arbre.caraSpe.contains(Character.toLowerCase(c))) mot += c;
		    if((i == KeyEvent.VK_ENTER && (!bool || jlist.isSelectionEmpty())) || i == KeyEvent.VK_SPACE){
			mot = "";
			menu.setVisible(false);
			bool = false;
		    }
		    if(i == KeyEvent.VK_BACK_SPACE && mot.length() != 0) mot = mot.substring(0,mot.length()-1);
		    if(bool && i == KeyEvent.VK_ENTER && !jlist.isSelectionEmpty()){
			e.consume();
			area.replaceRange((dlm.get(jlist.getSelectedIndex()).mot + " "), borneInf, borneSup);
		    }
		    if(bool && (i == KeyEvent.VK_UP || i == KeyEvent.VK_DOWN)){
			e.consume();
			int tmp = jlist.getSelectedIndex();
			if(tmp == (dlm.size() - 1) && i == KeyEvent.VK_DOWN) tmp = -1;
			if((tmp == (-1) || tmp == 0) && i == KeyEvent.VK_UP) tmp = dlm.size();
			if(i == KeyEvent.VK_DOWN) jlist.setSelectedIndex(tmp + 1);
			if(i == KeyEvent.VK_UP) jlist.setSelectedIndex(tmp - 1);
			return;
		    }
		}
	    });
	area.getCaret().addChangeListener(new ChangeListener(){
		public void stateChanged(ChangeEvent e){
		    chercheMot();
		}
	    });


	setVisible(true);
    }

    public void proposition(){
	dlm.clear();
	if(mot.length() == 0){
	    menu.setVisible(false);
	    bool = false;
	    return;
	}
	maj = Character.isUpperCase(mot.charAt(0));
	m.autoCompletion(mot.toLowerCase(), true);
	if(mot.length() > 2){
	    ArrayList<String> liste3 = m.correction(mot.toLowerCase());
	    for(int i = 0; i < liste3.size(); i++) m.autoCompletion(liste3.get(i), false);
	}
	java.util.Collections.sort(liste, java.util.Collections.reverseOrder(Mot.getComparator()));
	java.util.Collections.sort(listeCorrigee, java.util.Collections.reverseOrder(Mot.getComparator()));
	int i = 0, j = 0;
	while(j < Math.min((8 - Math.min(4,listeCorrigee.size())) ,liste.size()) && i < liste.size()){
	    if(!dlm.contains(liste.get(i))){
		if(maj) liste.get(i).mot = liste.get(i).mot.substring(0,1).toUpperCase() + liste.get(i).mot.substring(1,liste.get(i).mot.length());
		dlm.addElement(liste.get(i));
		j++;
	    }
	    i++;
	}
	i = 0; j = 0;
	while(j < Math.min((8 - Math.min(4,liste.size())) ,listeCorrigee.size()) && i < listeCorrigee.size()){
	    if(!dlm.contains(listeCorrigee.get(i))){
		if(maj) listeCorrigee.get(i).mot = listeCorrigee.get(i).mot.substring(0,1).toUpperCase() + listeCorrigee.get(i).mot.substring(1,listeCorrigee.get(i).mot.length());
		dlm.addElement(listeCorrigee.get(i));
		j++;
	    }
	    i++;
	}
	if(dlm.size() != 0){
	    try{ posCurseur = area.modelToView(area.getCaretPosition()).getLocation(); }
	    catch(Exception e){}
	    menu.setLocation((int)(posCurseur.getX() + area.getLocationOnScreen().getX()),(int)(posCurseur.getY() + area.getLocationOnScreen().getY() + 20));
	    menu.setVisible(true);
	    bool = true;

	}
	else{
	    menu.setVisible(false);
	    bool = false;
	}
	liste.clear();
	listeCorrigee.clear();
    }

    public void chercheMot(){
	mot = "";
	boolean a = true;
	int i = area.getCaretPosition()-1, j = 0;
	char c;
	while(a){
	    if((i-j) < 0 || (i-j) > area.getText().length() - 1) break;
	    c = area.getText().charAt(i-j);
	    if((c > 96 && c < 123) || (c > 64 && c < 91) || Arbre.caraSpe.contains(Character.toLowerCase(c))){
		mot = c + mot;
		j++;
	    }
	    else a = false;
	}
	borneInf = i-j+1;
	j = 1; a = true;
	while(a){
	    if((i+j) < 0 || (i+j) > area.getText().length() - 1) break;
	    c = area.getText().charAt(i+j);
	    if((c > 96 && c < 123) || (c > 64 && c < 91) || Arbre.caraSpe.contains(Character.toLowerCase(c))){
		mot += c;
		j++;
	    }
	    else a = false;
	}
	borneSup = i+j;
	proposition();
	jlist.setSelectedIndex(0);
    }
}
