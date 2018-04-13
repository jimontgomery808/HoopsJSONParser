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
	private List<BoxScore> boxscoreListDB  = new ArrayList<BoxScore>();
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
	
	private final String INSERT_INTO_CURR_BS = "insert ignore into all_boxscores (teamId, date, gameId, playerId, is_on_court,"
			+ "minutes, fgm, fga, fg_pct, fg3m, fg3a, fg3_pct, ftm, fta, ft_pct, oreb, dreb, reb, ast, stl, blk, turnovers, pf, points, plus_minus)"
            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            + "on duplicate key update "
            + "teamId = values(teamId), playerId = values(playerId), is_on_court = values(is_on_court),"
            + "minutes = values(minutes), fgm = values(fgm), fga = values(fga),"
            + "fg_pct = values(fg_pct), fg3m = values(fg3m), fg3a = values(fg3a),"
            + "fg3_pct =  values(fg3_pct), ftm = values(ftm), fta = values(fta), ft_pct = values(ft_pct),"
            + "oreb = values(oreb), dreb = values(dreb), reb = values(reb),"
            + "ast = values(ast), stl = values(stl), blk = values(blk), turnovers = values(turnovers), pf = values(pf), points = values(points), plus_minus = values(plus_minus)";

	public void setGameData(List<GameData> gameListDB)
	{
		this.gameListDB = gameListDB;
	}
	public void setPlayerData(List<Player> playerListDB)
	{
		this.playerListDB = playerListDB;
	}
	public void setBoxScoreData(List<BoxScore> boxscoreListDB)
	{
		this.boxscoreListDB = boxscoreListDB;
	}
	
	public void connect(String hostName, String port, String dbName, String userName, String password)
	{
		String jdbcUrl = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
		try
		{
			// ec2 DB
			myConnection = DriverManager.getConnection(jdbcUrl, userName, password);
			//myConnection = DriverManager.getConnection("jdbc:mysql://ec2-52-14-204-231.us-east-2.compute.amazonaws.com:3306/HoopsDB?useSSL=false", "jimontgomery", "81590Jim");
		} 
		catch (SQLException e)
		{
			System.out.println("CONNECTION FAILED!");
			e.printStackTrace();
		}
	}

	public void loadPlayerData() throws SQLException
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
				
				statement.addBatch();
				//statement.execute();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		statement.executeBatch();
	}
	public void loadBoxScoreData() throws SQLException
	{
		String myStatement = INSERT_INTO_CURR_BS;

		statement= myConnection.prepareStatement(myStatement);
		for(int i = 0; i < boxscoreListDB.size(); i ++)
		{
			try
			{
				BoxScore data = boxscoreListDB.get(i);
				statement.setString(1, data.getTeamId());
				statement.setString(2, data.getDate());
				statement.setString(3, data.getGameId());
				statement.setString(4, data.getPlayer_id());
				statement.setString(5, data.getIsOnCourt());
				statement.setString(6, data.getMinutes());
				statement.setInt(7, data.getFgm());
				statement.setInt(8, data.getFga());
				statement.setDouble(9, data.getFg_pct());
				statement.setInt(10, data.getFg3m());
				statement.setInt(11, data.getFg3a());
				statement.setDouble(12, data.getFg3_pct());
				statement.setInt(13, data.getFg3m());
				statement.setInt(14, data.getFta());
				statement.setDouble(15, data.getFt_pct());
				statement.setInt(16, data.getOreb());
				statement.setInt(17, data.getDreb());
				statement.setInt(18, data.getReb());
				statement.setInt(19, data.getAst());
				statement.setInt(20, data.getStl());
				statement.setInt(21, data.getBlk());
				statement.setInt(22, data.getTo());
				statement.setInt(23, data.getPf());
				statement.setInt(24, data.getPoints());
				statement.setInt(25, data.getPlus_minus());
				statement.addBatch();
				//statement.execute();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		statement.executeBatch();
	}
	public void loadGameData() throws SQLException
	{
		String myStatement = INSERT_INTO_ALL_SCOREBOARDS;

		statement= myConnection.prepareStatement(myStatement);
		for(int i = 0; i < gameListDB.size(); i ++)
		{
			try
			{
				GameData data = gameListDB.get(i);
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
				
				statement.addBatch();
				//statement.execute();

				
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		statement.executeBatch();
	}
}
