import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ScoresJSONReader
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
	private JSONArray array = null;
	private GameData gameData;
	private int startIndex;
	private List<GameData> gameList = new ArrayList<GameData>();

    // Show it.
	public ScoresJSONReader()
	{
		dateFormat = new SimpleDateFormat("yyyyMMdd");
		urlDate = dateFormat.format(date);
		jsonUrl = "https://data.nba.net/prod/v2/" + urlDate + "/scoreboard.json";
	}
	public ScoresJSONReader(String date)
	{
		jsonUrl = "https://data.nba.net/prod/v2/" + date + "/scoreboard.json";
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
		startIndex = jsonString.indexOf("\"games\"");
		jsonStringFinal = jsonString.substring(startIndex + 8, jsonString.length()-2);
		
		try
		{
			array = new JSONArray(jsonStringFinal);
		} 
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < array.length(); i ++)
		{
		    
			JSONObject obj = array.getJSONObject(i);
	
			String gameIdNumber = obj.getString("gameId");
			String isGameActivated = obj.getString("isGameActivated"); // game started
			String startTime = obj.getString("startTimeEastern");	   // start time
			String startDate = obj.getString("startDateEastern");	   // start date
			String clock = obj.getString("clock");					   // game clock
			
			JSONObject quarterInfo = obj.getJSONObject("period");	   // Quarter info JSON Object
			Object quarter = quarterInfo.get("current");			   // quarter #
			Object isHalfTime = quarterInfo.get("isHalftime");		   // half time
			Object isEndOfQuarter = quarterInfo.get("isEndOfPeriod");  // end of quarter
	
			JSONObject vTeam = obj.getJSONObject("vTeam");			   // visiting team JSON Object
		    Object vTeamName = vTeam.get("triCode");				   // team name
		    Object vWinRecord = vTeam.get("win");					   // # wins
		    Object vLossRecord = vTeam.get("loss");					   // # losses
		    Object vScore = vTeam.get("score");					       // # current score
		    
		    JSONObject hTeam = obj.getJSONObject("hTeam");			   // visiting team JSON Object
		    Object hTeamName = hTeam.get("triCode");				   // team name
		    Object hWinRecord = hTeam.get("win");					   // # wins
		    Object hLossRecord = hTeam.get("loss");                    // # losses
		    Object hScore = hTeam.get("score");                        // # current score
		    
		    // Nested JSON Object - used for tv chanel info
		    JSONObject watchInfo = obj.getJSONObject("watch").getJSONObject("broadcast").getJSONObject("broadcasters");
		    
		    Object vTeamWatch = watchInfo.get("vTeam");						 // visiting team JSON object
		    Object hTeamWatch = watchInfo.get("hTeam");						 // home team JSON object
		    
		    // National Broadcast Info
		    JSONArray ntlBroadcastArray = watchInfo.getJSONArray("national");
		    String ntlShortName;
		    String ntlLongName;
		    if( ntlBroadcastArray.length() != 0)
		    {
			    JSONObject ntlBroadcastObj = ntlBroadcastArray.getJSONObject(0);
			    ntlShortName = ntlBroadcastObj.getString("shortName");
			    ntlLongName = ntlBroadcastObj.getString("longName");
		    }
		    else
		    {
		    	ntlShortName = "broadcast not available";
		    	ntlLongName = "broadcast not available";
		    }
		    
		    // Visiting team broadcast info
		    JSONArray vTeamBroadCast = watchInfo.getJSONArray("vTeam");
		    String vShortName = null;
		    String vLongName = null;
		    
		    if(vTeamBroadCast.length() != 0)
		    {
			    JSONObject vTeamBroadcastObj = vTeamBroadCast.getJSONObject(0);
			    vShortName = vTeamBroadcastObj.getString("shortName");
			    vLongName = vTeamBroadcastObj.getString("longName");
		    }
		    else
		    {
		    	vShortName = ntlShortName;
		    	vLongName = ntlLongName;
		    }
	
		    // Home team broadcast info
		    JSONArray hTeamBroadcast = watchInfo.getJSONArray("hTeam");
		    String hShortName = null;
		    String hLongName = null;
		    
		    if(hTeamBroadcast.length() != 0)
		    {
			    JSONObject hTeamBroadcastObj = hTeamBroadcast.getJSONObject(0);
			    hShortName = hTeamBroadcastObj.getString("shortName");
			    hLongName = hTeamBroadcastObj.getString("longName");
		    }
		    else
		    {
		    	hShortName = ntlShortName;
		    	hLongName = ntlLongName;
		    }
	
		    gameData = new GameData(gameIdNumber, Boolean.valueOf(isGameActivated), startTime, startDate, clock, (int)quarter, (Boolean)isHalfTime, (Boolean)isEndOfQuarter,
		    						vTeamName.toString(), vWinRecord.toString(), vLossRecord.toString(), vScore.toString(), hTeamName.toString(), hWinRecord.toString(),
		    						hLossRecord.toString(), hScore.toString(), vShortName.toString(), vLongName, hShortName, hLongName);
		    gameList.add(gameData);
		}
		
	}
	
	public void sendData()
	{
		DatabaseDriver dbDriver = new DatabaseDriver(gameList);
		dbDriver.connect(HOST, PORT, DATABASE, USER, PASSOWRD);
		dbDriver.loadData();
	}
	
	public void printJSON()
	{
	    for(int i = 0; i < gameList.size(); i ++)
	    {
	    	System.out.println(gameList.get(i).toString());
	    }
	}
}

