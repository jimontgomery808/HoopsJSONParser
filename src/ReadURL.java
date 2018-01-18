import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadURL
{
	private HttpURLConnection connection = null;
	private BufferedReader reader = null;
	private String urlString;
	private String jsonString;

	public ReadURL(String setUrl)
	{
		urlString = setUrl;
	}
	public void read()
	{
	    try
	    {
		    URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
		
		
			InputStream stream = connection.getInputStream();
			
			reader = new BufferedReader(new InputStreamReader(stream));
		
			StringBuffer buffer = new StringBuffer();
			String line = "";
			
			while ((line = reader.readLine()) != null)
			{
				buffer.append(line+"\n");
			}
			jsonString = buffer.toString();
		}
		catch (MalformedURLException e)
		{
		    e.printStackTrace();
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
		finally
		{
		    if (connection != null)
		    {
		        connection.disconnect();
		    }
		    try
		    {
		        if (reader != null)
		        {
		            reader.close();
		        }
		    }
		    catch (IOException e)
		    {
		        e.printStackTrace();
		    }
	
		    
		}
	}
	
	public String getJsonString()
	{
		return jsonString;
	}
    
}
