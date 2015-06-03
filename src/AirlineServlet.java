import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

@WebServlet("/airlines")
public class AirlineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-type", "application/json");
		
		TravelPlan tp = new TravelPlan(getServletContext().getRealPath("/WEB-INF/flightData3.txt"));
		
		JSONArray airlines = new JSONArray();
		for (String airline : tp.myFlightMap.getAirlines()) {
			airlines.add(airline);
		}
		
		response.getWriter().write(airlines.toJSONString());
	}
}
