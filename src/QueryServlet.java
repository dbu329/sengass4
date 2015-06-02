import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/query")
public class QueryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write("healthy");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-type", "application/json");
		
		Map<String, String[]> params = request.getParameterMap();
		
		String origin = params.get("origin")[0];
		String destination = params.get("destination")[0];
		Date date = null;
		try {
			date = new SimpleDateFormat("DD/MM/yyyy").parse(params.get("date")[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		String airlinePreference = "None";
		List<Comparator<Path>> preferences = new ArrayList<Comparator<Path>>();
		if (params.get("preference")[0].equals("time")) {
			preferences.add(new TravelTimePreference());
			preferences.add(new CostPreference());
			preferences.add(new AirlinePreference(airlinePreference));
		} else if (params.get("preference")[0].equals("cost")) {
			preferences.add(new CostPreference());
			preferences.add(new TravelTimePreference());
			preferences.add(new AirlinePreference(airlinePreference));
		} else {
			airlinePreference = params.get("preference")[0];
			preferences.add(new AirlinePreference(params.get("preference")[0]));
			preferences.add(new CostPreference());
			preferences.add(new TravelTimePreference());
		}
		int amount = Integer.parseInt(params.get("ips")[0]);
		Query query = new Query(time, origin, destination, preferences, amount, airlinePreference);
		
		TravelPlan tp = new TravelPlan(getServletContext().getRealPath("/WEB-INF/flightData3.txt"));

		JSONArray jsonResults = new JSONArray();
		for (Path path : tp.executeQuery(query)) {
			JSONObject obj = new JSONObject();
			obj.put("price", path.getCost());
			obj.put("duration", path.getTotalTime());
			obj.put("flights", path.getFlights().toString());
			jsonResults.add(obj);
		}
		
		/*for (Flight flight : tp.myFlightMap.edges) {
			JSONObject obj = new JSONObject();
			obj.put("airline", flight.getAirline());
			obj.put("price", flight.getCost());
			obj.put("origin", flight.getOrigin());
			obj.put("destination", flight.getDestination());
			SimpleDateFormat time = new SimpleDateFormat("HH:mm");
			time.setTimeZone(flight.getTime().getTimeZone());
			SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
			date.setTimeZone(flight.getTime().getTimeZone());
			obj.put("date", date.format(flight.getTime().getTime()));
			obj.put("time", time.format(flight.getTime().getTime()));
			obj.put("duration", flight.getTravelTime());
			jsonResults.add(obj);
		}*/
		response.getWriter().write(jsonResults.toJSONString());
	}

}
