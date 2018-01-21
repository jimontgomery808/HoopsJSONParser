import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

public class MainDriver
{
	static String executeString = "Running...";
	public static void main(String[] args)
	{
		System.out.println(executeString);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimedTask(), 0, 10000);
	}

}
