package es.ucm.fdi.control;

import java.awt.event.*;
import javax.swing.JFrame;

public class SimWindow extends JFrame{
	public SimWindow() {
		super("Traffic Simulator");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		addBars();
		setSize(1000, 1000);
		setVisible(true);
	}
	
	private void addBars () {
		// instantiate actions
				SimulatorAction salir = new SimulatorAction(
						"Salir", "exit.png", "Salir de la aplicacion",
						KeyEvent.VK_A, "control shift X", 
						()-> System.exit(0));
				SimulatorAction guardar = new SimulatorAction(
						"Guardar", "save.png", "Guardar cosas",
						KeyEvent.VK_S, "control S", 
						()-> System.err.println("guardando..."));
	}
}
