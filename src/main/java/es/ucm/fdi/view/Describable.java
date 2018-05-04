package es.ucm.fdi.view;

import java.util.Map;
/**
 * Interface implemented by the clases that will fill 
 * the interface Tables
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public interface Describable {

	void describe(Map<String, String> out);
}