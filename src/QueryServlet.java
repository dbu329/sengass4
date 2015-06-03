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
		int hour = Integer.parseInt(params.get("time")[0].split(":")[0]);
		int min = Integer.parseInt(params.get("time")[0].split(":")[1]);
		time.set(Calendar.HOUR_OF_DAY, hour);
		time.set(Calendar.MINUTE, min);
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
			JSONArray flights = new JSONArray();
			for (Flight f : path.getFlights()) {
				JSONObject flight = new JSONObject();
				flight.put("airline", f.getAirline());
				flight.put("price", f.getCost());
				flight.put("origin", f.getOrigin());
				flight.put("destination", f.getDestination());
				SimpleDateFormat timeF = new SimpleDateFormat("HH:mm");
				timeF.setTimeZone(f.getDate().getTimeZone());
				SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
				dateF.setTimeZone(f.getDate().getTimeZone());
				flight.put("date", dateF.format(f.getDate().getTime()));
				flight.put("time", timeF.format(f.getDate().getTime()));
				flight.put("duration", f.getDuration());
				flights.add(flight);
			}
			obj.put("flights", flights);
			jsonResults.add(obj);
		}
		
		response.getWriter().write(jsonResults.toJSONString());
	}

}
