package ModelDAO;

import java.util.HashMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import pruebaConexion.DbConexion;

public class TypesModelDAO 
{
	public static HashMap<String, Object> listAll() throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String, Object> types = new HashMap<>();
		Statement statement;
		try 
		{
			statement = conex.getConnection().createStatement();
			ResultSet res =statement.executeQuery("SELECT * FROM types");
			if(res.first())
			{
				res = statement.getResultSet();
				ResultSetMetaData meta = res.getMetaData();
				for(int i =0;i<=meta.getColumnCount();i++)
				{
					String key = meta.getColumnName(i);
		            String value = res.getString(key);
		            types.put(key, value);
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
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return types;	
	}
	public static HashMap<String, Object> get(Integer id) throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String , Object> types = new HashMap<>();
		ResultSet res;
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM types WHERE idtype = ?");
			preparedStatement.setInt(1, id);
			if(preparedStatement.execute())
			{
				res = preparedStatement.getResultSet();
				ResultSetMetaData resultMeta = res.getMetaData();
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            types.put(key, value);
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
		return types;
	}
	public static String create(HashMap<String, Object> data)
	{
		Integer idtype = (Integer) data.get("idtype");
		String name = (String) data.get("name");
		String mensaje = "";
		DbConexion conex = new DbConexion();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("INSERT INTO types idtype,name VALUES (?, ?)");
			preparedStatement.setInt(1, idtype);
			preparedStatement.setString(2, name);
			
			ResultSet res = preparedStatement.executeQuery();
			
			if(res.first())
			{
				mensaje = "STATE_CREATE_SUCCES";
			}
			else
			{
				mensaje = "STATE_CREATE_FAILED";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensaje;
	}
	public static String delete(Integer id)
	{
		DbConexion conex = new DbConexion();
		String mensaje = "";
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("DELETE FROM types WHERE idtype = ?");
			preparedStatement.setInt(1, id);
			ResultSet res = preparedStatement.executeQuery();
			if(res.rowDeleted())
			{
				mensaje = "STATE_DELETE_SUCCESS";
			}
			else
			{
				mensaje = "STATE_DELETE_FAILED";
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
	public String update(Integer id, HashMap<String, Object> data)
	{
		Integer idtype = (Integer)data.get("idtype");
		String name = (String)data.get("name");
		DbConexion conex = new DbConexion();
		PreparedStatement preparedStatement;
		String mensaje = "";
		try 
		{
			preparedStatement = conex.getConnection().prepareStatement("UPDATE types SET idtype = ?, name = ? WHERE idtype = ?");
			preparedStatement.setInt(1, idtype);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, id);
			ResultSet res = preparedStatement.executeQuery();
			if(res.first())
			{
				mensaje = "STATE_UPDATE_SUCCESS";
			}
			else
			{
				mensaje = "STATE_UPDATE_FAILED";
			}	
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return mensaje;
	}
	
	

}
