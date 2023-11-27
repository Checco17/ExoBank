package it.exobank.methods;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	public static String formattaData(Date data) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 

		String dataFormattata = sdf.format(data);

		return dataFormattata;

	}

}
