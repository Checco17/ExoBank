package it.exobank.methods;

import java.util.HashMap;

public class CryptoString {
    private HashMap<String, String> map;

    public CryptoString() {
        map = getMap();
    }

    public String cryptoString(String stringa) {
        char[] charSequence = stringa.toCharArray();
        StringBuilder resultString = new StringBuilder();

        for (char c : charSequence) {
            String character = String.valueOf(c).toUpperCase();
            if (map.containsKey(character)) {
                resultString.append(map.get(character));
            } else {
                resultString.append(character);
            }
        }

        return resultString.toString();
    }

    public static void main(String[] args) {
        CryptoString crypto = new CryptoString();

        String parola = "Checco7!";
        String cryptoString = crypto.cryptoString(parola);
        System.out.println(cryptoString);
    }

    private HashMap<String, String> getMap() {
    	HashMap<String, String> map = new HashMap<>();
        // Inizializza la mappa come descritto nell'esempio precedente
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");
        map.put("E", "5");
        map.put("F", "6");
        map.put("G", "7");
        map.put("H", "8");
        map.put("I", "9");
        map.put("L", "10");
        map.put("M", "11");
        map.put("N", "12");
        map.put("O", "13");
        map.put("P", "14");
        map.put("Q", "15");
        map.put("R", "16");
        map.put("S", "17");
        map.put("T", "18");
        map.put("U", "19");
        map.put("V", "20");
        map.put("Z", "21");
        map.put("!", "22");
        map.put("?", "23");

        return map;
    }
}

