import static org.junit.Assert.*;

import org.junit.Test;


public class FlightMapTest {

	@Test
	public void test() {
		FlightMap fp = new FlightMap();
		Flight f1 = new Flight();
		Flight f2 = new Flight();
		
		f1.setOrigin("Sydney");
		f1.setDestination("Melbourne");
		
		f2.setOrigin("Melbourne");
		f2.setDestination("Perth");
		
		fp.addFlight(f1);
		fp.addFlight(f2);
		
		System.out.println("edges = " + fp);
	}

}
