package view;

import controller.Controller;
import program.Program;
import javafx.scene.layout.GridPane;

public abstract class AbstractView {

	protected Controller controller;
	protected Program view;

	public AbstractView(Controller controller, Program view) {
		this.controller = controller;
		this.view = view;
	}

	public abstract GridPane addEntity();

	public abstract GridPane showEntity();

	
}
