import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;


public class FlightMapTest {

	@Test
	public void test() {
		FlightMap fp = new FlightMap();
		Flight f1 = new Flight();
		Flight f2 = new Flight();
		Flight f3 = new Flight();
		Flight f4 = new Flight();
		Flight f5 = new Flight();
		Flight f6 = new Flight();
		
		f1.setOrigin("Sydney");
		f1.setDestination("Melbourne");
//		f1.setCost(20);
//		f1.setTime(2, 0);
//		f1.setAirline("AirSeng");
//		f1.setDate(10, 6, 2015);
//		f1.setTravelTime(120);
		
		f2.setOrigin("Melbourne");
		f2.setDestination("Perth");
//		f2.setCost(35);
//		f2.setTime(5, 30);
//		f2.setAirline("AirSeng");
//		f2.setDate(10, 6, 2015);
//		f2.setTravelTime(120);
		
		f3.setOrigin("Sydney");
		f3.setDestination("Adelaide");
		
		f4.setOrigin("Adelaide");
		f4.setDestination("Perth");
		
		f5.setOrigin("Perth");
		f5.setDestination("Sydney");
		
		f6.setOrigin("Sydney");
		f6.setDestination("Adelaide");
		
		fp.addFlight(f1);
		fp.addFlight(f2);
		fp.addFlight(f3);
		fp.addFlight(f4);
		fp.addFlight(f5);
		fp.addFlight(f6);
		
		System.out.println("edges = " + fp);
		
		// So now, to call getNeighbours, we pass in a Flight.
		//System.out.println("neighbours of " + "Sydney : " + fp.getNeighbours());
		System.out.println("Testing getting ALL cities:");
		Set<String> list = fp.getAllLocations();
		for (String s : list) {
			System.out.print(s + " ");
		}
		System.out.println();
	}

}
