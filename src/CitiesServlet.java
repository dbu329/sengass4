import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

@WebServlet("/cities")
public class CitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-type", "application/json");
		
		TravelPlan tp = new TravelPlan(getServletContext().getRealPath("/WEB-INF/flightData3.txt"));
		
		JSONArray cities = new JSONArray();
		for (String city : tp.myFlightMap.getCities()) {
			cities.add(city);
		}
		
		response.getWriter().write(cities.toJSONString());
	}
}
