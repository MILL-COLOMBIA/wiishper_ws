package ModelDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import Utils.WSConstants;
import pruebaConexion.DbConexion;

public class FriendsModelDAO 
{
	public static HashMap<String, Object> listAll() throws Exception
	{
		HashMap<String, Object> friends = new HashMap<>();
		DbConexion conex = new DbConexion();
		try 
		{
			Statement statement = conex.getConnection().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM friends");
			ResultSetMetaData resultMeta = res.getMetaData();	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            friends.put(key, value);
				}	
		res.close();
		conex.desconectar();
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return friends;
	}
	public static HashMap<String, Object> get(Integer id, Integer friendee) throws Exception
	{
		DbConexion conex = new DbConexion();
		PreparedStatement preparedStatement;
		HashMap<String, Object> friends = new HashMap<>();
		try 
		{
			preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM friends WHERE friendee = ? AND friender = ?");
			preparedStatement.setInt(2, id);
			preparedStatement.setInt(1, friendee);
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            friends.put(key, value);
				}	
			}
			else
			{
				throw new Exception("Error desconocido");
			}
		res.close();
		conex.desconectar();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return friends;
	}
	public static int create(Map<String, Object> data)
	{
		Integer friender = (Integer) data.get("friender");
		Integer friendee = (Integer) data.get("friendee");
		DbConexion conex = new DbConexion();
		Integer mensaje = 0;
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("INSERT INTO friends (friender, befriend_date, friendee ) VALUES (?, now(), ?) ");
			preparedStatement.setInt(1, friender);
			preparedStatement.setInt(2, friendee);
			
			ResultSet res = preparedStatement.executeQuery();
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
	public static int delete(Integer id, Integer friendee)
	{
		DbConexion conex = new DbConexion();
		int mensaje = 0;
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("DELETE FROM friends WHERE friendee = ? AND friender = ?");
			preparedStatement.setInt(1, friendee);
			preparedStatement.setInt(2, id);
			
			ResultSet res = preparedStatement.executeQuery();
			if(res.rowDeleted())
			{
				mensaje = WSConstants.STATE_DELETE_SUCCESS;
			}
			else
			{
				mensaje = WSConstants.STATE_DELETE_FAILED;
			}
			res.close();
			conex.desconectar();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return mensaje;
	}
	public static String update(Integer id, HashMap<String, Object> data)
	{
		Integer friender = (Integer) data.get("friender");
		Date befriend_date = (Date) data.get("befriend_date");
		Integer friendee = (Integer)data.get("friendee");
		String mensaje = "";
		DbConexion conex = new DbConexion();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("UPDATE friends SET friender = ?, befriend_date = ?, friendee = ? WHERE friendee = ?");
			preparedStatement.setInt(1, friender);
			preparedStatement.setDate(2, befriend_date);
			preparedStatement.setInt(3, friendee);
			preparedStatement.setInt(4, id);
			ResultSet res = preparedStatement.executeQuery();
			if(res.first())
			{
				mensaje = "STATE_CREATE_SUCCES";
			}
			else
			{
				mensaje = "STATE_CREATE_FAILED";
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return mensaje;
	}
	public static Map<String, Object> getFriends(Integer userID, Integer friendID) throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String, Object> getfriends = new HashMap<>();
		PreparedStatement preparedStatement = null;
		try 
		{
			if(friendID.equals(null))
			{

				preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM friends WHERE friender = ? AND friender = ?");
			
				preparedStatement.setInt(1, userID);
				preparedStatement.setInt(2, friendID);
			}
			
			else
			{
				preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM friends WHERE friender = ?");
				preparedStatement.setInt(1, userID);
			}
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            getfriends.put(key, value);
				}	
			}
			else
			{
				throw new Exception("Error desconocido");
			}
		res.close();
		conex.desconectar();
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return getfriends;
	}
	public static HashMap<String, Object> getNOFriends(Integer userID) throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String, Object> getNOfriends = new HashMap<>();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT DISTINCT(idusers) FROM users LEFT JOIN friends ON (idusers = friender) WHERE idusers <> ? AND idusers NOT IN (SELECT DISTINCT(friendee) FROM friends WHERE friender = ?");
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, userID);
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            getNOfriends.put(key, value);
				}	
			}
			else
			{
				throw new Exception("Error desconocido");
			}
			res.close();
			conex.desconectar();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		return getNOfriends; 
	}
	
}
