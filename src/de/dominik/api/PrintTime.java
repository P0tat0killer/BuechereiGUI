package de.dominik.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PrintTime {
	
	public static String getTime(){
		return "[" + new SimpleDateFormat("dd.MM HH:mm:ss").format(Calendar.getInstance().getTime()) + "]";
	}
}
