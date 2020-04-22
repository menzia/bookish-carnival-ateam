/**
 * Main.java created by {user} on Alienware M15 in ateam_MilkWeights
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date:     @date
 * 
 * Course:   CS400
 * Semester: Spring 2020 
 * Lecture:  001
 * 
 * IDE:      Eclipse IDE for Java Developers
 * Version:  2019-12
 * Build id:
 * 
 * Device:
 * OS:
 * Version:
 * OS Build:
 *
 * List Collaborators: name, wisc.edu, lecture number
 * 
 * Other Credits: Other source, website or people
 *
 * Know Bugs: known unresolved bugs
 */
package application;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneEditData {

	final static Integer[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

	// farms and years are hard-coded in here, but in actual
	// implementation will be calculated from the FarmLand
	// object given
	static String[] farms = { "Farm A", "Farm B", "Farm C" };

	// Creates the center dialogue box which allows the user to
	// select the report they would like to create.
	// In actual implementation will need to pass in access
	// to the FarmLand object so that a report can be created
	static public VBox tabPane() {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		Tab tab1 = new Tab("Add Farm", addFarm());
		Tab tab2 = new Tab("Edit Weight", editWeight());
		Tab tab3 = new Tab("Add Weight", addWeight());

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);

		VBox vBox = new VBox(tabPane);

		return vBox;
	}

	static public VBox addFarm() {
		VBox addFarm = new VBox();
		addFarm.setSpacing(30);

		TextField farmIdField = new TextField();
		VBox farmId = new VBox(new Label("Enter Farm ID to add:"), farmIdField);

		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> addFarmAction(farmIdField.getText()));

		addFarm.getChildren().addAll(farmId, gButton);

		return addFarm;

	}

	static public VBox editWeight() {
		VBox editWeight = new VBox();
		editWeight.setSpacing(30);

		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farms));
		VBox farmId = new VBox(new Label("Select Farm:"), idBox);

		DatePicker dateBox = new DatePicker();
		VBox date = new VBox(new Label("Select Date:"), dateBox);

		Spinner<Integer> weightBox = new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
		VBox weight = new VBox(new Label("Select Weight to Set(lbs)"), weightBox);

		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> editWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getValue()));

		editWeight.getChildren().addAll(selections, gButton,
				new Label("Note: Must first add the farm if not already included."));

		return editWeight;

	}

	static public VBox addWeight() {
		VBox addWeight = new VBox();
		addWeight.setSpacing(30);

		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farms));
		VBox farmId = new VBox(new Label("Select Farm:"), idBox);

		DatePicker dateBox = new DatePicker();
		VBox date = new VBox(new Label("Select Date:"), dateBox);

		Spinner<Integer> weightBox = new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
		weightBox.setEditable(true);
		VBox weight = new VBox(new Label("Select Weight to Add(lbs)"), weightBox);

		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> editWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getValue()));

		addWeight.getChildren().addAll(selections, gButton,
				new Label("Note: Must first add the farm if not already included."));

		return addWeight;

	}

	// Add a farm with the given ID to the collection of farms
	static public void addFarmAction(String farmId) {
		// TODO: Implement
		System.out.println("Add Farm: " + farmId);
	}

	// Generates a table view format annual report
	// Actual version will need to take month and year
	// As input
	static public void editWeightAction(String farmId, LocalDate date, Integer weight) {
		// TODO: Implement using FarmLand
		System.out.println("Edit Weight: " + farmId + ", " + date + ", " + weight + "lbs");

	}

	static public void addWeightAction(String farmId, LocalDate date, Integer weight) {
		// TODO: Implement
		System.out.println("Add Weight: " + farmId + ", " + date + ", " + weight + "lbs");
	}

}
