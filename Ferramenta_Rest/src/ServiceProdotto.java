import java.util.LinkedHashSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import ejb.EjbAdmin;
import ejb.EjbClient;
import models.Prodotto;

@Stateless
@Path("/prodotti")
public class ServiceProdotto {
	
	@EJB
	private EjbAdmin admin;
	
	@EJB
	private EjbClient client;
	
	@PUT
	@Path("/put")
	public void inserisciProdotto() {
		Prodotto prodotto = new Prodotto();
		prodotto.setCodice("53550");
		prodotto.setPrezzo(5.99);
		prodotto.setQuantita(99);
		prodotto.setNome("Chiodi di garofano");
		prodotto.setMarca("eurospin");
		prodotto.setAttivo(true);
		this.admin.insertProdotto(prodotto);
	}
	
	@GET
	@Path("/get")
	public String riceviProdotto(@QueryParam("id") int id) {
		Prodotto prodotto = new Prodotto();
		prodotto=client.getProdottoId(id);
		Gson gson = new Gson();
		return gson.toJson(prodotto);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAll")
	public LinkedHashSet<Prodotto> riceviProdotti(){
		return client.getProdotti();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/post")
	public void modificaProdotto(Prodotto prodotto) {
		this.admin.updateProdotto(prodotto);
	}
	
	@DELETE
	@Path("/delete")
	public void eliminaProdotto() {
		Prodotto prodotto=new Prodotto();
		prodotto.setId(1);
		prodotto.setCodice("53550");
		prodotto.setPrezzo(5.99);
		prodotto.setQuantita(99);
		prodotto.setNome("Chiodi di garofano");
		prodotto.setMarca("eurospin");
		prodotto.setAttivo(true);
		this.admin.removeProdotto(prodotto);
	}
	
}
