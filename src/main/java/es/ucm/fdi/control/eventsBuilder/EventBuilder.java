package es.ucm.fdi.control.eventsBuilder;
import java.lang.Character;
import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;

public interface EventBuilder {
	EventBuilder []bs = new EventBuilder[] {new NewVehicleEventBuilder(), new NewRoadEventBuilder(),
	new MakeFaultyVehicleEventBuilder(), new NewJunctionEventBuilder ()};
	
	public Event parse(IniSection sec);
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
	
	public default boolean isValidId (String id){
		for (int i =0; i < id.length() ; i++){
			if (!Character.isLetterOrDigit(id.charAt(i)) && id.charAt(i)!= '_'){
				throw new IllegalArgumentException ("Invalid ID");
			}
		}
		return true;
	}
	
	public default int parseInt (IniSection sec, String key, int min){
		int n = 0;
		n = Integer.parseInt(sec.getValue(key));
		if (n < min) throw new IllegalArgumentException("The value of the attribute is out of range");
		else return n;
		
	}
	
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
