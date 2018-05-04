package es.ucm.fdi.model.event.builder;

import java.util.*;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;

/**
 * Parse the IniSection and set the type of the event
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public interface EventBuilder {
	/**
	 * An array with the type of the Events that could be created
	 */
	EventBuilder[] bs = new EventBuilder[] { 
			new NewBikeEventBuilder(), 
			new NewCarEventBuilder(),
			new NewVehicleEventBuilder(), 
			new NewDirtEventBuilder(), 
			new NewLanesEventBuilder(),
			new NewRoadEventBuilder(),
			new MakeFaultyVehicleEventBuilder(), 
			new NewRoundRobinEventBuilder(),
			new NewMostCrowedEventBuilder(), 
			new NewJunctionEventBuilder() };

	/**
	 * Method implemented by the different Builders, it declares if the iniSection
	 * correspond to that Event
	 * 
	 * @param sec
	 * @return
	 */
	public Event parse(IniSection sec);

	/**
	 * Parse an ID
	 * 
	 * @param id
	 * @throws IllegalArgumentException
	 * @return boolean
	 */
	public default boolean isValidId(String id) {
		return id.matches("[a-zA-Z1-9_]++");
	}

	/**
	 * Parse an entire depending on a minimum value
	 * 
	 * @param sec
	 * @param key
	 * @param min
	 * @throws IllegalArgumentException
	 * @return entire
	 */
	public default int parseInt(IniSection sec, String key, int min) {
		int n = 0;
		n = Integer.parseInt(sec.getValue(key));
		if (n < min) {
			throw new IllegalArgumentException("The value of the attribute is out of range");
		} else {
			return n;
		}

	}

	/**
	 * Parse a double depending on a minimun value
	 * 
	 * @param sec
	 * @param key
	 * @param min
	 * @throws IllegalArgumentException
	 * @return double
	 */
	public default double parseDoub(IniSection sec, String key, int min) {
		double n = 0;
		n = Double.parseDouble(sec.getValue(key));
		if (n < min) {
			throw new IllegalArgumentException("The value of the attribute is out of range");
		} else {
			return n;
		}
	}

	/**
	 * Parse an ID array using the method isValidId()
	 * 
	 * @param sec
	 * @param key
	 * @throws IllegalArgumentException
	 * @return ArrayList<String>
	 */
	public default ArrayList<String> parseIdList(IniSection sec, String key) {

		String string = sec.getValue(key);
		int index = 0;
		String[] stringArray = string.split(",");
		ArrayList<String> idList = new ArrayList<>();
		for (int i = 0; i < stringArray.length; i++) {
			if (isValidId(stringArray[i])) {
				idList.add(index, stringArray[i]);
				index++;
			} else {
				throw new IllegalArgumentException("Invalid ID");
			}
		}

		return idList;
	}
}
