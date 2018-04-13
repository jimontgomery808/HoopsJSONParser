
public class BoxScore
{
	//"TEAM_ID","TEAM_ABBREVIATION","TEAM_CITY","PLAYER_ID","PLAYER_NAME","START_POSITION","COMMENT",
	//"MIN","FGM","FGA","FG_PCT","FG3M","FG3A","FG3_PCT","FTM","FTA","FT_PCT","OREB","DREB","REB","AST",
	//"STL","BLK","TO","PF","PTS","PLUS_MINUS"
	private String teamId;
	private String player_id;
	private String isOnCourt;
	private String minutes;
	private String gameId;
	private String date;
	private int fgm;
	private int fga;
	private double fg_pct;
	private int fg3m;
	private int fg3a;
	private double fg3_pct;
	private int ftm;
	private int fta;
	private double ft_pct;
	private int oreb;
	private int dreb;
	private int reb;
	private int ast;
	private int stl;
	private int blk;
	private int to;
	private int pf;
	private int points;
	private int plus_minus;
	public BoxScore(String teamId, String player_id, String isOnCourt, String minutes, String gameId, String date, int fgm,
			int fga, double fg_pct, int fg3m, int fg3a, double fg3_pct, int ftm, int fta, double ft_pct, int oreb,
			int dreb, int reb, int ast, int stl, int blk, int to, int pf, int points, int plus_minus)
	{
		super();
		this.teamId = teamId;
		this.player_id = player_id;
		this.isOnCourt = isOnCourt;
		this.minutes = minutes;
		this.gameId = gameId;
		this.date = date;
		this.fgm = fgm;
		this.fga = fga;
		this.fg_pct = fg_pct;
		this.fg3m = fg3m;
		this.fg3a = fg3a;
		this.fg3_pct = fg3_pct;
		this.ftm = ftm;
		this.fta = fta;
		this.ft_pct = ft_pct;
		this.oreb = oreb;
		this.dreb = dreb;
		this.reb = reb;
		this.ast = ast;
		this.stl = stl;
		this.blk = blk;
		this.to = to;
		this.pf = pf;
		this.points = points;
		this.plus_minus = plus_minus;
	}
	public String getGameId()
	{
		return gameId;
	}
	public void setGameId(String gameId)
	{
		this.gameId = gameId;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getTeamId()
	{
		return teamId;
	}
	public void setTeamId(String teamId)
	{
		this.teamId = teamId;
	}
	public String getPlayer_id()
	{
		return player_id;
	}
	public void setPlayer_id(String player_id)
	{
		this.player_id = player_id;
	}
	public String isOnCourt()
	{
		return isOnCourt;
	}
	public void setOnCourt(String isOnCourt)
	{
		this.isOnCourt = isOnCourt;
	}
	public String getMinutes()
	{
		return minutes;
	}
	public void setMinutes(String minutes)
	{
		this.minutes = minutes;
	}
	public int getFgm()
	{
		return fgm;
	}
	public void setFgm(int fgm)
	{
		this.fgm = fgm;
	}
	public int getFga()
	{
		return fga;
	}
	public void setFga(int fga)
	{
		this.fga = fga;
	}
	public double getFg_pct()
	{
		return fg_pct;
	}
	public void setFg_pct(double fg_pct)
	{
		this.fg_pct = fg_pct;
	}
	public int getFg3m()
	{
		return fg3m;
	}
	public void setFg3m(int fg3m)
	{
		this.fg3m = fg3m;
	}
	public int getFg3a()
	{
		return fg3a;
	}
	public void setFg3a(int fg3a)
	{
		this.fg3a = fg3a;
	}
	public double getFg3_pct()
	{
		return fg3_pct;
	}
	public void setFg3_pct(double fg3_pct)
	{
		this.fg3_pct = fg3_pct;
	}
	public int getFtm()
	{
		return ftm;
	}
	public void setFtm(int ftm)
	{
		this.ftm = ftm;
	}
	public int getFta()
	{
		return fta;
	}
	public void setFta(int fta)
	{
		this.fta = fta;
	}
	public double getFt_pct()
	{
		return ft_pct;
	}
	public void setFt_pct(double ft_pct)
	{
		this.ft_pct = ft_pct;
	}
	public int getOreb()
	{
		return oreb;
	}
	public void setOreb(int oreb)
	{
		this.oreb = oreb;
	}
	public int getDreb()
	{
		return dreb;
	}
	public void setDreb(int dreb)
	{
		this.dreb = dreb;
	}
	public int getReb()
	{
		return reb;
	}
	public void setReb(int reb)
	{
		this.reb = reb;
	}
	public int getAst()
	{
		return ast;
	}
	public void setAst(int ast)
	{
		this.ast = ast;
	}
	public int getStl()
	{
		return stl;
	}
	public void setStl(int stl)
	{
		this.stl = stl;
	}
	public int getBlk()
	{
		return blk;
	}
	public void setBlk(int blk)
	{
		this.blk = blk;
	}
	public int getTo()
	{
		return to;
	}
	public void setTo(int to)
	{
		this.to = to;
	}
	public int getPf()
	{
		return pf;
	}
	public void setPf(int pf)
	{
		this.pf = pf;
	}
	public int getPoints()
	{
		return points;
	}
	public void setPoints(int points)
	{
		this.points = points;
	}
	public int getPlus_minus()
	{
		return plus_minus;
	}
	public void setPlus_minus(int plus_minus)
	{
		this.plus_minus = plus_minus;
	}
	
	public String getIsOnCourt()
	{
		return isOnCourt;
	}
	public void setIsOnCourt(String isOnCourt)
	{
		this.isOnCourt = isOnCourt;
	}
	@Override
	public String toString()
	{
		return "BoxScore [teamId=" + teamId + ", player_id=" + player_id + ", isOnCourt=" + isOnCourt + ", position="
			    + ", minutes=" + minutes + ", fgm=" + fgm + ", fga=" + fga + ", fg_pct=" + fg_pct + ", fg3m="
				+ fg3m + ", fg3a=" + fg3a + ", fg3_pct=" + fg3_pct + ", ftm=" + ftm + ", fta=" + fta + ", ft_pct="
				+ ft_pct + ", oreb=" + oreb + ", dreb=" + dreb + ", reb=" + reb + ", ast=" + ast + ", stl=" + stl
				+ ", blk=" + blk + ", to=" + to + ", pf=" + pf + ", points=" + points + ", plus_minus=" + plus_minus
				+ "]";
	}
	
	
	
	
}
