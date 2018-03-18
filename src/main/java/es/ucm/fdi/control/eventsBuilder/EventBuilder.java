package es.ucm.fdi.control.eventsBuilder;
import java.lang.Character;
import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
/**
 * Parse the IniSection and set the type of the event 
 * @author Carla Martínez
 *
 */
public interface EventBuilder {
	/**
	 * An array with the type of the Events that could be created
	 */
	EventBuilder []bs = new EventBuilder[] {new NewBikeEventBuilder(), 
			 new NewCarEventBuilder(),new NewVehicleEventBuilder(),new NewPathEventBuilder(),
			 new NewHighWayEventBuilder(),new NewRoadEventBuilder(),
	new MakeFaultyVehicleEventBuilder(), new NewRoundRobinEventBuilder(), new NewMostCrowedEventBuilder(),
	new NewJunctionEventBuilder ()};
	/**
	 * Method implemented by the different Builders, it declares if the iniSection correspond to that Event
	 * @param sec
	 * @return
	 */
	public Event parse(IniSection sec);
	/**
	 * Go through the array of possibles Events and says which one is the type of the Event created
	 * @param sec
	 * @return Event 
	 */
	public default Event parseSection (IniSection sec) {
		Event e = null;
		for (EventBuilder eb: bs){
			try{
				e = eb.parse(sec);
				if (e != null) break;
			}catch (IllegalArgumentException i){
				throw new IllegalArgumentException("There has been a problem creating an Event", i);
			}
		}
		return e;
	}
	/**
	 * Parse an ID 
	 * @param id
	 * @throws IllegalArgumentException
	 * @return boolean
	 */
	public default boolean isValidId (String id){
		for (int i =0; i < id.length() ; i++){
			if (!Character.isLetterOrDigit(id.charAt(i)) && id.charAt(i)!= '_'){
				throw new IllegalArgumentException ("Invalid ID");
			}
		}
		return true;
	}
	/**
	 * Parse an entire depending on a minimum value
	 * @param sec
	 * @param key
	 * @param min
	 * @throws IllegalArgumentException
	 * @return entire
	 */
	public default int parseInt (IniSection sec, String key, int min){
		int n = 0;
		n = Integer.parseInt(sec.getValue(key));
		if (n < min) throw new IllegalArgumentException("The value of the attribute is out of range");
		else return n;
		
	}
	/**
	 * Parse a double depending on a minimun value
	 * @param sec
	 * @param key
	 * @param min
	 * @throws IllegalArgumentException
	 * @return double
	 */
	public default double parseDoub (IniSection sec, String key, int min){
		double n = 0;
		n = Double.parseDouble(sec.getValue(key));
		if (n < min) throw new IllegalArgumentException("The value of the attribute is out of range");
		else return n;
	}
	/**
	 * Parse an ID array using the method isValidId()
	 * @param sec
	 * @param key
	 * @throws IllegalArgumentException
	 * @return ArrayList<String>
	 */
	public default ArrayList <String> parseIdList (IniSection sec, String key){
		
		String string = sec.getValue(key);
		int index = 0;
		String[]  stringArray = string.split(",");
		ArrayList <String> idList = new ArrayList<>();
		for (int i = 0; i < stringArray.length; i++){
			if (isValidId(stringArray[i])) {
				idList.add(index, stringArray[i]);
				index++;
			}
			else {
				throw new IllegalArgumentException ("Invalid ID");
			}
		}
		
		return idList;
	}
}
