package ModelDAO;

import java.util.HashMap;
import java.util.Map;

import Encrypted.Sha;
import Utils.WSConstants;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import pruebaConexion.DbConexion;

public class UsersModelDAO  
{
	public static Map<String, Object> listAll() throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String, Object> users = new HashMap<>();
		try
		{
			Statement statement = conex.getConnection().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM users");
			if(res.first())
			{
				res = statement.getResultSet();
				ResultSetMetaData meta = res.getMetaData();
				for(int i =0;i<=meta.getColumnCount();i++)
				{
					String key = meta.getColumnName(i);
		            String value = res.getString(key);
		            users.put(key, value);
				}	
			}
			else
			{
				throw new Exception("Se produjo un error desconocido");
			}	
			res.close();
			statement.close();
			conex.desconectar();
				
		}
		catch(Exception e)
		{
			 throw new Exception(e.getLocalizedMessage());
		}
		return users;
		
	}
	public static Map<String, Object> userId (Integer id) throws Exception
	{
		Map<String, Object> user = new HashMap<>();
		DbConexion conex = new DbConexion();
		ResultSet res = null;
		try
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM users WHERE idusers=?");
			preparedStatement.setInt(1, id);
				
			if(preparedStatement.execute())
			{
				res = preparedStatement.getResultSet();
				ResultSetMetaData meta = res.getMetaData();
				for(int i =0;i<=meta.getColumnCount();i++)
				{
					String key = meta.getColumnName(i);
		            String value = res.getString(key);
		            user.put(key, value);
				}	
			}
			else
			{
				throw new Exception("Se produjo un error desconocido");
			}	
			preparedStatement = conex.getConnection().prepareStatement("SELECT COUNT(*) as count FROM friends INNER JOIN users ON friends.friendee = users.idusers WHERE users.idusers = ?");	
			preparedStatement.setInt(1, id);
			if(preparedStatement.execute())
			{
				res = preparedStatement.getResultSet();
				ResultSetMetaData meta = res.getMetaData();
				String key = meta.getColumnName(1);
	            String value = res.getString(key);
	            user.put(key, value);
			}
			else
			{
				throw new Exception("Se produjo un error desconocido");
			}	
			preparedStatement = conex.getConnection().prepareStatement("SELECT COUNT(*) AS count FROM friends INNER JOIN users ON friends.friender = users.idusers WHERE users.idusers = ?");
			preparedStatement.setInt(1, id);
			if(preparedStatement.execute())
			{
				res = preparedStatement.getResultSet();
				ResultSetMetaData meta = res.getMetaData();
				String key = meta.getColumnName(1);
	            String value = res.getString(key);
	            user.put(key, value);
				
			}
			else
			{
				throw new Exception("Se produjo un error desconocido");
			}
			preparedStatement = conex.getConnection().prepareStatement("SELECT COUNT(*) AS count FROM users INNER JOIN tastes ON users.idusers = tastes.idusers WHERE tastes.liked = 1 AND users.idusers = ?");
			preparedStatement.setInt(1, id);
			if(preparedStatement.execute())
			{
				res = preparedStatement.getResultSet();
				ResultSetMetaData meta = res.getMetaData();
				String key = meta.getColumnName(1);
	            String value = res.getString(key);
	            user.put(key, value);
			}
			else
			{
				throw new Exception("Se produjo un error desconocido");
			}
		}
		catch(Exception e)
		{
			throw new Exception(e.getLocalizedMessage());
		}
		
		res.close();
		conex.desconectar();
		return user;
		
	}
	public static int create(Map<String, Object> data)
	{
		String username = (String) data.get("username");
		String password = (String) data.get("password");
		String encryptedpassword = encryptedPassword(password);
		String email = (String) data.get("email");
		String name = (String) data.get("name");
		String surname = (String) data.get("surname");
		Date birthdate = (Date) data.get("birthdate");
		
		String apikey = generateApikey();
		DbConexion conex = new DbConexion();
		ResultSet res = null;
		int mensaje = 0;
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("INSERT INTO users username, name, surname, email, password, bithdate, apikey, entrydate VALUES (?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, (String) username);
			preparedStatement.setString(2, (String) name);
			preparedStatement.setString(3, surname);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, encryptedpassword);
			preparedStatement.setDate(6, birthdate);
			preparedStatement.setString(7, apikey);
			res = preparedStatement.executeQuery();
			if(res.first())
			{	
				mensaje = WSConstants.STATE_CREATE_SUCCESS;
			}
			else
			{
				mensaje = WSConstants.STATE_CREATE_FAILED;
			}	
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		return mensaje;
	}
	
	//Metodo pendiente por revisar
	public static String generateApikey() 
	{
		return null;
	}

	@SuppressWarnings("static-access")
	public static String encryptedPassword(String password) {
		Sha sha = new Sha();
		String encryptedpassword = "";
		try 
		{
			encryptedpassword = sha.hash256(password);
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return encryptedpassword;
	}
	public static String delete(Integer id)
	{
		DbConexion conex = new DbConexion();
		ResultSet res = null;
		String mensaje = "";
		try {
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("DELETE FROM users WHERE idusers = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeQuery();
			res = preparedStatement.getResultSet();
			
			if(res.rowDeleted())
			{
				mensaje = "STATE_DELETE_SUCCESS";
			}
			else
			{
				mensaje = "STATE_DELETE_FAILED";
			}	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			res.close();
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		conex.desconectar();
		return mensaje;
	}
	public static int update(Integer id, Map<String, Object> data) throws SQLException
	{
		String profilepic = (String) data.get("profilepic");
		String apikey = (String) data.get("apikey");
		String surname = (String)data.get("surname");
		String password = (String)data.get("password");
		String encryptedpassword = encryptedPassword(password);
		Integer idusers= (Integer)data.get("idusers");
		String username = (String)data.get("username");
		Date birthdate = (Date)data.get("birthdate");
		String email = (String)data.get("email");
		String name = (String)data.get("name");
		int mensaje = 0;
		ResultSet res = null;
		DbConexion conex = new DbConexion();
		try {
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("UPDATE users SET profilepic  =?, apikey =?,surname =?,password =?,idusers=?, username =?, birthdate =?, email =?, name =? WHERE idusers=?");
			preparedStatement.setString(1, profilepic);
			preparedStatement.setString(2, apikey);
			preparedStatement.setString(3, surname);
			preparedStatement.setString(4, encryptedpassword);
			preparedStatement.setInt(5, idusers);
			preparedStatement.setString(6, username);
			preparedStatement.setDate(7, birthdate);
			preparedStatement.setString(8, email);
			preparedStatement.setString(9, name);
			preparedStatement.setInt(10, id);
			preparedStatement.execute();
			res = preparedStatement.getResultSet();
			if(res.first())
			{
				mensaje = WSConstants.STATE_UPDATE_SUCCESS;
			}
			else
			{
				mensaje = WSConstants.STATE_UPDATE_FAILED;
			}	
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		res.close();
		conex.desconectar();
		return mensaje;
	}
	public static Boolean authenticate(String email, String password) throws Exception
	{
		DbConexion conex = new DbConexion();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT password FROM  users WHERE email =?");
			preparedStatement.setString(1, email);
			preparedStatement.executeQuery();
			if(preparedStatement.execute())
			{
				ResultSet res = preparedStatement.getResultSet();
				return validatePassword(password, res.getString(password));
			}
			else
			{
				return false;
			}	
		} 
		catch (SQLException e) 
		{
			throw new Exception(e.getMessage());
		}
		
	}
	public static Boolean validatePassword(String plainPassword, String hashedPassword)
	{
		return Sha.decodeHas256(plainPassword, plainPassword)== hashedPassword;
	}
	public static HashMap<String , Object> obtainUserByEmail(String email) throws SQLException
	{
		DbConexion conex = new DbConexion();
		PreparedStatement preparedStatement = null;
		preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM users WHERE email =?");
		preparedStatement.setString(1, email);
		HashMap<String, Object> user = null;
		ResultSet res = preparedStatement.executeQuery();
		if(res != null)
		{
			ResultSetMetaData meta = res.getMetaData();
			for(int i =0;i<=meta.getColumnCount();i++)
			{
				String key = meta.getColumnName(i);
	            String value = res.getString(key);
				user.put(key, value);
			}	
		}
		else
		{
			return user;
		}
		return user;	
	}
	public static Boolean validateApiKey(String apikey) throws SQLException
	{
		DbConexion conex = new DbConexion();
		PreparedStatement preparedStatement = null;
		preparedStatement = conex.getConnection().prepareStatement("SELECT COUNT(idusers) FROM users WHERE apikey = ?");
		preparedStatement.setString(1, apikey);
		ResultSet res = preparedStatement.executeQuery();
		if(!res.equals(0))
		{
			return true;
		}
		else
		{
			return false;
		}	
	}
	public static Integer obtainUserID(String apikey) throws SQLException
	{
		DbConexion conex = new DbConexion();
		Integer iduser = null;
		
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT idusers FROM users WHERE apikey = ?");
			preparedStatement.setString(1, apikey);
			ResultSet res = preparedStatement.executeQuery();
			
			if(res!= null)
			{
				return iduser = res.getInt(0);
			}
			else
			{
				return iduser;
			}	
	}
	
}
