import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayersJSONReader
{
	private final String HOST = "ec2-52-14-204-231.us-east-2.compute.amazonaws.com";
	private final String PORT = "3306";
	private final String DATABASE = "HoopsDB?useSSL=false";
	private final String USER = "jimontgomery";
	private final String PASSOWRD = "8159081590Jim";
	
	private ReadURL readUrl;
	private String urlDate;
	private Date date = new Date();
	private DateFormat dateFormat;
	private String jsonUrl;
	private String jsonString = null;
	private String jsonStringFinal = null;
	private JSONArray jsonArray = null;
	private GameData gameData;
	private int startIndex;
	private List<GameData> gameList = new ArrayList<GameData>();

	
	public PlayersJSONReader()
	{
		jsonUrl = "http://www.nba.com/players/active_players.json";
	}
	public String getDate()
	{
		return urlDate;
	}
	public void readData() throws JSONException
	{
		readUrl = new ReadURL(jsonUrl);
		readUrl.read();
		jsonString = readUrl.getJsonString();
		System.out.print(jsonString);
		
		try
		{
			jsonArray = new JSONArray(jsonString);
		} 
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < jsonArray.length(); i ++)
		{
		    
			JSONObject obj = jsonArray.getJSONObject(i);
	
			String firstName = obj.getString("firstName");
			String lastName = obj.getString("lastName"); 
			String number = obj.getString("jersey");	   
			String position = obj.getString("pos");	   
			String heightFeet = obj.getString("heightFeet");
			String heightInches = obj.getString("heightInches");
			String weight = obj.getString("weightPounds");
			String id = obj.getString("personId");
			
			JSONObject teamObject = obj.getJSONObject("teamData");
			
			
			System.out.println(firstName + ", " + lastName + ", " + number + ", " + position + ", " + heightFeet + "'" + heightInches
					         + ", " + weight + ", " + id);
		}
		
	}
	
	public void sendData()
	{
		DatabaseDriver dbDriver = new DatabaseDriver(gameList);
		dbDriver.connect(HOST, PORT, DATABASE, USER, PASSOWRD);
		dbDriver.loadData("PLAYERS");
	}
	
	public void printJSON()
	{
	    for(int i = 0; i < gameList.size(); i ++)
	    {
	    	System.out.println(gameList.get(i).toString());
	    }
	}
}
