package view;

import controller.Controller;
import program.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CompanyView extends AbstractView {

	public CompanyView(Controller controller, Program view) {
		super(controller, view);
	}

	@Override
	public GridPane addEntity() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(15);

		Label lblCompanyName = new Label("Company Name:");
		Label lblProfitHour = new Label("Profit Per Hour:");
		
		TextField tfCompanyName = new TextField();
		TextField tfProfitHour = new TextField();
		
		// add company button
		Button companyBtn = new Button("Add Company");
		companyBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				double profit = 0;
				if (tfCompanyName.getText().length() == 0) { // if company name is empty
					view.showErrorMessage("Company name is empty");
					return;
				}
				if (tfProfitHour.getText().length() == 0) { // if profit hour is empty
					view.showErrorMessage("Profit hour is empty");
					return;
				}
				try {
					profit = Double.parseDouble(tfProfitHour.getText()); // if profit is not a number
				} catch (NumberFormatException n) {
					view.showErrorMessage("Profit must be a number");
					return;
				}

				if (!(tfCompanyName.getText().length() == 0 && tfProfitHour.getText().length() == 0)) { // company created
																									// successfully
					String firstLetter = tfCompanyName.getText().substring(0, 1); // capitalize the first letter of
																				// company name
					String remainingLetters = tfCompanyName.getText().substring(1, tfCompanyName.getText().length());
					firstLetter = firstLetter.toUpperCase();
					String companyNameUpperCase = firstLetter + remainingLetters;
					controller.createCompany(companyNameUpperCase, profit);
					view.showSuccessMessage("The Company " + companyNameUpperCase + " was created successfully");
				}

			}

		});
		
		gpRoot.add(lblProfitHour, 0, 1);
		gpRoot.add(lblCompanyName, 0, 0);
		
		gpRoot.add(tfCompanyName, 1, 0);
		gpRoot.add(tfProfitHour, 1, 1);
		

		lblCompanyName.setTextFill(Color.BLACK);
		lblProfitHour.setTextFill(Color.BLACK);
		companyBtn.setTextFill(Color.SADDLEBROWN);
		
		lblCompanyName.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		lblProfitHour.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		companyBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		
		
		gpRoot.add(companyBtn, 0, 3); // company button
		return gpRoot;
	}

	@Override
	public GridPane showEntity() {
		// TODO Auto-generated method stub
		return null;
	}

}
