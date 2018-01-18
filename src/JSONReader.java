import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONReader
{
	
	public static void main(String[] args) throws JSONException
	{
		ReadURL readUrl;
		String url = "https://data.nba.net/prod/v2/20180117/scoreboard.json";
		String jsonString = null;
		String jsonStringFinal = null;
		JSONArray array = null;
		GameData gameData;
		int numGames;
		int startIndex;

        // Show it.
		
		readUrl = new ReadURL(url);
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
		    
		    Object vTeamWatch = watchInfo.get("vTeam");									// visiting team JSON object
		    Object hTeamWatch = watchInfo.get("hTeam");									// home team JSON object
		    
		    // split the JSON strings
		    String hSplitted[] = hTeamWatch.toString().split(":");						
		    String vSplitted[] = vTeamWatch.toString().split(":");
		    // remove the quotations and commas
		    String vWatchShort = vSplitted[1].substring(1, vSplitted[1].length() -12);
		    String vWatchLong = vSplitted[2].substring(1, vSplitted[2].length()-3);
		    String hWatchShort = hSplitted[1].substring(1, hSplitted[1].length() -12);
		    String hWatchLong = hSplitted[2].substring(1, hSplitted[2].length()-3);
		    


		    gameData = new GameData(Boolean.valueOf(isGameActivated), startTime, startDate, clock, (int)quarter, (Boolean)isHalfTime, (Boolean)isEndOfQuarter,
		    						vTeamName.toString(), vWinRecord.toString(), vLossRecord.toString(), vScore.toString(), hTeamName.toString(), hWinRecord.toString(),
		    						hLossRecord.toString(), hScore.toString(), vWatchShort.toString(), vWatchLong.toString(), hWatchShort.toString(), hWatchLong.toString());
		    
		    System.out.println(gameData.getvTeamAbrv());
		}
		
		
		
		
	}
	

}
