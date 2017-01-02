package Manager;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/Manager")
@Consumes({ "application/json" })
@Produces({ "application/json"})

public class ServicesManager implements Processor 
{
	private AccessManager acces_manager;
	private PeopleManager people_manager;
	private ProductsManager products_manager;

	public ServicesManager() 
	{
		this.acces_manager = new AccessManager();
		this.people_manager = new PeopleManager();
		this.products_manager = new ProductsManager();
	}
	
	//Pensar en este metodo como lanzar excepciones
	public Map<String, Object> process(Integer operation, Map<String, Object> data) throws Exception {
		if(operation == null)
		{
			throw new Exception("Operación no puede ser null");
		}
		if(operation < 100)
		{
			throw new Exception("Operación no valida");
		}
		switch(operation)
		{
			case 100:
					//Call access manager
					return this.acces_manager.process(operation, data);
			case 200:
					//Call people manager
					return this.people_manager.process(operation, data);
			case 300:
					//Call products manager
					return this.products_manager.process(operation, data);
			default:
					throw new Exception("operacion no implementada");
					
		}
		
	}

	
}
