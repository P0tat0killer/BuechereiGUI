package de.dominik.api;

public class Leser {

	private int LeserNr;
	private String Nachname;
	private String Vorname;
	private String Geschlecht;
	private String geboren;
	private String Strasse;
	private int Postleitzahl;
	private String Ort;
	
	public Leser(int LeserNr, String Nachname, String Vorname, String Geschlecht, String geboren, String Strasse, int Postleitzahl, String Ort){
		setLeserNr(LeserNr);
		setNachname(Nachname);
		setVorname(Vorname);
		setGeschlecht(Geschlecht);
		setGeboren(geboren);
		setStrasse(Strasse);
		setPostleitzahl(Postleitzahl);
		setOrt(Ort);
	}

	public int getLeserNr() {
		return LeserNr;
	}

	public void setLeserNr(int leserNr) {
		LeserNr = leserNr;
	}

	public String getNachname() {
		return Nachname;
	}

	public void setNachname(String nachname) {
		Nachname = nachname;
	}

	public String getVorname() {
		return Vorname;
	}

	public void setVorname(String vorname) {
		Vorname = vorname;
	}

	public String getGeschlecht() {
		return Geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		Geschlecht = geschlecht;
	}

	public String getStrasse() {
		return Strasse;
	}

	public void setStrasse(String strasse) {
		Strasse = strasse;
	}

	public int getPostleitzahl() {
		return Postleitzahl;
	}

	public void setPostleitzahl(int postleitzahl) {
		Postleitzahl = postleitzahl;
	}

	public String getOrt() {
		return Ort;
	}

	public void setOrt(String ort) {
		Ort = ort;
	}

	public String getGeboren() {
		return geboren;
	}

	public void setGeboren(String geboren) {
		this.geboren = geboren;
	}
}
