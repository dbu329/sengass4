import static org.junit.Assert.*;

import org.junit.Test;


public class FlightPlanTest {

	@Test
	public void test() {
		Flight f1 = new Flight();
		Flight f2 = new Flight();
		
		f1.setOrigin("Sydney");
		f1.setDestination("Melboure");
		f1.setCost(20);
		f1.setTime(2, 0);
		f1.setAirline("AirSeng");
		f1.setDate(10, 6, 2015);
		f1.setTravelTime(120);
		
		f2.setOrigin("Melbourne");
		f2.setDestination("Perth");
		f2.setCost(35);
		f2.setTime(5, 0);
		f2.setAirline("AirSeng");
		f2.setDate(10, 6, 2015);
		f2.setTravelTime(120);
		
		f1.print();
		f2.print();
		
		FlightPlan p = new FlightPlan();
		p.addFlight(f1);
		assertTrue(p.getTotalCost() == 20);
		assertTrue(p.getAirlineTime() == 0);
		p.incrementAirlineTime(25);
		assertTrue(p.getAirlineTime() == 25);
		assertTrue(p.getTotalTime() == 120);
		p.addFlight(f2);
		
	
	}

}
