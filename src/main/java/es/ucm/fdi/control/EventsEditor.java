package es.ucm.fdi.control;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import es.ucm.fdi.model.trafficSimulator.Simulator;

public class EventsEditor extends JTextArea{

	public EventsEditor() {
		 setEditable(true);
		    setLineWrap(true);
		    setWrapStyleWord(true);
		    //Border
		    Border b = BorderFactory.createLineBorder(Color.black, 2);
		    setBorder(BorderFactory.createTitledBorder(b, "Events"));
	}
}
