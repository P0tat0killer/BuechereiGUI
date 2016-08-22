package de.dominik.api;

public class Buch {

	private int BuchNr;
	private String Sachgebiet;
	private String Autor;
	private String Titel;
	private String Ort;
	private int Jahr;
	private String Verlag;
	
	public Buch(int BuchNr, String Sachgebiet, String Autor, String Titel, String Ort, int Jahr, String Verlag){
		setBuchNr(BuchNr);
		setSachgebiet(Sachgebiet);
		setAutor(Autor);
		setTitel(Titel);
		setOrt(Ort);
		setJahr(Jahr);
		setVerlag(Verlag);
	}

	public int getBuchNr() {
		return BuchNr;
	}

	public void setBuchNr(int buchNr) {
		BuchNr = buchNr;
	}

	public String getAutor() {
		return Autor;
	}

	public void setAutor(String autor) {
		Autor = autor;
	}

	public String getSachgebiet() {
		return Sachgebiet;
	}

	public void setSachgebiet(String sachgebiet) {
		Sachgebiet = sachgebiet;
	}

	public String getTitel() {
		return Titel;
	}

	public void setTitel(String titel) {
		Titel = titel;
	}

	public String getOrt() {
		return Ort;
	}

	public void setOrt(String ort) {
		Ort = ort;
	}

	public int getJahr() {
		return Jahr;
	}

	public void setJahr(int jahr) {
		Jahr = jahr;
	}

	public String getVerlag() {
		return Verlag;
	}

	public void setVerlag(String verlag) {
		Verlag = verlag;
	}
}
