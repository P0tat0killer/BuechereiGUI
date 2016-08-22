package de.dominik.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MySQL {

	private static String database;
	private static String hostname;
	private static int port;
	private static String userid;
	private static String password;
    private static String connectionString = "";
    
	public MySQL(String db, String hn, int po, String user, String pw){
    	database=db;
    	hostname=hn;
    	port=po;
    	userid=user;
    	password=pw;
    	
		try {
			startCore();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	   public boolean startCore() throws ClassNotFoundException{
	    	connectionString = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}	    	
	    	System.out.println(PrintTime.getTime() + "Oeffne MySQL Datenbank " + hostname + ":" + port + "/" + database);
	    	return true;
	    }
	   
	   public ArrayList<Buch> getAllBuecher(){
		   ArrayList<Buch> re = new ArrayList<>();
		   Connection c = null;
		   try {
			c = getConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `Buecher` WHERE 1");
			while(rs.next()){
				int bn = rs.getInt("BuchNr");
				String Sachgebiet = rs.getString("Sachgebiet");
				String Autor = rs.getString("Autor");
				String Titel = rs.getString("Titel");
				String Ort = rs.getString("Ort");
				int Jahr = rs.getInt("Jahr");
				String Verlag = rs.getString("Verlag");
				Buch b = new Buch(bn, Sachgebiet, Autor, Titel, Ort, Jahr, Verlag);
				re.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			releaseConnection(c);
		}
		   return re;
	   }
	   
	   public ArrayList<Leser> getAllLeser(){
		   ArrayList<Leser> re = new ArrayList<>();
		   Connection c = null;
		   try {
			c = getConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `Leser` WHERE 1 ORDER BY  `Leser`.`LeserNr` ASC ");
			while(rs.next()){
				int ln = rs.getInt("LeserNr");
				String nn = rs.getString("Nachname");
				String vn = rs.getString("Vorname");
				String gs = rs.getString("Geschlecht");
				String geb = rs.getString("geboren");
				String str = rs.getString("Strasse");
				int post = rs.getInt("Postleitzahl");
				String ort = rs.getString("Ort");
				Leser l = new Leser(ln, nn, vn, gs, geb, str, post, ort);
				re.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			releaseConnection(c);
		}
		   return re;
	   }
	   
	   private static Connection getConnection() throws SQLException {
			Connection c = null;
			c = DriverManager.getConnection(connectionString, userid, password);
			return c;
		}
		private static void doUpdate(Connection c, String sql) throws SQLException {
		    Statement stmt = c.createStatement();
		    stmt.executeUpdate(sql);
		    stmt.close();
		}
		private static void releaseConnection(Connection c){
			try {
				c.close();
			} catch (SQLException x) {
				System.out.println(x.getMessage());
			}
		}
		public void update(String s){
			Connection c = null;
			try {
				c = getConnection();
				doUpdate(c, s);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
			
		}
		
		public ArrayList<Ausleihe> getAusleihenFuerLeserByNr(int LeserNr){
			ArrayList<Ausleihe> re = new ArrayList<>();
			Connection c = null;
			try {
				c = getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `Ausgeliehen` WHERE `LeserNr`='"+LeserNr+"'");
				while(rs.next()){
					Leser leser = this.getLeserByNr(LeserNr);
					Buch buch = this.getBuchByNr(rs.getInt("BuchNr"));
					String gemahnt = rs.getString("gemahnt");
					String ausgeliehen = rs.getString("ausgeliehen");
					int zurueck = rs.getInt("zurueck");
					Ausleihe a = new Ausleihe(leser, buch, ausgeliehen, gemahnt, zurueck);
					re.add(a);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
			return re;
		}
		
		public ArrayList<Ausleihe> getAusleihenFuerBuchByBuchNr(int BuchNr){
			ArrayList<Ausleihe> re = new ArrayList<>();
			Connection c = null;
			try {
				c = getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `Ausgeliehen` WHERE `BuchNr`='"+BuchNr+"'");
				while(rs.next()){
					Leser l = this.getLeserByNr(rs.getInt("LeserNr"));
					Buch b = this.getBuchByNr(BuchNr);
					String ausgeliehen = rs.getString("ausgeliehen");
					String gemahnt = rs.getString("gemahnt");
					int zurueck = rs.getInt("zurueck");
					Ausleihe a = new Ausleihe(l, b, ausgeliehen, gemahnt, zurueck);
					re.add(a);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
			return re;
		}

		public Buch getBuchByNr(int buchnr) {
			Buch buch = null;
			Connection c = null;
			try {
				c = getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `Buecher` WHERE `BuchNr`='"+buchnr+"'");
				if(rs.next()){
					String Sachgebiet = rs.getString("Sachgebiet");
					String Autor = rs.getString("Autor");
					String Titel = rs.getString("Titel");
					String Ort = rs.getString("Ort");
					int Jahr = rs.getInt("Jahr");
					String Verlag = rs.getString("Verlag");
					buch = new Buch(buchnr, Sachgebiet, Autor, Titel, Ort, Jahr, Verlag);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
			
			return buch;
		}

		public Leser getLeserByNr(int leserNr) {
			Leser re = null;
			Connection c = null;
			try {
				c = getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM `Leser` WHERE `LeserNr`='" + leserNr + "'");
				if(rs.next()){
					int ln = rs.getInt("LeserNr");
					String nn = rs.getString("Nachname");
					String vn = rs.getString("Vorname");
					String gs = rs.getString("Geschlecht");
					String geb = rs.getString("geboren");
					String str = rs.getString("Strasse");
					int post = rs.getInt("Postleitzahl");
					String ort = rs.getString("Ort");
					re = new Leser(ln, nn, vn, gs, geb, str, post, ort);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
			return re;
		}

		public void addLeser(Leser l) {
			Connection c = null;
			try {
				c = getConnection();
				doUpdate(c, "INSERT INTO `Leser`(`LeserNr`, `Nachname`, `Vorname`, `Geschlecht`, `geboren`, `Strasse`, `Postleitzahl`, `Ort`) VALUES ('"+l.getLeserNr()+"', '"+l.getNachname()+"', '"+l.getVorname()+"', '"+l.getGeschlecht()+"', '"+l.getGeboren()+"', '"+l.getStrasse()+"', '"+l.getPostleitzahl()+"', '"+l.getOrt()+"')");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
		}

		public void deleteLeser(Leser leser) {
			Connection c = null;
			try {
				c = getConnection();
				doUpdate(c, "DELETE FROM `Leser` WHERE `LeserNr`='"+leser.getLeserNr()+"'");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
		}

		public void addAusleihe(int leserid, int buchid) {
			Connection c = null;
			try {
				c = getConnection();
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date date = new Date();
				doUpdate(c, "INSERT INTO `Ausgeliehen`(`LeserNr`, `BuchNr`, `ausgeliehen`, `gemahnt`, `zurueck`) VALUES ('"+leserid+"', '"+buchid+"', '"+dateFormat.format(date)+"', '', '0')");
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				releaseConnection(c);
			}
			
		}
	
}
