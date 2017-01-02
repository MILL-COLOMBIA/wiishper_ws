package Utils;

public class WSConstants 
{
	public final static int STATE_DB_ERROR 				= 1;
	public final static int STATE_CREATE_FAILED 		= 2;
	public final static int STATE_CREATE_SUCCESS 		= 0;
	public final static int STATE_INCORRECT_URL 		= 3;
	public final static int STATE_INCORRECT_PARAMS 		= 4;
	public final static int STATE_UNKNOW_FAILURE 		= -1;
	public final static int STATE_WRONG_APIKEY 			= 5;
	public final static int STATE_NO_APIKEY 			= 6;
	public final static int STATE_SUCCESS 				= 7;
	public final static int STATE_NOT_FOUND 			= 8;
	public final static int STATE_DELETE_SUCCESS 		= 9;
	public final static int STATE_DELETE_FAILED 		= 10;
	public final static int STATE_UPDATE_SUCCESS 		= 11;
	public final static int STATE_UPDATE_FAILED 		= 12;
	
	//Operation Constants
	public final static int OPER_SIGNUP 				= 100;
	public final static int OPER_LOGIN 					= 101;
	public final static int OPER_REFRESH_SESSION 		= 102;
	public final static int OPER_UPDATE_USER 			= 103;
	public final static int OPER_SHOW_FRIENDS 			= 200;
	public final static int OPER_SEARCH_PLP   			= 201;
	public final static int OPER_ADD_FRIEND   			= 202;
	public final static int OPER_REM_FRIEND   			= 203;
	public final static int OPER_SYNC_FRIENDS   		= 204;
	public final static int OPER_IS_FRIEND	   			= 205;
	public final static int OPER_SHOW_RND_PRODUCTS   	= 300;
	public final static int OPER_LIKE_PRODUCT		   	= 301;
	public final static int OPER_REJECT_PRODUCT   		= 302;
	public final static int OPER_FILTER_PRODUCTS   		= 303;
	public final static int OPER_SHOW_LIKED_PRODUCTS  	= 304;
	public final static int OPER_SHOW_REJECTED_PRODS  	= 305;
	public final static int OPER_SYNC_PRODUCTS			= 306;
	
	//Mesagge forat constants
	public final static String FIELD_CONTROL			= "control";
	public final static String FIELD_DATA 				= "data";
	public final static String FIELD_OPERATION 			= "operation";
	public final static String FIELD_RESPONSE 			= "response";
	public final static String FIELD_STATE 				= "state";
	public final static String FIELD_MESSAGE 			= "message";
	
}
