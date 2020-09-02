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
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.Prodotto;
import models.Storico;
import models.Utente;
import utils.Costants;
import utils.Risposta;

public class Client {
	
	private HttpClient client;
	Gson gson;
	
	public Client() {
		this.client=HttpClientBuilder.create().build();
		this.gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	}
	
	public Risposta registrazione(Utente utente) {
		Risposta risposta=null;
		HttpPut putReq = new HttpPut(Costants.RestUtente.PUT);
		putReq.addHeader("Content-Type", "application/json");
		try {
			putReq.setEntity(new StringEntity(gson.toJson(utente)));
			HttpResponse response = client.execute(putReq);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta=new Risposta(response.getStatusLine().getStatusCode(),"Username o email già esistente");
			}else {
				risposta=new Risposta(response.getStatusLine().getStatusCode(),"Utente registrato correttamente!");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return risposta;
	}
	
	public Risposta login(String username) {
		Risposta risposta = null;
		try {
			URIBuilder builder = new URIBuilder(Costants.RestUtente.GET_USERNAME);
			builder.setParameter("username", username);
			HttpGet getReq = new HttpGet(builder.build());
			HttpResponse response = client.execute(getReq);
			HttpEntity entity= response.getEntity();
			Utente utente = gson.fromJson(EntityUtils.toString(entity), Utente.class);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Non è stato possibile trovare un account con questo username");
			}else {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),utente);
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
	
	
	public Risposta modificaUtente(Utente utente) {
		Risposta risposta = null;
		HttpPost postReq = new HttpPost(Costants.RestUtente.POST);
		postReq.addHeader("Content-Type", "application/json");
		try {
			postReq.setEntity(new StringEntity(gson.toJson(utente)));
			HttpResponse response = client.execute(postReq);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta= new Risposta(response.getStatusLine().getStatusCode(),"Interno, ci scusiamo per il disagio");
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
	
	public Risposta eliminaUtente(Utente utente) {
		Risposta risposta = null;
		try {
			URIBuilder builder = new URIBuilder(Costants.RestUtente.DELETE);
			builder.setParameter("username", utente.getUsername());
			HttpDelete deleteReq = new HttpDelete(builder.build());
			HttpResponse response = client.execute(deleteReq);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Non è stato possibile cancellare l'account");
			}else {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Account cancellato con successo");
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
	
	public Risposta riceviProdotti() {
		Risposta risposta=null;
		
		try {
			URIBuilder builder = new URIBuilder(Costants.RestProdotto.GET_ALL);
			HttpGet getReq = new HttpGet(builder.build());
			HttpResponse response = client.execute(getReq);
			HttpEntity entity= response.getEntity();
			Type type=new TypeToken<LinkedHashSet<Prodotto>>(){}.getType();
			LinkedHashSet<Prodotto> prodotti = gson.fromJson(EntityUtils.toString(entity),type);
			
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Nessun prodotto");
			}else {
				risposta = new Risposta(response.getStatusLine().getStatusCode());
				risposta.setProdotti(prodotti);
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
	
	public Risposta compra(LinkedHashSet<Storico> storico) {
		Risposta risposta=null;
		HttpPut putReq = new HttpPut(Costants.RestStorico.PUT);
		putReq.addHeader("Content-Type", "application/json");
		try {
			putReq.setEntity(new StringEntity(gson.toJson(storico)));
			HttpResponse response = client.execute(putReq);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta=new Risposta(response.getStatusLine().getStatusCode(),"Errore durante la fase di acquisto");
			}else {
				risposta=new Risposta(response.getStatusLine().getStatusCode(),"Il tuo acquisto è stato inoltrato,controlla il tuo storico per restare aggiornato");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return risposta;
	}
	
	public Risposta richiediStoricoData(Utente utente) {
		Risposta risposta=null;
		try {
			URIBuilder builder = new URIBuilder(Costants.RestStorico.GET_DATA);
			builder.setParameter("id", ""+utente.getId());
			HttpGet getReq = new HttpGet(builder.build());
			HttpResponse response = client.execute(getReq);
			HttpEntity entity= response.getEntity();
			Type type=new TypeToken<LinkedHashSet<Storico>>(){}.getType();
			LinkedHashSet<Storico> storico = gson.fromJson(EntityUtils.toString(entity),type);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Non è stato possibile richiedere lo storico");
			}else {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Richiesta storico andata a buon fine");
				risposta.setStorico(storico);
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
	
	public Risposta richiediStoriciData(Utente utente,String data) {
		Risposta risposta=null;
		try {
			URIBuilder builder = new URIBuilder(Costants.RestStorico.GET_BY_DATA);
			builder.setParameter("id", ""+utente.getId()).setParameter("data", data);
			HttpGet getReq = new HttpGet(builder.build());
			HttpResponse response = client.execute(getReq);
			HttpEntity entity= response.getEntity();
			Type type=new TypeToken<LinkedHashSet<Storico>>(){}.getType();
			LinkedHashSet<Storico> storico = gson.fromJson(EntityUtils.toString(entity),type);
			if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Non è stato possibile richiedere lo storico");
			}else {
				risposta = new Risposta(response.getStatusLine().getStatusCode(),"Richiesta storico andata a buon fine");
				risposta.setStorico(storico);
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
	
}
