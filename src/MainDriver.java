import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

public class MainDriver
{
	static String executeString = "Running...";
	public static void main(String[] args) throws ParseException
	{
		currentGames();
		//fillPastGames();
		//fillFutureGames();
	}
	public static void currentGames()
	{
		System.out.println(executeString);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimedTask(), 0, 5000);
	}
	public static void fillFutureGames()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		  // number of days to add 
		for(int i = 1; i <= 76; i ++)
		{
			cal.add(cal.DAY_OF_MONTH, 1);
			Date nextDay = cal.getTime();
			ScoresJSONReader reader = null;
			try
			{
				reader = new ScoresJSONReader(sdf.format(nextDay).toString());
				reader.readData();
			} 
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader.sendData();
			double d = (((double)i)/76) * 100;
			DecimalFormat df = new DecimalFormat("#0.000");
			System.out.println("row: " + (i) + "/76" + ".........." + df.format(d) + "%");
		}
	}
	public static void fillPastGames()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		  // number of days to add 
		for(int i = 30; i > 0; i --)
		{
			cal.add(cal.DAY_OF_MONTH, -1);
			Date nextDay = cal.getTime();
			ScoresJSONReader reader = null;
			try
			{
				reader = new ScoresJSONReader(sdf.format(nextDay).toString());
				reader.readData();
			} 
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader.sendData();
			double d = ((118 - (double)i)/117) * 100;
			DecimalFormat df = new DecimalFormat("#0.000");
			System.out.println("row: " + (118-i) + "/117" + ".........." + df.format(d) + "%");
		}
	}

}
