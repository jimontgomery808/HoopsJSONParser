import java.util.TimerTask;

import org.json.JSONException;

public class TimedTask extends TimerTask
{

	@Override
	public void run()
	{
		ScoresJSONReader reader = new ScoresJSONReader();
		try
		{
			reader.readData();
		} 
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.sendData();	
		reader.printJSON();
		System.out.println("Running...");
	}

}
