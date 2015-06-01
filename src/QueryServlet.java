import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-type", "application/json");
		String[] data = {getServletContext().getRealPath("/WEB-INF/flightData3.txt"),
						 getServletContext().getRealPath("/WEB-INF/queryData3.txt")};
		
		// interaction with backend.
		// we create a TravelPlan object.
		TravelPlan tp = new TravelPlan(data);

		ArrayList<QueryAnswerPair> solutions = new ArrayList<QueryAnswerPair>();
		// the creation of 'tp' above loads all the queries etc into the object
		// so, calling the method below is VALID.
		solutions = tp.getResults();
		
		JSONArray jsonResults = new JSONArray();
		for (QueryAnswerPair qap : solutions) {
			JSONObject obj0 = new JSONObject();
		}
		
		
		for (Flight flight : tp.myFlightMap.edges) {
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
		}
		response.getWriter().write(jsonResults.toJSONString());
	}

}
