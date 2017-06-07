import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Serverapp
 */
@WebServlet("/api/weather")
public class Serverapp implements Servlet {

	/**
	 * Default constructor.
	 */
	public Serverapp() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	//Entry Point for the application
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		JSONObject jsonans = null;
		try {
			jsonans = getValue(request.getParameter("city")); //Parsing the city parameter for API request
		} catch (Exception e) {

			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonans.toString()); //Sending the response in form of a JSON Response.

	}

	//Method for developing the JSON Object
	private static JSONObject getValue(String city) throws Exception {

		city = city.replace(" ", "_");
		double[] temp = new double[2];
		temp[0] = fromFirstApi(city);
		temp[1] = fromSecondApi(city);
		double avg = getAverage(temp);
		double std = getStandardDeviation(avg, temp);

		JSONObject answer = new JSONObject();
		answer.put("temp", avg);
		answer.put("std", std);
		answer.put("all", temp);

		return answer;

	}

	//Method to calculate the standard deviation
	private static double getStandardDeviation(double avg, double[] temp) {
		double n = temp.length;
		double dv = 0;
		for (double d : temp) {
			double dm = d - avg;
			dv += dm * dm;
		}

		return Math.sqrt(dv / n);

	}

	//Method to calculate the Average
	private static double getAverage(double[] temp) {
		double sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum += temp[i];
		}

		return (sum / temp.length);
	}

	//Method to calculate Temperature from First Api
	private static Double fromFirstApi(String city) throws IOException, JSONException {
		String key = "40e80daa66514dfaab21c98a003d08e7";
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + key + "&units=imperial";
		StringBuilder sb = getJsonData(url);

		JSONObject object = new JSONObject(sb.toString());
		JSONObject obj = object.getJSONObject("main");
		double val = obj.getDouble("temp");
		return val;

	}

	//Method to calculate Temperature from Second Api
	private static double fromSecondApi(String city) throws Exception {
		String key = "1e4ac434bb8e4f75";
		String url = "http://api.wunderground.com/api/" + key + "/conditions/q/CA/" + city + ".json";
		StringBuilder sb = getJsonData(url);
		JSONObject object = new JSONObject(sb.toString());
		JSONObject obj = object.getJSONObject("current_observation");
		double val = obj.getDouble("temp_f");
		return val;
	}

	//Method to parse the Json Response from the APIs
	private static StringBuilder getJsonData(String url) throws IOException {
		String data = null;
		URL ur = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		while ((data = br.readLine()) != null) {
			sb.append(data + "\n");
		}
		return sb;
	}

}
