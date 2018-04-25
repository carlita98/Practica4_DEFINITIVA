package es.ucm.fdi.view;

public enum Command {

	Load("Abrir", "Abriendo fichero..."),
	Save("Guardar", "Guardando fichero..."),
	Clear("Limpiar", "Eliminando zona de eventos..."),
	Event("Insertar", "Insertando eventos..."),
	Run("Ejecutar", "Ejecutando la simulación..."),
	Reset("Reiniciar", "Reiniciando la simulación"),
	GenerateReport("Generar informes", "Generando informes..."),
	DeleteReport("Eliminar informes", "Eliminando informes"),
	SaveReport("Guardar informes", "Guardando informes"),
	Exit("Salir", "Saliendo...");
	
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
