import java.lang.reflect.Type;
import java.util.LinkedHashSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ejb.EjbAdmin;
import ejb.EjbClient;
import enums.Stato;
import models.Prodotto;
import models.Storico;

@Stateless
@Path("/storico")
public class ServiceStorico {
	
	@EJB
	private EjbAdmin admin;
	
	@EJB
	private EjbClient client;
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/put")
	public void inserisciStorico(String str) {
		Type type=new TypeToken<LinkedHashSet<Storico>>(){}.getType();
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		LinkedHashSet<Storico> storico = gson.fromJson(str,type);
		client.insertStorico(storico);
	}
	
	@GET
	@Path("/get")
	public String riceviStorico(@QueryParam("id")int id) {
		Storico storico=new Storico();
		Gson gson=new Gson();
		storico=client.getStoricoId(id);
		return gson.toJson(storico);
	}
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashSet<Storico> riceviStorici(@QueryParam("id")int id) {
		LinkedHashSet<Storico> storico= new LinkedHashSet<>();
		storico=client.getStorico(id);
		return storico;
	}
	
	@GET
	@Path("/getAllData")
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashSet<Storico> riceviStoriciData(@QueryParam("id")int id){
		LinkedHashSet<Storico> storico= new LinkedHashSet<>();
		storico=client.getStoriciData(id);
		return storico;
	}
	
	@GET
	@Path("/getAllByData")
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashSet<Storico> riceviStoriciByData(@QueryParam("id")int id,@QueryParam("data")String data){
		LinkedHashSet<Storico> storico= new LinkedHashSet<>();
		storico=client.getStoriciByData(id,data);
		return storico;
	}
	
}
