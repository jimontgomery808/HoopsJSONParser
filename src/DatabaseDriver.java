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
	private Connection myConnection;
	private PreparedStatement statement;
	private String myStatement = "insert into current_games (gameId, isGameActivated, startTime, startDate, clock, quarter, isHalfTime, isEndOfQuarter,"
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
	
	public DatabaseDriver(List<GameData> gameListDB)
	{
		this.gameListDB = gameListDB;
	}

	public void connect(String hostName, String port, String dbName, String userName, String password)
	{
		String jdbcUrl = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
		try
		{
			myConnection = DriverManager.getConnection(jdbcUrl);
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadData()
	{

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
