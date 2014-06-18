package nate;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Security extends Properties
{
	public Security(String PropsFile) throws IOException
	{
		load((InputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream(PropsFile));
	}
	
	public String getProp(String key)
	{
		return super.getProperty(key);
	}
}