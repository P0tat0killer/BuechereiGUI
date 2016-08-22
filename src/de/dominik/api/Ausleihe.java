package de.dominik.api;

public class Ausleihe {

	private Leser leser;
	private Buch buch;
	private String ausgeliehen;
	private String gemahnt;
	private boolean zurueck;
	
	public Ausleihe(Leser leser, Buch buch, String ausgeliehen, String gemahnt, int zurueck){
		setLeser(leser);
		setBuch(buch);
		setAusgeliehen(ausgeliehen);
		setGemahnt(gemahnt);
		switch (zurueck + "") {
		case"0": setZurueck(false);break;
		case"1": setZurueck(true);break;
		default: setZurueck(false);break;
		}
	}

	public Leser getLeser() {
		return leser;
	}

	public void setLeser(Leser leser) {
		this.leser = leser;
	}

	public Buch getBuch() {
		return buch;
	}

	public void setBuch(Buch buch) {
		this.buch = buch;
	}

	public String getAusgeliehen() {
		return ausgeliehen;
	}

	public void setAusgeliehen(String ausgeliehen) {
		this.ausgeliehen = ausgeliehen;
	}

	public String getGemahnt() {
		return gemahnt;
	}

	public void setGemahnt(String gemahnt) {
		this.gemahnt = gemahnt;
	}

	public boolean isZurueck() {
		return zurueck;
	}

	public void setZurueck(boolean zurueck) {
		this.zurueck = zurueck;
	}
	
}
