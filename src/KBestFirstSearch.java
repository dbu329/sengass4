import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class KBestFirstSearch {
	
	private FlightMap graph;
	
	public KBestFirstSearch(FlightMap graph) {
		this.graph = graph;
	}

	public List<Path> search(Query query) {
		// if the origin or destination isn't valid, return null 
		if (!graph.getCities().contains(query.getOrigin()) || !graph.getCities().contains(query.getDestination())) {
			return null;
		}
		
		List<Comparator<Path>> preferences = query.getPreferences();
		
		String start = query.getOrigin();
		String finish = query.getDestination();
		String airlineToUse = query.getAirlinePreference();
		
		PriorityQueue<Path> queue = new PriorityQueue<Path>(10, new MultiComparator<Path>(preferences));
		
		//create a new flight from null -> origin
		Flight fake = new Flight(query.getDepartureTime(), null, start, 0, "None", 0);
		
		// Stores the count of shortest paths to EACH city.
		// Then set the count for all of these cities to ZERO
		HashMap<String, Integer> numShortestPaths = new HashMap<String, Integer>();
		for (String s : graph.getCities()) {
			numShortestPaths.put(s, 0);
		}
		/*
		Path path = new Path();
		path.addFlight(fake);
		queue.offer(path); 
		*/
		Calendar queryDate = query.getDepartureTime();
		for (Flight flight : graph.getNeighbours(fake)) {
			Calendar flightDate = flight.getDate();
			if (flightDate.after(queryDate)) { 
				Path path = new Path();
				path.addFlight(flight);
				queue.offer(path);
			}
		} 
		
		List<Path> pathsToFinish = new ArrayList<Path>();
		
		while (!queue.isEmpty() &&  numShortestPaths.get(finish) < query.getNumToDisplay()) {
			// get the highest priority path from the queue
			Path path = queue.poll();
			numShortestPaths.put(path.getCurrentCity(), numShortestPaths.get(path.getCurrentCity())+1);
			 
			if (path.getCurrentCity().equals(finish)) {
				pathsToFinish.add(path);
			}
			if (numShortestPaths.get(path.getCurrentCity()) <= query.getNumToDisplay()) {
				for (Flight flight : graph.getNeighbours(path.getLastFlight())) {
					Path adj = new Path(path.getFlights());
					adj.addFlight(flight);
					queue.offer(adj);
				}
			}
		}
//		System.out.println("Paths Found:");
//		for (Path pl: pathsToFinish) {
//			System.out.println(pl+" Cost:"+pl.getCost() +" Travel Time:" + pl.getTotalTime()+ " Airline Minutes Used" + pl.getAirlineTime(airlineToUse));
//		}
		
		return pathsToFinish;
	}

}
