package ModelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import ModelVO.TastesModelVO;
import pruebaConexion.DbConexion;

public class TastesModelDAO 
{
	public static ArrayList<TastesModelVO> listAll() throws Exception
	{
		DbConexion conex = new DbConexion();
		ArrayList<TastesModelVO> arreglo = new ArrayList<>();
		try 
		{
			Statement statement = conex.getConnection().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM tastes");
			if(res.first())
			{
				while(res.next())
				{
					arreglo.add((TastesModelVO) res);
				}	
			}
			else
			{
				throw new Exception("Se produjo un error desconocido");
			}	
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return arreglo;
	}
	public static HashMap<String, Object> get(Integer id) throws Exception
	{
		DbConexion conex = new DbConexion();
		PreparedStatement preparedStatement;
		HashMap<String, Object> tastes = new HashMap<>();
		try 
		{
			preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM tastes WHERE idproduct = ?");
			preparedStatement.setInt(1, id);
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            tastes.put(key, value);
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
		return tastes;
		
	}
	public static HashMap<String, Object> getByUser(Integer idProduct, Integer userid) throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String , Object> arreglo = new HashMap<>();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM tastes WHERE idproducts = ? AND userid = ? ");
			preparedStatement.setInt(1, idProduct);
			preparedStatement.setInt(2, userid);
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            arreglo.put(key, value);
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
		return arreglo;
	}
	public static ArrayList<TastesModelVO> getByPreferences(Integer iduser, Integer liked) throws Exception
	{
		DbConexion conex = new DbConexion();
		ArrayList<TastesModelVO> tastes = new ArrayList<>();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM tastes WHERE liked = ? AND idusers =?");
			preparedStatement.setInt(1, iduser);
			preparedStatement.setInt(2, liked);
			if(preparedStatement.execute())
			{
				ResultSet res = preparedStatement.executeQuery();
				for(int i =0;i<res.getRow();i++)
				{
					tastes.add((TastesModelVO) res);
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
		return tastes;
	}
	public static String create(HashMap<String, Object> data)
	{
		Integer liked = (Integer) data.get("liked");
		Integer idusers = (Integer) data.get("idusers");
		Integer idproducts = (Integer) data.get("idproducts");
		String mensaje = "";
		DbConexion conex = new DbConexion();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("INSERT INTO tastes inner_date, liked, idusers, idproducts VALUES (now(),?,?,?");
			preparedStatement.setInt(1, liked);
			preparedStatement.setInt(2, idusers);
			preparedStatement.setInt(3, idproducts);
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
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("DELETE FROM tastes WHERE idtype = ?");
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
	public static String update(Integer id, HashMap<String, Object> data)
	{
		Integer liked = (Integer) data.get("liked");
		Integer idusers = (Integer) data.get("idusers");
		Integer idproducts = (Integer) data.get("idproducts");
		String mensaje = "";
		
		DbConexion conex = new DbConexion();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("UPDATE tastes SET inner_date = now(), liked = ? ");
			preparedStatement.setInt(1, liked);
			preparedStatement.setInt(2, idusers);
			preparedStatement.setInt(3, idproducts);
			
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
