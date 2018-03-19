package es.ucm.fdi.control.eventsBuilder;
import java.io.*;


import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.trafficSimulator.Simulator;
/**
 *  Recieve the outputFile and inputFile, load the IniSection and call the simulator
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Controller{

	private int time;
	private Simulator sim = new Simulator ();
	private String inputFile;
	private String outputFile;
	
	/**
	 * An array with the type of the Events that could be created
	 */
	EventBuilder []bs = new EventBuilder[] {new NewBikeEventBuilder(), 
			 new NewCarEventBuilder(),new NewVehicleEventBuilder(),new NewDirtEventBuilder(),
			 new NewLanesEventBuilder(),new NewRoadEventBuilder(),
	new MakeFaultyVehicleEventBuilder(), new NewRoundRobinEventBuilder(), new NewMostCrowedEventBuilder(),
	new NewJunctionEventBuilder ()};
	
	/**
	 * Constructor
	 * @param time
	 * @param inputFile
	 * @param outputFile
	 */
	public Controller(int time, String inputFile, String outputFile) {
		this.time = time;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}
	/**
	 * Go through the array of possibles Events and says which one is the type of the Event created
	 * @param sec
	 * @return Event 
	 */
	public Event parseSection (IniSection sec) {
		Event e = null;
		for (EventBuilder eb: bs){
			try{
				e = eb.parse(sec);
				if (e != null) break;
			}catch (IllegalArgumentException i){
				throw new IllegalArgumentException("There has been a problem parsing a Section", i);
			}
		}
		return e;
	}
	/**
	 * Load the data form inputFile into an IniSection and call simulator.execute()
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void controlExecute() throws FileNotFoundException, IOException, IllegalArgumentException{
		Ini read = new Ini ();
		read.load (new FileInputStream (inputFile));
		for (IniSection sec: read.getSections()) {
			try {
			Event newEvent = parseSection(sec);
			if (newEvent != null)sim.insertEvent(newEvent);
			}catch(IllegalArgumentException i) {
				System.out.println(i.getMessage());
			}
		}
		if (outputFile == null) sim.execute(time, System.out);
		else sim.execute(time, new FileOutputStream(outputFile));
	}

}
