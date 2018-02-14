import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseDriver
{
	private List<GameData> gameListDB = new ArrayList<GameData>();
	private List<Player> playerListDB = new ArrayList<Player>();
	private Connection myConnection;
	private PreparedStatement statement;
	private final String INSERT_INTO_PLAYERS = "insert into players (first_name, last_name, jersey_number, player_position, height_feet, height_inches, player_weight, player_id,"
            + "team, allStar) "
            + "values(?,?,?,?,?,?,?,?,?,?) "
            + "on duplicate key update "
            + "first_name = values(first_name), last_name = values(last_name), player_position = values(player_position), height_feet = values(height_feet), "
            + "height_inches = values(height_inches), player_weight = values(player_weight), player_id = values(player_id), allStar = values(allStar)";
	
	private final String INSERT_INTO_ALL_SCOREBOARDS = "insert into all_scoreboards (gameId, isGameActivated, startTime, startDate, clock, quarter, isHalfTime, isEndOfQuarter,"
            + "vTeamAbrv, vWinRecord, vLossRecord, vTeamScore, hTeamAbrv, hWinRecord, hLossRecord, "
            + "hTeamScore, vTeamWatchShort, vTeamWatchLong, hTeamWatchShort, hTeamWatchLong) "
            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            + "on duplicate key update "
            + "gameID = values(gameId), isGameActivated = values(isGameActivated), startTime = values(startTime),"
            + "startDate = values(startDate), clock = values(clock), quarter = values(quarter), isHalfTime = values(isHalfTime),"
            + "isEndOfQuarter = values(isEndOfQuarter), vTeamAbrv = values(vTeamAbrv), vWinRecord = values(vWinRecord),"
            + "vLossRecord =  values(vLossRecord), vTeamScore = values(vTeamScore), hTeamAbrv = values(hTeamAbrv), hWinRecord = values(hWinRecord),"
            + "hLossRecord = values(hLossRecord), hTeamScore = values(hTeamScore), vTeamWatchShort = values(vTeamWatchShort),"
            + "vTeamWatchLong = values(vTeamWatchLong), hTeamWatchShort = values(hTeamWatchShort), hTeamWatchLong = values(hTeamWatchLong)";

	public void setGameData(List<GameData> gameListDB)
	{
		this.gameListDB = gameListDB;
	}
	public void setPlayerData(List<Player> playerListDB)
	{
		this.playerListDB = playerListDB;
	}
	public void connect(String hostName, String port, String dbName, String userName, String password)
	{
		String jdbcUrl = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
		try
		{
			// ec2 DB
			myConnection = DriverManager.getConnection(jdbcUrl, userName, password);
			//myConnection = DriverManager.getConnection("jdbc:mysql://ec2-52-14-204-231.us-east-2.compute.amazonaws.com:3306/HoopsDB?useSSL=false", "root", "root");
		} 
		catch (SQLException e)
		{
			System.out.println("CONNECTION FAILED!");
			e.printStackTrace();
		}
	}

	public void loadPlayerData()
	{
		String myStatement = INSERT_INTO_PLAYERS;
		for(int i = 0; i < playerListDB.size(); i ++)
		{
			System.out.println(i);
			try
			{
				Player data = playerListDB.get(i);
				statement= myConnection.prepareStatement(myStatement);
				statement.setString(1, data.getFirstName());
				statement.setString(2, data.getLastName());
				statement.setString(3, data.getNumber());
				statement.setString(4,data.getPosition());
				statement.setString(5, data.getHeightFeet());
				statement.setString(6, data.getHeightInches());
				statement.setString(7, data.getWeight());
				statement.setString(8, data.getId());
				statement.setString(9, data.getTeam());
				statement.setBoolean(10, data.isAllStar());
				
				statement.execute();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void loadGameData()
	{
		String myStatement = INSERT_INTO_ALL_SCOREBOARDS;
		
		for(int i = 0; i < gameListDB.size(); i ++)
		{
			try
			{
				GameData data = gameListDB.get(i);
				statement= myConnection.prepareStatement(myStatement);
				statement.setString(1, data.getGameId());
				statement.setBoolean(2, data.isGameActivated());
				statement.setString(3, data.getStartTime());
				statement.setString(4,data.getStartDate());
				statement.setString(5, data.getClock());
				statement.setInt(6, data.getQuarter());
				statement.setBoolean(7, data.isHalfTime());
				statement.setBoolean(8, data.isEndOfQuarter());
				statement.setString(9, data.getvTeamAbrv());
				statement.setString(10, data.getvTeamWinRecord());
				statement.setString(11, data.getvTeamLossRecord());
				statement.setString(12, data.getvTeamScore());
				statement.setString(13, data.gethTeamAbrv());
				statement.setString(14, data.gethTeamWinRecord());
				statement.setString(15, data.gethTeamLossRecord());
				statement.setString(16, data.gethTeamScore());
				statement.setString(17, data.getvTeamWatchShort());
				statement.setString(18, data.getvTeamWatchLong());
				statement.setString(19, data.gethTeamWatchShort());
				statement.setString(20, data.gethTeamWatchLong());
				
				statement.execute();

				
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
