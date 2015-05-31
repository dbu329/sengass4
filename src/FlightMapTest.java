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
		
		f2.setOrigin("Melbourne");
		f2.setDestination("Perth");
		
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
		
		System.out.println("neighbours of " + "Sydney : " + fp.getNeighbours("Sydney"));
		System.out.println("Testing All Neighbours:");
		Set<String> list = fp.getAllLocations();
		for (String s : list) {
			System.out.print(s + " ");
		}
		System.out.println();
	}

}
