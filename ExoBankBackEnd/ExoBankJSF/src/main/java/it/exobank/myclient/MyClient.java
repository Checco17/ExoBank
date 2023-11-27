package it.exobank.myclient;

import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import javax.faces.context.ExternalContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.exobank.utils.Costanti;
import it.exobank.utils.RouteRest;
import it.exobank.methods.JsonConvert;

/*
 * Client è una classe che fa parte della specifica JAX-RS (Java API for RESTful Web Services) e viene utilizzata 
 * per creare e gestire client che possono inviare richieste HTTP a servizi web RESTful. 
 */

public class MyClient {

	public void callRestServiceWithInterfaceClient(Object object, String urlBase, String endPoint) throws Exception {

		try {

			// Creazione di un client per effettuare richieste HTTP al servizio REST.
			Client client = ClientBuilder.newClient();
		
			// Definizione del target del client, ovvero l'URL del servizio REST.
			WebTarget target = client.target(urlBase);

			// Conversione dell'oggetto Utente in una rappresentazione JSON.
			String jsonUtente = JsonConvert.convertObjectToJson(object);

			// Invio di una richiesta HTTP POST al servizio REST, inclusa l'entità JSON
			// dell'utente.
			Response response = target.path(endPoint).request()
					.post(Entity.entity(jsonUtente, MediaType.APPLICATION_JSON));

			if (response.getStatus() == Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR) {
				System.out.println(
						"Errore nella response della classe MyClient nel metodo callRestServiceWithInterfaceClient alla riga 42");
				throw new Exception("Errore nella response. Status: " + response.getStatus());

			} else {
				System.out.println("L'invio dei dati è stato effettuato con successo e lo status della response è "
						+ response.getStatus());

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.ERRORE_CONNESSIONE);
		}
	}

	public void callRestServiceWithoutInterfaceClient(Object object, String urlBase, String endPoint, String metodo,
			String contentType, ExternalContext externalContext) throws Exception {

		try {

			// Definizione del url del servizio REST
			URL url = new URL(urlBase + endPoint);

			// Apertura e settaggio della connessione tramite il metodo privato
			// openConnection(url, metodoHttp, contentType, permessoDatiInUscita)
			HttpURLConnection connection = openConnection(url, metodo, contentType, true);

			// Conversione dell'oggetto in JSON
			String jsonData = JsonConvert.convertObjectToJson(object);

			// Invio dei dati nel corpo della richiesta
			try (OutputStream outputStream = connection.getOutputStream()) {
				byte[] data = jsonData.getBytes(StandardCharsets.UTF_8);
				outputStream.write(data, 0, data.length);
			}

			// Prendo lo status della response
			int responseCode = connection.getResponseCode();
			
			System.out.println(connection.getURL());

			// Gestione della response
			if (responseCode == Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR) {
				System.out.println(
						"Errore nella response della classe MyClient nel metodo callRestServiceWithoutInterfaceClient alla riga 74");
				throw new Exception("Errore nella response. Status: " + responseCode);

			} else {
				System.out.println("L'invio dei dati è stato effettuato con successo e lo status della response è "
						+ responseCode);
				externalContext.redirect(RouteRest.URL_APP_REACT + "?utente=" + URLEncoder.encode(jsonData, "UTF-8"));
			}

			// Chiudo la connessione
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.ERRORE_CONNESSIONE);
		}
	}

	public void callRestServiceAndRedirect() {
	    try {
	        HttpClient client = HttpClient.newBuilder()
	                .version(HttpClient.Version.HTTP_1_1)
	                .followRedirects(HttpClient.Redirect.ALWAYS)
	                .connectTimeout(Duration.ofSeconds(20))
	                .build();

	        URI uri = new URI(RouteRest.URL_SERVIZIO_REST_UTENTE + "/redirectResponse");

	        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();

	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        // Verifica se ci sono reindirizzamenti multipli
	        do {
	        	String redirectUrl = response.headers().firstValue("Location").orElse("");
            	System.out.println(redirectUrl);
            	URI urii = new URI("http://localhost:3000");
            	request = HttpRequest.newBuilder(urii).GET().build();
            	response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        	
	        }   while (response.statusCode() >= 300 && response.statusCode() < 400);         
	        

	        System.out.println(response.statusCode());
	        System.out.println(response.body());

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	private HttpURLConnection openConnection(URL url, String metodo, String contentType, boolean doOutPut)
			throws Exception {

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(metodo);
		connection.setRequestProperty("Content-Type", contentType);
		connection.setDoOutput(doOutPut);
		return connection;

	}

}
