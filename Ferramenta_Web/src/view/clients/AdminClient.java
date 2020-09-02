package view.clients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.Prodotto;
import models.Utente;
import utils.Costants;
import utils.Risposta;

public class AdminClient {
	
	private HttpClient client;
	Gson gson;
	
	public AdminClient() {
		this.client=HttpClientBuilder.create().build();
		this.gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	}
	
	public Risposta riceviListaUtenti() {
		Risposta risposta=null;
		try {
			URIBuilder builder = new URIBuilder(Costants.RestUtente.GET_ALL);
			HttpGet getReq = new HttpGet(builder.build());
			HttpResponse response = client.execute(getReq);
			HttpEntity entity= response.getEntity();
			Type type=new TypeToken<LinkedHashSet<Utente>>(){}.getType();
			LinkedHashSet<Utente> utenti = gson.fromJson(EntityUtils.toString(entity),type);
			
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Nessun utente");
			}else {
				risposta = new Risposta(response.getStatusLine().getStatusCode());
				risposta.setUtenti(utenti);;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return risposta;
	}
	
	public Risposta modificaProdotto(Prodotto prodotto) {
		Risposta risposta=null;
		HttpPost postReq = new HttpPost(Costants.RestProdotto.POST);
		postReq.addHeader("Content-Type", "application/json");
		try {
			postReq.setEntity(new StringEntity(gson.toJson(prodotto)));
			HttpResponse response = client.execute(postReq);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta= new Risposta(response.getStatusLine().getStatusCode(),"Interno, non è stato possibile modificare il prodotto");
			}else {
				risposta=new Risposta(response.getStatusLine().getStatusCode(),"Modifica avvenuta con successo");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return risposta;
	}
	
}
