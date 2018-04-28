package es.ucm.fdi.view;
/**
 * An enum type that contains the name and the message that will be shown
 * when the client uses the interface
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public enum Command {

	Load("Abrir", "Opening file..."),
	Save("Guardar", "Saving file..."),
	Clear("Limpiar", "Deleting events..."),
	Event("Insertar", "Inserting events..."),
	Run("Ejecutar", "Executing the simulation..."),
	Reset("Reiniciar", "Reset the simulation..."),
	GenerateReport("Generar informes", "Generating report..."),
	DeleteReport("Eliminar informes", "Deleting report..."),
	SaveReport("Guardar informes", "Saving report..."),
	Exit("Salir", "Coming out...");
	
	private String name;
	private String text;
	
	private Command(String name, String text) {
		this.name = name;
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return text;
	}
	
}
