import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

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
		TravelPlan tp = new TravelPlan(data);
		JSONArray results = new JSONArray();
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
			results.add(obj);
		}
		response.getWriter().write(results.toJSONString());
	}

}
