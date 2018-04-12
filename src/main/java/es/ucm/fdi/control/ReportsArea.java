package es.ucm.fdi.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class ReportsArea extends JTextArea{
	public ReportsArea() {
	    setEditable(false);
	    setLineWrap(true);
	    setWrapStyleWord(true);
	    //Border
	    Border b = BorderFactory.createLineBorder(Color.black, 2);
	    setBorder(BorderFactory.createTitledBorder(b, "Reports"));
	}
}
