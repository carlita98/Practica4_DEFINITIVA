package es.ucm.fdi.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class SimWindow extends JFrame{
	
	public SimWindow() {
		super("Traffic Simulator");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		addBars();
		addEventsEditor();
		setSize(1000, 1000);
		setVisible(true);
	}
	
	private void addBars () {
		// instantiate actions
		SimulatorAction load = new SimulatorAction(
				"Abrir", "open.png", "Abrir archivos",
				KeyEvent.VK_O, "control O", 
				()-> System.err.println("abriendo..."));
		
		SimulatorAction save = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_S, "control S", 
				()-> System.err.println("guardando..."));
		
		SimulatorAction clear = new SimulatorAction(
				"Limpiar", "clear.png", "Limpiar eventos",
				KeyEvent.VK_C, "control C",
				()->System.err.println("limpiando..."));
		
		SimulatorAction redirectOutput = new SimulatorAction(
				"Insertar", "events.png", "Insertar eventos",
				KeyEvent.VK_I, "control I", 
				()->System.err.println("insertando..."));
		
		SimulatorAction run = new SimulatorAction(
				"Ejecutar", "play.png", "Ejecutar la simulación",
				KeyEvent.VK_E, "control E", 
				()->System.err.println("ejecutando..."));
		
		SimulatorAction reset = new SimulatorAction(
				"Reiniciar", "reset.png", "Reiniciar",
				KeyEvent.VK_R, "control R", 
				()->System.err.println("reiniciando..."));
		
		SimulatorAction generateReport = new SimulatorAction(
				"Generar Informe", "report.png", "Generar informes",
				KeyEvent.VK_A, "control G", 
				()->System.err.println("generando informes..."));
		
		SimulatorAction deleteReport = new SimulatorAction(
				"Eliminar Informes", "delete_report.png", "Eliminar informes",
				KeyEvent.VK_A, "control shift E", 
				()->System.err.println("eliminando informes..."));
		
		SimulatorAction saveReport = new SimulatorAction(
				"Guardar Informes", "save_report.png", "Guardar informes",
				KeyEvent.VK_A, "control shift S", 
				()->System.err.println("guardando informe..."));
		
		SimulatorAction exit = new SimulatorAction(
				"Salir", "exit.png", "Salir de la aplicacion",
				KeyEvent.VK_A, "control shift X", 
				()-> System.exit(0));
		
		JToolBar bar = new JToolBar();
		bar.add(load);
		bar.add(save);
		bar.add(clear);
		bar.add(redirectOutput);
		bar.add(run);
		bar.add(reset);
		bar.add(generateReport);
		bar.add(deleteReport);
		bar.add(saveReport);
		bar.add(exit);
		
		add(bar, BorderLayout.NORTH);

		// add actions to menubar, and bar to window
		JMenu file = new JMenu("File");
		file.add(load);
		file.add(save);
		file.add(saveReport);
		file.add(exit);
		
		JMenu simulator = new JMenu("Simulator");
		simulator.add(reset);
		simulator.add(run);
		simulator.add(redirectOutput);
		
		JMenu report = new JMenu("Report");
		report.add(generateReport);
		report.add(deleteReport);
		
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		menu.add(simulator);
		menu.add(report);
		setJMenuBar(menu);
				
	}
	private void addEventsEditor(){
	    JTextArea  textArea = new JTextArea(5, 30);
	    textArea.setEditable(true);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    add(textArea, BorderLayout.CENTER);
	    //Border
	    Border b = BorderFactory.createLineBorder(Color.black, 2);
	    add(textArea, BorderLayout.LINE_START);
	    textArea.setBorder(BorderFactory.createTitledBorder(b, "Events"));
	    //ScrollPane
	    JScrollPane area = new JScrollPane( textArea,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    area.setPreferredSize(new Dimension(300, 200));
	    add()
	}
}
