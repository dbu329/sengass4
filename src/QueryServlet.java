import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-type", "application/json");
		
		Map<String, String[]> params = request.getParameterMap();
		for (String p : params.keySet()) {
			System.out.println(p);
			System.out.println(params.get(p)[0]);
		}
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
		ArrayList<String> order = new ArrayList<String>();
		order.add("Time");
		order.add("Cost");
		int amount = Integer.parseInt(params.get("ips")[0]);
		//Query query = new Query(time, origin, destination, order, amount);
		
		String[] data = {getServletContext().getRealPath("/WEB-INF/flightData3.txt"),
						 getServletContext().getRealPath("/WEB-INF/queryData3.txt")};
		
		//TravelPlan tp = new TravelPlan(data);
		ArrayList<Query> queryList = new ArrayList<Query>();
		//queryList.add(query);
		/*List<QueryAnswerPair> results = tp.doAnswers(queryList);
		
		JSONArray jsonResults = new JSONArray();
		for (QueryAnswerPair qap : results) {
			//qap.answer.get(0).flightPlan.
			System.out.println(qap.answer.get(0).flightPlan.getListOfFlights());
		}*/
		
		
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
		//response.getWriter().write(jsonResults.toJSONString());
	}

}
