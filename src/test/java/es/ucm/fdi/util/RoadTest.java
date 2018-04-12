package es.ucm.fdi.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

import es.ucm.fdi.model.simulatedObjects.*;

public class RoadTest {
	//This test ensure the performance of moveForward for one vehicle
/*	@Test
	public void moveForwardTest() {
		Road r1 = new Road("r1", 20, 50);
		Junction j1 = new Junction("j1");
		j1.addOutcoming(r1);
		Junction j2 = new Junction("j2");
		j2.addIncoming(r1);
		ArrayList<Junction> itinerary = new ArrayList<>();
		itinerary.add(j1);
		itinerary.add(j2);
		Vehicle v = new Vehicle("v1", 30, itinerary );
		
		r1.moveForward();
		assertEquals(20, v.getRoadLocation());
	}
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void popVehicleTest() {
		Road r1 = new Road("r1", 20, 50);
		Junction j1 = new Junction("j1");
		j1.addOutcoming(r1);
		Junction j2 = new Junction("j2");
		j2.addIncoming(r1);
		ArrayList<Junction> itinerary = new ArrayList<>();
		itinerary.add(j1);
		itinerary.add(j2);
		Vehicle v = new Vehicle("v1", 30, itinerary );

		r1.popVehicle(v);
		assertNull(r1.getVehicleList().get(v));
	}*/
}
