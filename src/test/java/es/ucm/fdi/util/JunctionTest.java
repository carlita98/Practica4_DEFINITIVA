package es.ucm.fdi.util;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.model.simulatedObjects.Road;
import es.ucm.fdi.model.simulatedObjects.Vehicle;
/*
public class JunctionTest {
	@Test
	public void moveForwardTest(){
		Road r1 = new Road("r1", 20, 20);
		Road r2 = new Road("r2", 30, 70);
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Junction j3 = new Junction("j3");
		j1.addOutcoming(r1);
		j2.addIncoming(r1);
		j2.addInRoadQueue(r1);
		j2.addOutcoming(r2);
		j3.addIncoming(r2);
		ArrayList<Junction> itinerary = new ArrayList<>();
		itinerary.add(j1);
		itinerary.add(j2);
		itinerary.add(j3);
		Vehicle v = new Vehicle("v1", 30, itinerary );
		v.setActualSpeed(20);
		v.moveForward();
		
		j2.moveForward();
		assertEquals(r2, v.getActualRoad());
	}
}
*/