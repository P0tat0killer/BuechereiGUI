package de.dominik.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import de.dominik.api.Ausleihe;
import de.dominik.api.Buch;
import de.dominik.api.Leser;
import de.dominik.api.MySQL;
import de.dominik.api.TextPrompt;

public class Main {

	public static MySQL mysql = null;
	public static JFrame MainFrame = null;
	private static Image MainIcon = null;
	private static boolean resizeable = false;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws IOException {
		InputStream is = Main.class.getResourceAsStream("/MYSQL.dll");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader bffr = new BufferedReader(isr);
		URL url = new URL("http://85.25.209.103/team/buecher.png");
		MainIcon = ImageIO.read(url);
		mysql = new MySQL("schule", "85.25.209.103", 3306, "schule", bffr.readLine());
		MainFrame = new JFrame("Bibliotheksausleihe Stadt Hohenstein");
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainFrame.setIconImage(MainIcon);
		MainFrame.setSize(500, 600);
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("-resizeable")){
				resizeable=true;
			}
		}
		MainFrame.setResizable(resizeable);
		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		JPanel JPLeser = new JPanel();
		JPLeser.setLayout(new BoxLayout(JPLeser, BoxLayout.Y_AXIS));
		final ArrayList<Leser> AllLeser = mysql.getAllLeser();
		for(int i = 0; i < AllLeser.size(); i++){
			JLabel LeserLabel = new JLabel(AllLeser.get(i).getLeserNr() + ", " + AllLeser.get(i).getVorname() + " " + AllLeser.get(i).getNachname());
			final int i2 = i;
			LeserLabel.setToolTipText("Klicke hier für weitere Informationen über " + AllLeser.get(i).getVorname() + " " + AllLeser.get(i).getNachname());
			LeserLabel.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
				openLeserInfo(AllLeser.get(i2).getLeserNr());
				}
			});
			JPLeser.add(LeserLabel);
		}
		JPanel JPBuecher = new JPanel();
		JPBuecher.setLayout(new BoxLayout(JPBuecher, BoxLayout.Y_AXIS));
		ArrayList<Buch> buecher = mysql.getAllBuecher();
		for(int i = 0; i < buecher.size(); i++){
			final Buch b = buecher.get(i);
			JLabel bu = new JLabel(b.getBuchNr() + ", " + b.getTitel());
			bu.setToolTipText("Klicke hier für mehr Informationen über " + b.getTitel() + " von " + b.getAutor());
			bu.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
				openBuchInfo(b.getBuchNr());
				}
			});
			JPBuecher.add(bu);
		}
		JPanel JPNLeser = new JPanel();
		JPNLeser.setLayout(new GridLayout(8, 1,1,20));
		final JTextField vorname = new JTextField(10);
		Font f = new Font(vorname.getFont().getName(), Font.PLAIN, vorname.getFont().getSize()+7);
		vorname.setFont(f);
		new TextPrompt("Vorname", vorname);
		JPNLeser.add(vorname);
		final JTextField nachname = new JTextField(10);
		nachname.setFont(f);
		new TextPrompt("Nachname", nachname);
		JPNLeser.add(nachname);
		String MW[] = {"Männlich", "Weiblich"};
		final JComboBox MWBox = new JComboBox(MW);
		MWBox.setFont(f);
		JPNLeser.add(MWBox);
		final JTextField geboren = new JTextField(10);
		geboren.setFont(f);
		new TextPrompt("geboren am (TT.MM.YYYY)", geboren);
		JPNLeser.add(geboren);
		final JTextField strasse = new JTextField(10);
		strasse.setFont(f);
		new TextPrompt("Strasse und Hausnummer", strasse);
		JPNLeser.add(strasse);
		final JTextField PoZahl = new JTextField(10);
		PoZahl.setFont(f);
		new TextPrompt("Postleitzahl", PoZahl);
		JPNLeser.add(PoZahl);
		final JTextField Orttf = new JTextField(10);
		Orttf.setFont(f);
		new TextPrompt("Ort", Orttf);
		JPNLeser.add(Orttf);
		final JButton saveLeser = new JButton("Speichern");
		saveLeser.setFont(f);
		saveLeser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String Vorname = vorname.getText();
				vorname.setText("");
				String Nachname = nachname.getText();
				nachname.setText("");
				String geschlecht = (String) MWBox.getSelectedItem();
				switch (geschlecht) {
				case"Männlich":
					geschlecht="m";
					break;

				case"Weiblich":
					geschlecht="w";
					break;
				}
				String geboren1 = geboren.getText();
				geboren.setText("");
				String Strasse1 = strasse.getText();
				strasse.setText("");
				String Postleitzahl = PoZahl.getText();
				PoZahl.setText("");
				String Ort = Orttf.getText();
				Orttf.setText("");
				int Leserid = mysql.getAllLeser().get(mysql.getAllLeser().size()-1).getLeserNr()+1;
				Leser l = new Leser(Leserid, Nachname, Vorname, geschlecht, geboren1, Strasse1, new Integer(Postleitzahl), Ort);
				mysql.addLeser(l);
				openLeserInfo(Leserid);
			}
		});
		JPNLeser.add(saveLeser);
		JPanel JPNAusleihe = new JPanel();
		JPNAusleihe.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		gBC.fill = GridBagConstraints.HORIZONTAL;
		
		final JTextField LeserIDTF = new JTextField();
		Font fAusleihe = new Font(LeserIDTF.getFont().getName(), Font.PLAIN, LeserIDTF.getFont().getSize()+7);
		LeserIDTF.setFont(fAusleihe);
		new TextPrompt("LeserNr", LeserIDTF);
		gBC.weightx = 4;
		gBC.gridx = 0;
		gBC.gridy = 0;
		gBC.ipady = 40;
		JPNAusleihe.add(LeserIDTF, gBC);
		JButton LeserIDSuche = new JButton("Suchen?");
		gBC.weightx = 0.5;
		gBC.gridx = 1;
		gBC.gridy = 0;
		gBC.ipady = 40;
		LeserIDSuche.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame LeserIDSucheFrame = new JFrame("LeserNr Suche");
				LeserIDSucheFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				LeserIDSucheFrame.setLocation(MainFrame.getLocation());
				LeserIDSucheFrame.setIconImage(MainIcon);
				LeserIDSucheFrame.setResizable(resizeable);
				JScrollPane JPSearch = new JScrollPane(LeserIDSucheFrame);
				JPSearch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				JPSearch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				JPSearch.setLayout(new BoxLayout(JPSearch, BoxLayout.Y_AXIS));
				ArrayList<Leser> Leserarray = mysql.getAllLeser();
				ArrayList<JLabel> Labelarray = new ArrayList<>();
				JTextField Suche = new JTextField();
				new TextPrompt("Suche", Suche);
				Suche.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent e) {
						suche();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						suche();
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						suche();
					}
					
					private void suche() {
						
					}
				});
				JPSearch.add(Suche);
				
				for(int i = 0 ; i < Leserarray.size(); i++){
					final Leser l = Leserarray.get(i);
					JLabel label = new JLabel(l.getLeserNr() + ", " + l.getVorname() + " " + l.getNachname());
					label.setToolTipText("Klicke hier um " + l.getVorname() + " " + l.getNachname() + " auszuwählen");
					label.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
						}
						
						@Override
						public void mousePressed(MouseEvent e) {
						}
						
						@Override
						public void mouseExited(MouseEvent e) {
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
						}
						
						@Override
						public void mouseClicked(MouseEvent e) {
							LeserIDTF.setText(l.getLeserNr() + "");
							LeserIDSucheFrame.dispose();
						}
					});
					Labelarray.add(label);
					JPSearch.add(label);
				}
				
				LeserIDSucheFrame.add(JPSearch);
				LeserIDSucheFrame.setSize(174, 500);
				LeserIDSucheFrame.setVisible(true);
			}
		});
		JPNAusleihe.add(LeserIDSuche,gBC);
		
		final JTextField BuchIDTF = new JTextField();
		BuchIDTF.setFont(fAusleihe);
		new TextPrompt("BuchNr", BuchIDTF);
		gBC.weightx = 4;
		gBC.gridx = 0;
		gBC.gridy = 1;
		gBC.ipady = 40;
		JPNAusleihe.add(BuchIDTF,gBC);
		JButton BuchIDSuche = new JButton("Suchen?");
		gBC.weightx = 0.5;
		gBC.gridx = 1;
		gBC.gridy = 1;
		gBC.ipady = 40;
		JPNAusleihe.add(BuchIDSuche, gBC);
		
		JButton saveAusleihe = new JButton("Speichern");
		gBC.gridx = 0;
		gBC.gridy = 2;
		gBC.ipady = 40;
		saveAusleihe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					int leserid = new Integer(LeserIDTF.getText());
					int buchid = new Integer(BuchIDTF.getText());
					mysql.addAusleihe(leserid, buchid);
					LeserIDTF.setText("");
					BuchIDTF.setText("");
				}catch(NumberFormatException e){
					return;
				}
			}		
		});
		JPNAusleihe.add(saveAusleihe,gBC);
		
		JScrollPane JPLeserScroll = new JScrollPane(JPLeser);
		JPLeserScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPLeserScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JScrollPane JPBuecherScroll = new JScrollPane(JPBuecher);
		JPBuecherScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPBuecherScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tabpane.addTab("Leser", JPLeserScroll);
		tabpane.addTab("Bücher", JPBuecherScroll);
		tabpane.addTab("Neue Ausleihe", JPNAusleihe);
		tabpane.addTab("Neuer Leser", JPNLeser);
		MainFrame.add(tabpane);
		MainFrame.setVisible(true);
		
	}
	
	protected static void openLeserInfo(int LeserNr) {
		final Leser leser = mysql.getLeserByNr(LeserNr);
		final JFrame LeserFrame = new JFrame(leser.getVorname() + " " + leser.getNachname());
		LeserFrame.setLocation(MainFrame.getLocation());
		LeserFrame.setResizable(resizeable);
		JPanel LPanel = new JPanel();
		LPanel.setLayout(new BoxLayout(LPanel, BoxLayout.Y_AXIS));
		LeserFrame.setIconImage(MainIcon);
		LeserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel infos = new JLabel("Informationen", JLabel.CENTER);
		Font font = infos.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize() + 7);
		infos.setFont(boldFont);
		LPanel.add(infos);
		JLabel id = new JLabel("ID: " + leser.getLeserNr());
		LPanel.add(id);
		JLabel vorname = new JLabel("Vorname: " + leser.getVorname());
		LPanel.add(vorname);
		JLabel nachname = new JLabel("Nachname: " + leser.getNachname());
		LPanel.add(nachname);
		switch (leser.getGeschlecht()) {
		case"m": JLabel m = new JLabel("Geschlecht: Männlich");LPanel.add(m);break;
		case"w": JLabel w = new JLabel("Geschlecht: Weiblich");LPanel.add(w);break;
		default: JLabel n = new JLabel("Geschlecht: -/-");LPanel.add(n);break;
		}
		JLabel anschrift = new JLabel("Anschrift: " + leser.getStrasse() + ", " + leser.getPostleitzahl() + " " + leser.getOrt()); 
		LPanel.add(anschrift);
		JLabel ausleihen = new JLabel("Ausleihhistorie");
		ausleihen.setFont(boldFont);
		LPanel.add(ausleihen);
		ArrayList<Ausleihe> ausarray = mysql.getAusleihenFuerLeserByNr(LeserNr);
		for(int i=0; i < ausarray.size(); i++){
			final Ausleihe a = ausarray.get(i);
			String text;
			if(a.getGemahnt().equalsIgnoreCase("")){
				text = a.getBuch().getTitel() + " am " + a.getAusgeliehen();
			}else{
				text = a.getBuch().getTitel() + " am " + a.getAusgeliehen() + ". Gemahnt am " + a.getGemahnt();
			}
			JLabel forlabel = new JLabel(text);
			if(a.isZurueck()){
				forlabel.setForeground(Color.BLUE);
			}else if(!a.getGemahnt().equalsIgnoreCase("")){
				forlabel.setForeground(Color.RED);
			}else{
				forlabel.setForeground(Color.ORANGE);
			}
			forlabel.setToolTipText("Klicke hier für mehr Informationen über " + a.getBuch().getTitel() + " von " + a.getBuch().getAutor());
			forlabel.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Thread BuchInfo = new Thread(new Runnable() {
						
						@Override
						public void run() {
							openBuchInfo(a.getBuch().getBuchNr());							
						}
					});
					BuchInfo.start();
				}
			});
			LPanel.add(forlabel);
		}
		JMenuBar bar = new JMenuBar();
        JMenu del = new JMenu("Löschen");
        del.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Soll dieser Leser wirklich gelöscht werden?", "Löschen?", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	mysql.deleteLeser(leser);
		        	LeserFrame.dispose();
		        }
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
			}
		});
        bar.add(del);
        LeserFrame.setJMenuBar(bar);
		LeserFrame.add(LPanel);
		LeserFrame.pack();
		LeserFrame.setVisible(true);
	}

	protected static void openBuchInfo(int buchNr) {
		Buch buch = mysql.getBuchByNr(buchNr);
		JFrame BuchFrame = new JFrame(buch.getTitel() + " von " + buch.getAutor());
		BuchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		BuchFrame.setIconImage(MainIcon);
		BuchFrame.setLocation(MainFrame.getLocation());
		JPanel BPanel = new JPanel();
		BPanel.setLayout(new BoxLayout(BPanel, BoxLayout.Y_AXIS));
		JLabel infos = new JLabel("Informationen");
		Font font = infos.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize() + 7);
		infos.setFont(boldFont);
		BPanel.add(infos);
		JLabel ID = new JLabel("ID: " + buchNr);
		BPanel.add(ID);
		JLabel Titel = new JLabel("Titel: " + buch.getTitel());
		BPanel.add(Titel);
		JLabel Autor = new JLabel("Autor: " + buch.getAutor());
		BPanel.add(Autor);
		JLabel Sachgebiet = new JLabel("Sachgebiet: " + buch.getSachgebiet());
		BPanel.add(Sachgebiet);
		JLabel Ort = new JLabel("Ort: " + buch.getOrt());
		BPanel.add(Ort);
		String Jahrtext;
		switch (buch.getJahr()) {
		case 0:
			Jahrtext = "Jahr: -/-";
			break;

		default:
			Jahrtext = "Jahr: " + buch.getJahr();
			JLabel Jahr = new JLabel(Jahrtext);
			BPanel.add(Jahr);
			break;
		}
		JLabel Verlag = new JLabel("Verlag: " + buch.getVerlag());
		BPanel.add(Verlag);
		JLabel aus = new JLabel("Ausleihen");
		aus.setFont(boldFont);
		BPanel.add(aus);
		ArrayList<Ausleihe> ausleihen = mysql.getAusleihenFuerBuchByBuchNr(buchNr);
		for(int i = 0; i < ausleihen.size(); i++){
			final Ausleihe a = ausleihen.get(i);
			String text;
			if(a.getGemahnt().equalsIgnoreCase("")){
				text = a.getLeser().getVorname() + " " + a.getLeser().getNachname() + " am " + a.getAusgeliehen();
			}else{
				text = a.getLeser().getVorname() + " " + a.getLeser().getNachname() + " am " + a.getAusgeliehen() + ". Gemahnt am " + a.getGemahnt();
			}
			JLabel forlabel = new JLabel(text);
			if(a.isZurueck()){
				forlabel.setForeground(Color.BLUE);
			}else if(!a.getGemahnt().equalsIgnoreCase("")){
				forlabel.setForeground(Color.RED);
			}else{
				forlabel.setForeground(Color.ORANGE);
			}
			forlabel.setToolTipText("Klicke hier für mehr Informationen über " + a.getLeser().getVorname() + " " + a.getLeser().getNachname());
			forlabel.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Thread LeserThread = new Thread(new Runnable() {
						
						@Override
						public void run() {
							openLeserInfo(a.getLeser().getLeserNr());							
						}
					});
					LeserThread.start();
				}
			});
			BPanel.add(forlabel);
		}
		BuchFrame.add(BPanel);
		BuchFrame.pack();
		BuchFrame.setResizable(resizeable);
		BuchFrame.setVisible(true);
	}

}
