package view;

import controller.Controller;
import model.WorkingHour;
import program.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class WorkingHoursView extends AbstractView{

	public WorkingHoursView(Controller controller, Program vT) {
		super(controller, vT);
	
	}

	private TextField tfStartTime;
	private TextField tfEndTime;
	private CheckBox cb;
	
	
	public String getStartTime() {
		return tfStartTime.getText();
	}



	public void setStartTime(String  startTime) {
		this.tfStartTime.setText(startTime);
	}



	public String getEndTime() {
		return tfEndTime.getText();
	}



	public void setEndTime(String endTime) {
		this.tfEndTime.setText(endTime);
	}



	public boolean getCb() {
		return cb.isSelected();
	}



	public void setCb(boolean cb) {
		this.cb.setSelected(cb);
	}


	@Override
	public GridPane addEntity() {

		GridPane gpRoot = new GridPane();
		//gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(45);

		Text startTime = new Text("Starting time:");
		tfStartTime = new TextField("8");
		startTime.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");

		Text endTime = new Text("ending time:");
		tfEndTime = new TextField("17");
		endTime.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");

		Text workFromHome = new Text("Work at Home :");

		cb = new CheckBox();
		
		
			
		gpRoot.add(startTime, 0, 0);
		gpRoot.add(tfStartTime, 1, 0);

		gpRoot.add(endTime, 0, 1);
		gpRoot.add(tfEndTime, 1, 1);

		gpRoot.add(workFromHome, 0, 2);
		gpRoot.add(cb, 1, 2);

		return gpRoot;

	}
	
	@Override
	public GridPane showEntity() {
		return null;
	}
	
}
