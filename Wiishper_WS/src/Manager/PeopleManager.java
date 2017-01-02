package Manager;

import java.util.HashMap;
import java.util.Map;

import ModelDAO.FriendsModelDAO;
import Utils.WSConstants;

public class PeopleManager 
{
	public Map<String, Object> process(Integer operation, Map<String, Object> data) throws Exception 
	{
		Integer userID = AccessManager.authorize();
		switch (operation) 
		{
			case WSConstants.OPER_SHOW_FRIENDS:
				return this.listFriends(userID, data);
			case WSConstants.OPER_SEARCH_PLP:
				return this.listPeople(userID, data);
			case WSConstants.OPER_ADD_FRIEND:
				return this.addFriend(userID, data);
			case WSConstants.OPER_REM_FRIEND:
				return this.unfriend(userID, data);
			case WSConstants.OPER_SYNC_FRIENDS:
				// pendiente desarrollo
			case WSConstants.OPER_IS_FRIEND:
				return this.isFriend(userID, data);
		default:
			throw new Exception("Operación no valida");
		}	
		
	}

	private Map<String, Object> isFriend(Integer userID, Map<String, Object> data) throws Exception 
	{
		if(!data.get("friendee").equals(null))
		{
			Map<String, Object> result = FriendsModelDAO.get(userID, (Integer) data.get("friendee"));
			Map<String, Object> message = new HashMap<>();
			if(result != null)
			{
				message.put(WSConstants.FIELD_MESSAGE, true);
				return message;
			}
			else
			{
				message.put(WSConstants.FIELD_MESSAGE, false);
				return message;
			}	
		}
		else
		{
			throw new Exception("Friend id cannot be null");
		}	
	}

	private Map<String, Object> unfriend(Integer userID, Map<String, Object> data) throws Exception 
	{
		if(!data.get("friendee").equals(null))
		{
			int result = FriendsModelDAO.delete(userID, (Integer) data.get("friendee"));
			Map<String, Object> message = new HashMap<>();
			switch(result)
			{
				case WSConstants.STATE_DELETE_SUCCESS:
					message.put(WSConstants.FIELD_DATA, "unfriend succefull");
					return message;
				case WSConstants.STATE_DELETE_FAILED:
					message.put(WSConstants.FIELD_DATA, "Ocurrio un error");
					return message;
				default:
					throw new Exception("Falla desconocida");
			}
			
		}
		else
		{
			throw new Exception("Friend cannot be null");
		}	
		
	}

	private Map<String, Object> addFriend(Integer userID, Map<String, Object> data) throws Exception 
	{
		if(!data.get("friendee").equals(null))
		{
			//int friendeer = (int) data.get("friendeer");
			//friendeer= userID;
			int result = FriendsModelDAO.create(data); 
			Map<String, Object> message = new HashMap<>();
			switch(result)
			{
			case WSConstants.STATE_CREATE_SUCCESS:
				 message.put(WSConstants.FIELD_DATA, "Registro exitoso");
				 return message;
			case WSConstants.STATE_CREATE_FAILED:
				 message.put(WSConstants.FIELD_DATA, "Registro fallido");
				 return message;
			default:
				throw new Exception("Falla desconocida");
			}
		}
		else
		{
			throw new Exception("Friend id cannot be null");
		}	
		
	}

	private Map<String, Object> listPeople(Integer userID, Map<String, Object> data) throws Exception 
	{
		Map<String, Object> users = FriendsModelDAO.getNOFriends(userID);
		
		if(users.size() > 0)
		{
			return users;
		}
		else
		{
			throw new Exception("Ooooops!! No people found");
		}	
		
	}

	private Map<String, Object> listFriends(int userID, Map<String, Object> data) throws Exception 
	{
		Map<String, Object >friends = new HashMap<>();
		if(data.get("friendee").equals(null))
		{
			friends = FriendsModelDAO.getFriends(userID, null);
		}	
		else
		{
			friends = FriendsModelDAO.getFriends(userID, (Integer) data.get("friendee"));
		}
		if(friends.size() > 0)
		{
			return friends;
		}
		else
		{
			throw new Exception("Ooooops! No friends found");
		}
			
	}
	
}
