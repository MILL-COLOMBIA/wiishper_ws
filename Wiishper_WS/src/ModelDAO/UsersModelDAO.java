package ModelDAO;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ModelVO.UsersModelVO;
import pruebaConexion.DbConexion;

public class UsersModelDAO  
{
	public ArrayList<UsersModelVO> listAll() throws Exception
	{
		DbConexion conex = new DbConexion();
		ArrayList<UsersModelVO> array = new ArrayList<>();
		try
		{
			Statement statement = conex.getConnection().createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM users");
			if(res.first())
			{
				while(res.next())
				{
					array.add((UsersModelVO) res);
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
		return array;
		
	}
	public HashMap<String, Object> userId (Integer id)
	{
		HashMap<String, Object> user = new HashMap<>();
		DbConexion conex = new DbConexion();
		ResultSet res = null;
		try 
		{
			PreparedStatement preparedStatement = conex.getConnection().prepareStatement("SELECT * FROM users WHERE idusers=?");
			preparedStatement.setInt(1, id);
			
			if(preparedStatement.execute())
			{
				res = preparedStatement.getResultSet();
				user = res;
			}	
			
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		return user;
		
	}
	
	

}
