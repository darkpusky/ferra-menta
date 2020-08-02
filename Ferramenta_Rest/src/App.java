
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


//Per il rest su client o PostMan l'uri è "Nome del DWP dove risiede il rest"/"application path"/"path del servizio"/"path dell'operazione"
@ApplicationPath("/rest")
public class App extends Application{

}
