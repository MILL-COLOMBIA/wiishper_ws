package Manager;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.JSONException;

import ModelDAO.UsersModelDAO;
import Utils.WSConstants;

public class AccessManager implements Processor 
{
	public Map<String, Object> process(Integer operation, Map<String, Object> data) throws Exception {
		
		int UserID = AccessManager.authorize();
		
		switch(operation)
		{
			case WSConstants.OPER_SIGNUP:
				return this.signup(data);
			case WSConstants.OPER_LOGIN:
				return this.login(data);
			case WSConstants.OPER_UPDATE_USER:
				return this.update(data);
		}
		
		return null;
	}
	
	@GET
	@Path("/Signup")
	private Map<String, Object> signup(Map<String, Object> data)
	{
		Map<String, Object> user = new HashMap<>(data);
		Map<String, Object> message = new HashMap<>();
		int result = UsersModelDAO.create(user);
		switch(result)
		{
			case WSConstants.STATE_CREATE_SUCCESS:
				message.put(WSConstants.FIELD_DATA, "Registro exitoso!");
				return message;
			case WSConstants.STATE_CREATE_FAILED:
				message.put(WSConstants.FIELD_MESSAGE, "Ocurrio un error");
				return message;
			default:
				message.put(WSConstants.FIELD_MESSAGE, "Falla desconocida");
				return message;
		}
		
		
	}
	
	@GET
	@Path("/login")
	private Map<String, Object> login(Map<String, Object> data) throws Exception
	{
		try 
		{
			String email = 	(String) data.get("email");
			String password = (String) data.get("password");

			if(UsersModelDAO.authenticate(email, password))
			{
				Map<String, Object> users = UsersModelDAO.obtainUserByEmail(email);
				if(users != null)
				{
					// Pendiente terminar el metodo
				}	
			}
			
		}
		catch (JSONException e) 
		{
			throw new Exception();
		}
		return data;		
	}
	
	@POST
	@Path("/update")
	private Map<String, Object> update(Map<String, Object> data) throws Exception
	{
		int userdata = (int) data.get("idusers");
		int result = UsersModelDAO.update(userdata, data);
		Map<String, Object> message = new HashMap<>();
		switch (result) 
		{
		case WSConstants.STATE_UPDATE_SUCCESS:
			message.put(WSConstants.FIELD_STATE, "Operación exitosa");
			return message;
		case WSConstants.STATE_UPDATE_FAILED:
			message.put(WSConstants.FIELD_STATE, "Ocurrió un error");
			return message;
		default:
			throw new Exception("Falla desconocida");
		}
			
	}
	//Pendiente
	public static int authorize()
	{
		return (Integer) null;
		
	}
	

}