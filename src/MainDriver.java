import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

public class MainDriver
{

	public static void main(String[] args)
	{		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimedTask(), 0, 10000);
	}

}
