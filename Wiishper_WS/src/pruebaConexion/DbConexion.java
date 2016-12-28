package pruebaConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConexion 
{
	static String bd = "wiishperdb";
	static String login = "wiishperdb";
	static String password = "Wiishper2016!";
	static String url = "jdbc:mysql://97.74.31.80/"+bd;
	
	Connection connection = null;
	
	public DbConexion()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url,login,password);
			
			if(connection != null)
			{
				System.out.print("Conexion a base de datos ok");
			}
		}
		catch(SQLException e)
		{
			System.out.print(e);
		} catch (ClassNotFoundException e) {
			System.out.print(e);
		}
		
	}
	public Connection getConnection()
	{
		return connection;
	}
	
	public void desconectar()
	{
		connection = null;
	}

}
