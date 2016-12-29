package ModelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import ModelVO.StoresModelVO;
import pruebaConexion.DbConexion;

public class StoresModelDAO 
{
	public ArrayList<StoresModelVO> listAll() throws Exception
	{
		DbConexion conex = new DbConexion();
		ArrayList<StoresModelVO> stores = new ArrayList<>();
		try 
		{
			Statement statement = conex.getConnection().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM stores");
			if(res.first())
			{
				while(res.next())
				{
					stores.add((StoresModelVO) res);
				}	
			}
			else
			{
				throw new Exception("Error desconocido");
			}	
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return stores;
	}
	public static HashMap<String, Object> get(Integer id) throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String, Object> stores = new HashMap<>();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM stores WHERE idstores = =");
			preparedStatement.setInt(1, id);
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            stores.put(key, value);
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
		return stores;
	}
	public static String create(HashMap<String, Object> data)
	{
		Integer idstores = (Integer) data.get("idstores");
		String name = (String) data.get("name");
		DbConexion conex = new DbConexion();
		String mensaje = "";
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("INSERT INTO stores (idstores, name) VALUES  (?,?)");
			preparedStatement.setInt(1, idstores);
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
		}
		catch (SQLException e) 
		{
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
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("DELETE FROM stores WHERE idtstores = ?");
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
	
	public static String update(Integer id, HashMap<String , Object> data)
	{
		Integer idstores = (Integer) data.get("idstores");
		String name = (String) data.get("name");
		DbConexion conex = new DbConexion();
		String mensaje = "";
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("UPDATE stores SET idstores = ?, name = ? WHERE idstores = ?");
			preparedStatement.setInt(1, idstores);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, id);
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
	
	

}
