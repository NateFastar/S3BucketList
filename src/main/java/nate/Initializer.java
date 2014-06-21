package nate;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Initializer extends HttpServlet
{
	private String access;
	private String secret;
	
	public Initializer()
	{
		try
		{
			Security Manager = new Security("resources/aws.properties");
			access = Manager.getProp("access");
			secret = Manager.getProp("secret");
		}
		catch (IOException e)
		{
			access = "";
			secret = "";
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String bucket = request.getParameter("bucket");
		String prefix = request.getParameter("prefix");
		ArrayList<String> results;
		if (bucket != null && prefix != null && bucket.length() > 0 && prefix.length() > 0)
		{
			Computation computer = new Computation(access, secret, bucket, prefix);
			results = computer.getContents();
		}
		else
		{
			results = new ArrayList<String>();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(results);
		response.setContentType("application/json");
		response.getWriter().write(json);
		response.flushBuffer();
	}
}
