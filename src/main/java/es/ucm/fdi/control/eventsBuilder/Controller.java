package es.ucm.fdi.control.eventsBuilder;

import java.io.*;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.trafficSimulator.Simulator;
public class Controller implements EventBuilder{
	private int time;
	private Simulator sim = new Simulator ();
	private String inputFile;
	private String outputFile;
	
	public Controller(int time, String inputFile, String outputFile) {
		this.time = time;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}
	
	public void controlExecute() throws FileNotFoundException, IOException, IllegalArgumentException{
		Ini read = new Ini ();
		read.load (new FileInputStream (inputFile));
		for (IniSection sec: read.getSections()) {
			Event newEvent = parseSection(sec);
			if (newEvent != null)sim.insertEvent(newEvent);
		}
		if (outputFile == null) sim.execute(time, System.out);
		else sim.execute(time, new FileOutputStream(outputFile));
	}

	@Override
	public Event parse(IniSection sec) {
		// TODO Auto-generated method stub
		return null;
	}

}
