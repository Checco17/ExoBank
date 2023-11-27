package it.exobank.methods;

import com.google.gson.Gson;


public class JsonConvert {
	
	public static String convertObjectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
	
	
	public static <T> T convertJsonToObject(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
	
}

