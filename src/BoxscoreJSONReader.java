import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BoxscoreJSONReader
{
	private final String HOST = "ec2-52-14-204-231.us-east-2.compute.amazonaws.com";
	private final String PORT = "3306";
	private final String DATABASE = "HoopsDB?useSSL=false";
	private final String USER = "jimontgomery";
	private final String PASSOWRD = "8159081590Jim";
	
	private ReadURL readUrl;
	private String jsonUrl;
	private String jsonString = null;
	private JSONArray jsonArray = null;
	private List<BoxScore> allBoxScores = new ArrayList<BoxScore>();
	private String gameID;
	private String date;
	
	public BoxscoreJSONReader(String date, String gameID)
	{
		jsonUrl = "http://data.nba.net/data/10s/prod/v1/" + date + "/" + gameID + "_boxscore.json";
		this.gameID = gameID;
		this.date = date;
	}
	
	public List<BoxScore> getBSList()
	{
		return allBoxScores;
	}
	
	public void readData() throws JSONException
	{
		readUrl = new ReadURL(jsonUrl);
		readUrl.read();
		jsonString = readUrl.getJsonString();
		
		try
		{
			JSONObject completeObject = new JSONObject(jsonString);
			JSONObject activePlayersObj = completeObject.getJSONObject("stats");
			jsonArray = activePlayersObj.getJSONArray("activePlayers");
		} 
		catch (JSONException e)
		{
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < jsonArray.length(); i ++)
		{
			JSONObject obj = jsonArray.getJSONObject(i);
	
			String teamId     = obj.getString("teamId");
			String playerId   = obj.getString("personId");
			String isOnCourt  = obj.getString("isOnCourt");   
			String minutes 	  = obj.getString("min");	   
			String fgm 		  = obj.getString("fgm");
			String fga 		  = obj.getString("fga");
			String fg_pct 	  = obj.getString("fgp");
			String fg3m 	  = obj.getString("ftm");
			String fg3a 	  = obj.getString("fta");
			String fg3_pct 	  = obj.getString("ftp");
			String ftm 	      = obj.getString("tpm");
			String fta 		  = obj.getString("tpa");
			String ft_pct 	  = obj.getString("tpp");
			String oreb 	  = obj.getString("offReb");
			String dreb 	  = obj.getString("defReb");
			String reb 		  = obj.getString("totReb");
			String ast 		  = obj.getString("assists");
			String stl 		  = obj.getString("steals");
			String blk 		  = obj.getString("blocks");
			String to 		  = obj.getString("turnovers");
			String pf 		  = obj.getString("pFouls");
			String points     = obj.getString("points");
			String plus_minus = obj.getString("plusMinus");
			BoxScore boxscore = new BoxScore(teamId, playerId, isOnCourt, minutes, gameID, date, Integer.valueOf(fgm), Integer.valueOf(fga), Double.valueOf(fg_pct), 
					                         Integer.valueOf(fg3m), Integer.valueOf(fg3a), Double.valueOf(fg3_pct), Integer.valueOf(ftm), Integer.valueOf(fta), 
					                         Double.valueOf(ft_pct), Integer.valueOf(oreb), Integer.valueOf(dreb), Integer.valueOf(reb), Integer.valueOf(ast), 
					                         Integer.valueOf(stl), Integer.valueOf(blk), Integer.valueOf(to), Integer.valueOf(pf), Integer.valueOf(points), Integer.valueOf(plus_minus));
			
			allBoxScores.add(boxscore);
		}
		
	}
	public void sendData() throws SQLException
	{
		DatabaseDriver boxscoreDB = new DatabaseDriver();
		boxscoreDB.setBoxScoreData(allBoxScores);
		boxscoreDB.connect(HOST, PORT, DATABASE, USER, PASSOWRD);
		boxscoreDB.loadBoxScoreData();
	}
		
}

