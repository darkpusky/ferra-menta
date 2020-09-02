package utils;

public class Costants {
	
	//costanti per rest in generale
	public class Rest{
		public static final String REST = "http://localhost:8080/Ferramenta_Rest/rest/";
	}
	
	//costanti per rest dell'utente
	public class RestUtente{
		public static final String REST_UTENTE = Costants.Rest.REST + "utenti/";
		public static final String PUT = REST_UTENTE + "put";
		public static final String GET_USERNAME = REST_UTENTE + "get";
		public static final String POST = REST_UTENTE + "post";
		public static final String DELETE = REST_UTENTE + "delete";
		public static final String GET_ALL = REST_UTENTE + "getAll";
	}
	
	//costanti per rest dei prodotti
	public class RestProdotto{
		public static final String REST_PRODOTTO = Costants.Rest.REST + "prodotti/";
		public static final String GET_ALL = REST_PRODOTTO + "getAll";
		public static final String POST = REST_PRODOTTO + "post";
	}
	
	//costanti per rest dello storico
	public class RestStorico{
		public static final String REST_STORICO = Costants.Rest.REST + "storico/";
		public static final String PUT = REST_STORICO + "put";
		public static final String GET_DATA = REST_STORICO + "getAllData";
		public static final String GET_BY_DATA = REST_STORICO + "getAllByData";
	}
	
	//costanti per le query in generale
	public class Query{
		
	}
	
	//costanti per le query dell'utente
	public class QueryUtente{
		public final static String SELECT_USERNAME= "FROM Utente WHERE username=";
		public final static String SELECT_ALL= "FROM Utente";
	}
	
	//costanti per le query dei prodotti
	public class QueryProdotto{
		public static final String SELECT_ALL = "FROM Prodotto";
	}
	
	//costanti per le query dello storico
	public class QueryStorico{
		public final static String SELECT_IDUTENTE= "FROM Storico WHERE id_utente = ";
		public final static String SELECT_ALLBYDATA =" GROUP BY cast(data_acquisto as date)";
		public static final String SELECT_BYDATA =" AND cast(data_acquisto as date)= ";
	}
	
	//costanti per la validazione o altro
	public class Other{
		public static final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
	}
}
