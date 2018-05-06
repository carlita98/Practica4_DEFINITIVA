package es.ucm.fdi.control;

import java.io.*;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Simulator;
import es.ucm.fdi.model.Simulator.EventType;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.builder.EventBuilder;

/**
 * Recieve the outputFile and inputFile, load the IniSection and call the
 * simulator
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Controller {

	private int time;
	private Simulator sim = new Simulator();
	private InputStream inputFile;
	private OutputStream outputFile;

	/**
	 * Constructor
	 * 
	 * @param time
	 * @param inputFile
	 * @param outputFile
	 * @throws FileNotFoundException
	 */
	public Controller(int time, InputStream inputFile, 
			OutputStream outputFile) throws FileNotFoundException {
		this.time = time;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public void setInputFile(InputStream inputFile) {
		this.inputFile = inputFile;
	}

	public OutputStream getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(OutputStream outputFile) {
		this.outputFile = outputFile;
	}

	public Controller(int time, InputStream inputFile) {
		this.time = time;
		this.inputFile = inputFile;
	}

	public Simulator getSim() {
		return sim;
	}

	/**
	 * Go through the array of possibles Events and says which one is the type of
	 * the Event created
	 * 
	 * @param sec
	 * @return Event
	 */
	public Event parseSection(IniSection sec) {
		Event e = null;
		// Iterates through the EventBuilder array
		for (EventBuilder eb : EventBuilder.bs) {
			try {
				e = eb.parse(sec);
				if (e != null) {
					break;
				}
			} catch (IllegalArgumentException i) {
				StringBuilder sb = new StringBuilder();
				sb.append("There has been a problem parsing a Section");
				sb.append(sec);
				throw new IllegalArgumentException(sb.toString(), i);
			}
		}

		return e;
	}

	/**
	 * Load the data form inputFile into an IniSection and call simulator.execute()
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void loadEvents() {
		Ini read ;
		try {
			read = new Ini(inputFile);
			for (IniSection sec : read.getSections()) {
				try {
					Event newEvent = parseSection(sec);
					if (newEvent != null) {
						sim.insertEvent(newEvent);
					}
				} catch (IllegalArgumentException i) {
					StringBuilder sb = new StringBuilder();
					sb.append("Error in the simulation\n");
					sb.append(i.getMessage());
					getSim().fireUpdateEvent(EventType.ERROR, sb.toString());
				}
			}
		} catch (IOException e) {
			getSim().fireUpdateEvent(EventType.ERROR,"Error in the simulation\n");
		}
		
	}

	public void run() throws FileNotFoundException, IOException, IllegalArgumentException {
		loadEvents();
		sim.execute(time, outputFile);
	}

}
