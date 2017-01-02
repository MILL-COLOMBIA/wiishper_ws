package ModelDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import ModelVO.ProductsModelVO;
import pruebaConexion.DbConexion;

public class ProductsModelDAO
{
	public static HashMap<String, Object> listAll() throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String, Object> products = new HashMap<>();
		try 
		{
			Statement statement = conex.getConnection().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM products");
			if(res.first())
			{
				res = statement.getResultSet();
				ResultSetMetaData meta = res.getMetaData();
				
				for(int i = 0; i < meta.getColumnCount();i++)
				{
					String key = meta.getColumnName(i);
		            String value = res.getString(key);
		            products.put(key, value);
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
		return products;
	}
	public static HashMap<String, Object> getByStore(Integer storeid, Integer quantity) throws Exception
	{
		DbConexion conex = new DbConexion();
		HashMap<String, Object> products = new HashMap<>();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM products WHERE idstore = ?");
			preparedStatement.setInt(1, storeid);
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
				for(int i = 0; i < resultMeta.getColumnCount();i++)
				{
					String key = resultMeta.getColumnName(i);
		            String value = res.getString(key);
		            products.put(key, value);
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
		return products;
	}
	public static HashMap<String, Object> getByType(Integer userID, Integer typeid, Integer quantity) throws Exception
	{
		DbConexion conex =  new DbConexion();
		HashMap<String, Object> types = new HashMap<>();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT products.name as name, products.price AS price, products.description AS desciption, stores.name AS store, products.idproducts AS idproducts, products.image AS image, products.publishdate AS publishdate "+
			"FROM products INNER JOIN stores ON stores.idstores = products.idstore INNER JOIN types ON products.idtype = types.idtype "+ 
			"WHERE types.idtype = ?  AND products.idproducts NOT IN (SELECT tastes.idproducts FROM tastes WHERE tastes.idusers = ?)");
			preparedStatement.setInt(1, typeid);
			preparedStatement.setInt(2, userID);
			ResultSet res = preparedStatement.executeQuery();
			ResultSetMetaData resultMeta = res.getMetaData();
			if(preparedStatement.execute())
			{	
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
		String image = (String) data.get("image");
		String price = (String) data.get("price");
		String descripcion = (String) data.get("descripcion");
		Integer idproducts = (Integer) data.get("idproducts");
		String name = (String) data.get("name");
		Integer idstore = (Integer) data.get("idstore");
		DbConexion conex = new DbConexion();
		String mensaje = "";
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("INSERT INTO products (idtype, image, price, description, publishdate, idproducts, name, udstore) VALUES (?,?,?,? now(),?,?,?)");
			preparedStatement.setInt(1, idtype);
			preparedStatement.setString(2, image);
			preparedStatement.setString(3, price);
			preparedStatement.setString(4, descripcion);
			preparedStatement.setInt(5, idproducts);
			preparedStatement.setString(6, name);
			preparedStatement.setInt(7, idstore);
			
			ResultSet res = preparedStatement.executeQuery();
			if(res.first())
			{
				mensaje = "STATE_CREATE_SUCCESS";
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
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("DELETE FROM products WHERE idproducts = ?");
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
		Integer idtype = (Integer) data.get("idtype");
		String image = (String) data.get("image");
		String price = (String) data.get("price");
		String descriprion = (String) data.get("description");
		Date publishdate = (Date)data.get("publishdate");
		Integer idproducts = (Integer)data.get("idproducts");
		String name = (String)data.get("name");
		Integer idstore = (Integer)data.get("idstore");
		String mensaje = "";
		DbConexion conex = new DbConexion();
		PreparedStatement preparedStatement;
		try 
		{
			preparedStatement = conex.getConnection().prepareStatement("UPDATE products SET (idtype = ?, image = ?,price = ?, descrption = ?, publishdate = ?, idproducts = ?, name = ?, idstore = ? ");
			preparedStatement.setInt(1, idtype);
			preparedStatement.setString(2, image);
			preparedStatement.setString(3, price);
			preparedStatement.setString(4, descriprion);
			preparedStatement.setDate(5, publishdate);
			preparedStatement.setInt(6, idproducts);
			preparedStatement.setString(7, name);
			preparedStatement.setInt(8, idstore);
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
