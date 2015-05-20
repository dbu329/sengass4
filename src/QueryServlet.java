import java.io.File;
import java.io.IOException;

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
		String[] data = {getServletContext().getRealPath("/WEB-INF/flightData.txt"),
						 getServletContext().getRealPath("/WEB-INF/queryData.txt")};
		//TravelPlan tp = new TravelPlan(data);
		JSONArray results = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("city", "Melbourne");
		obj.put("price", 230);
		results.add(obj);
		response.getWriter().write(results.toJSONString());
	}

}
