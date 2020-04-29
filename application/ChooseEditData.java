/**
 * ChooseEditData.java
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date:     4/29/2020
 * 
 * Course:   CS400
 * Semester: Spring 2020 
 * Lecture:  001/002/001
 *
 * List Collaborators: 
 * 
 * Other Credits:
 *
 * Know Bugs: 
 */
package application;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

public class ChooseEditData {

	// All valid integer values for a month
	final static Integer[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

	// farms are hard-coded in here, but in actual
	// implementation will be calculated from the FarmLand
	// object given
	static String[] farms = { "Farm A", "Farm B", "Farm C" };

	/**
	 * Creates the center dialogue box which allows the user to select the report
	 * they would like to create. In actual implementation will need to pass in
	 * access to the FarmLand object so that a report can be created
	 * 
	 * @return VBox containing UI for data edit section
	 */
	static public VBox tabPane(FarmLand farmLand) {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		//Make one tab for each type of edit
		Tab tab1 = new Tab("Add Farm", addFarm());
		Tab tab2 = new Tab("Edit Weight", editWeight());
		Tab tab3 = new Tab("Add Weight", addWeight());

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);

		VBox vBox = new VBox(tabPane);

		return vBox;
	}

	/**
	 * Returns a Box containing the UI controls for adding
	 * a farm with a given farm ID to the data structure.
	 * 
	 * @return VBox with UI controls for adding a farm
	 */
	static public VBox addFarm() {
		VBox addFarm = new VBox();
		addFarm.setSpacing(30);

		// Gets text from user for the new farm Id
		TextField farmIdField = new TextField();
		VBox farmId = new VBox(new Label("Enter Farm ID to add:"), farmIdField);

		// Button which is pressed to actually create new farm
		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> addFarmAction(farmIdField.getText()));

		addFarm.getChildren().addAll(farmId, gButton);

		return addFarm;

	}

	/**
	 *  Returns a VBox which contains the UI controls
	 *  for changing the weight of a given farm on a
	 *  given date to a given number
	 *  
	 *  @return VBox with UI controls for editing weight
	 */
	static public VBox editWeight() {
		VBox editWeight = new VBox();
		editWeight.setSpacing(30);

		// Gets farm Id from user
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farms));
		VBox farmId = new VBox(new Label("Select Farm:"), idBox);

		// Gets date from user
		DatePicker dateBox = new DatePicker();
		VBox date = new VBox(new Label("Select Date:"), dateBox);

		// Gets weight from user
		Spinner<Integer> weightBox = new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
		VBox weight = new VBox(new Label("Select Weight to Set(lbs)"), weightBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> editWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getValue()));

		// Include all above nodes plus an explanatory label
		editWeight.getChildren().addAll(selections, gButton,
				new Label("Note: Must first add the farm if not already included."));

		return editWeight;

	}

	/**
	 *  Returns a VBox which contains the UI controls
	 *  for adding to the weight of a given farm on a
	 *  given date by a given number
	 *  
	 *  @return VBox with UI controls for adding weight
	 */
	static public VBox addWeight() {
		VBox addWeight = new VBox();
		addWeight.setSpacing(30);

		// Gets farm Id from user
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farms));
		VBox farmId = new VBox(new Label("Select Farm:"), idBox);

		// Gets date from user
		DatePicker dateBox = new DatePicker();
		VBox date = new VBox(new Label("Select Date:"), dateBox);

		//Gets the weight to add from user
		Spinner<Integer> weightBox = new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
		weightBox.setEditable(true);
		VBox weight = new VBox(new Label("Select Weight to Add(lbs)"), weightBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> editWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getValue()));

		// Include all above nodes plus an explanatory label
		addWeight.getChildren().addAll(selections, gButton,
				new Label("Note: Must first add the farm if not already included."));

		return addWeight;

	}

	/**
	 * Adds a farm with the given ID to the collection of farms
	 * 
	 * TODO: implement once data structure finished
	 * 
	 * @param farmId to add to structure
	 */
	static public void addFarmAction(String farmId) {
		// TODO: Implement
		System.out.println("Add Farm: " + farmId);
	}

	/**
	 * Changes the weight associated to the given farm
	 * on the given date to the given number.
	 * 
	 * TODO: implement once data structure finished
	 * 
	 * @param farmId to change weight of
	 * @param date to change weight on
	 * @param weight to change to
	 */
	static public void editWeightAction(String farmId, LocalDate date, Integer weight) {
		// TODO: Implement using FarmLand
		System.out.println("Edit Weight: " + farmId + ", " + date + ", " + weight + "lbs");

	}

	/**
	 * Adds the given amount of weight to the given farm
	 * on the given date.
	 * 
	 * TODO: implement once data structure finished
	 * 
	 * @param farmId to change weight of
	 * @param date to change weight on
	 * @param weight to add to previous weight
	 */
	static public void addWeightAction(String farmId, LocalDate date, Integer weight) {
		// TODO: Implement
		System.out.println("Add Weight: " + farmId + ", " + date + ", " + weight + "lbs");
	}

}
