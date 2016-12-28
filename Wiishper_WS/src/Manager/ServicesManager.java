package Manager;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/Manager")
@Consumes({ "application/json" })
@Produces({ "application/json"})

public class ServicesManager 
{
	@GET
	@Path("/Signup")
	private JSONObject signup(JSONObject data)
	{
		JSONObject json = new JSONObject(data);
		
		
		return null;
		
	}
	
	@GET
	@Path("/login")
	private ResponseBuilder login(JSONObject data)
	{
		JSONObject json = new JSONObject(data);
		String error = "Ocurrió un error";
		try 
		{
			json.put("email", data.get("email"));
			json.put("password", data.get("password"));
			
			//UsersModel usersModel = new UsersModel();
			return Response.ok();
		}
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			return Response.status(12).entity(error);
		}
		
		
	}

}
