package ModelDAO;

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
	public static ArrayList<ProductsModelVO> listAll() throws Exception
	{
		DbConexion conex = new DbConexion();
		ArrayList<ProductsModelVO> products = new ArrayList<>();
		try 
		{
			Statement statement = conex.getConnection().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM products");
			if(res.first())
			{
				while(res.next())
				{
					products.add((ProductsModelVO) res);
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
	public static ArrayList<ProductsModelVO> getByStore(Integer storeid, Integer quantity) throws Exception
	{
		DbConexion conex = new DbConexion();
		ArrayList<ProductsModelVO> products = new ArrayList<>();
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM products WHERE idstore = ?");
			preparedStatement.setInt(1, storeid);
			ResultSet res = preparedStatement.executeQuery();
			if(res.first())
			{
				while(res.next())
				{
					products.add((ProductsModelVO) res);
					quantity--;
					if(quantity<=0)
						break;
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
	
	

}
