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
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.Tab;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * ChooseEditData - Stores a FarmLand object and has a method for returning a VBox
 * containing UI controls which allow the user to modify that FarmLand object
 * in various ways.
 *
 */
public class ChooseEditData {

	private final static int WINDOW_WIDTH = 350;
	private final static int WINDOW_HEIGHT = 200;

	// Keep track of the farmLand and of each box
	// in the tab pane
	private FarmLand farmLand;

	/**
	 * Create a new instance of the class with the given FarmLand object
	 * as it's basis for editing
	 * 
	 * @param farmLand that this UI will use and modify
	 */
	public ChooseEditData(FarmLand farmLand) {
		this.farmLand = farmLand;
	}

	/**
	 * Creates the center dialogue box which allows the user to select the report
	 * they would like to create. In actual implementation will need to pass in
	 * access to the FarmLand object so that a report can be created
	 * 
	 * @return VBox containing UI for data edit section
	 */
	public VBox tabPane() {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		// Make one tab for each type of edit
		Tab tab1 = new Tab("Edit Weight", editWeight());
		Tab tab2 = new Tab("Add Weight", addWeight());
		Tab tab3 = new Tab("View Weight", viewWeight());

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);

		VBox vBox = new VBox(tabPane);

		return vBox;
	}

	/**
	 * Returns a VBox which contains the UI controls for changing the weight of a
	 * given farm on a given date to a given number
	 * 
	 * @return VBox with UI controls for editing weight
	 */
	public VBox editWeight() {
		VBox editWeight = new VBox();
		editWeight.setSpacing(30);

		// Gets farm Id from user

		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farmLand.getFarms()));
		VBox farmId = new VBox(new Label(" Select Farm:"), idBox);

		// Gets date from user
		DatePicker dateBox = new DatePicker();
		dateBox.setEditable(false);
		VBox date = new VBox(new Label(" Select Date:"), dateBox);

		// Gets weight from user
		TextField weightBox = new TextField();
		VBox weight = new VBox(new Label(" Select Weight to Set(lbs)"), weightBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		gButton.setOnAction(e -> editWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getText()));

		// Include all above nodes plus an explanatory label
		editWeight.getChildren().addAll(selections, gButton,
				new Label(" Note: Must first add the farm if not already included."
						+ "\n\n Note: Weight must be non-negative integer written without commas"));

		return editWeight;

	}

	/**
	 * Returns a VBox which contains the UI controls for adding to the weight of a
	 * given farm on a given date by a given number
	 * 
	 * @return VBox with UI controls for adding weight
	 */
	public VBox addWeight() {
		VBox addWeight = new VBox();
		addWeight.setSpacing(30);

		// Gets farm Id from user
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farmLand.getFarms()));
		VBox farmId = new VBox(new Label(" Select Farm:"), idBox);

		// Gets date from user
		DatePicker dateBox = new DatePicker();
		dateBox.setEditable(false);
		VBox date = new VBox(new Label(" Select Date:"), dateBox);

		// Gets the weight to add from user
		TextField weightBox = new TextField();
		VBox weight = new VBox(new Label(" Select Weight to Add(lbs)"), weightBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		gButton.setOnAction(e -> addWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getText()));

		// Include all above nodes plus an explanatory label
		addWeight.getChildren().addAll(selections, gButton,
				new Label(" Note: Must first add the farm if not already included."
						+ "\n\n Note: Weight must be a non-negative integer written without commas"));

		return addWeight;

	}

	/**
	 * Box containing UI controls for the user to view the weight of a given farm on
	 * a given day
	 * 
	 * @return VBox containing UI controls for viewing daily weights
	 */
	private VBox viewWeight() {
		VBox viewWeight = new VBox();
		viewWeight.setSpacing(30);

		// Gets farm Id from user
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farmLand.getFarms()));
		VBox farmId = new VBox(new Label(" Select Farm:"), idBox);

		// Gets date from user
		DatePicker dateBox = new DatePicker();
		dateBox.setEditable(false);
		VBox date = new VBox(new Label(" Select Date:"), dateBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		gButton.setOnAction(e -> viewWeightAction(idBox.getValue(), dateBox.getValue()));

		// Include all above nodes plus an explanatory label
		viewWeight.getChildren().addAll(selections, gButton,
				new Label(" Note: Must first add the farm if not already included."));

		return viewWeight;
	}

	/**
	 * Changes the weight associated to the given farm on the given date to the
	 * given number.
	 * 
	 * Also displays a stage which displays a descriptive success or failure
	 * message.
	 * 
	 * @param farmId to change weight of
	 * @param date   to change weight on
	 * @param weight to change to
	 */
	public void editWeightAction(String farmId, LocalDate date, String weightString) {
		Stage responseStage = new Stage();
		responseStage.setTitle("Edit Weight");
		String response;

		try {
			// Attempt to parse integer from the input string
			Integer weight = Integer.parseInt(weightString);

			// Catch various errors up front, if none apply then try to set weight
			if (farmId == null || date == null || weight == null) {
				response = " Error: Must select ID, date, and weight";

			} else if (!farmLand.contains(farmId)) {
				response = " Error: Farm ID is not contained in data structure";

			} else if (weight < 0) {
				response = " Error: Weight must be non-negative";
			} else {
				farmLand.setDailyWeight(farmId, weight, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
				response = " Successfully set weight of " + farmId + " on " + date + " to " + weight + " lbs";

			}

			// Give error if the weight string cannot be parsed
		} catch (NumberFormatException e) {
			response = " Error: Could not parse weight as an integer";

			// Give error if any other exception occurs
		} catch (Exception f) {
			response = " Error Editing Weight: " + f.getMessage();

		}

		// Display error or success message
		Scene responseScene = new Scene(new Label(response), WINDOW_WIDTH, WINDOW_HEIGHT);
		responseStage.setScene(responseScene);
		responseStage.show();

	}

	/**
	 * Adds the given amount of weight to the given farm on the given date.
	 * 
	 * Also displays stage with a descriptive success or failure message
	 * 
	 * @param farmId to change weight of
	 * @param date   to change weight on
	 * @param weight to add to previous weight
	 */
	public void addWeightAction(String farmId, LocalDate date, String weightString) {
		Stage responseStage = new Stage();
		responseStage.setTitle("Add Weight");
		String response;// store string that will be displayed to user(success/fail message)

		try {
			// Try to parse the weight from the string
			Integer weight = Integer.parseInt(weightString);

			// Catch various erros up front. If none apply, try to add the given weight
			if (farmId == null || date == null || weight == null) {
				response = " Error: Must select ID, date, and weight";

			} else if (!farmLand.contains(farmId)) {
				response = " Error: Farm ID is not contained in data structure";

			} else if (weight < 0) {
				response = " Error: Weight must be non-negative";

			} else {
				farmLand.addToDailyWeight(farmId, weight, date.getYear(), date.getMonthValue(), date.getDayOfMonth());

				response = " Successfully added " + weight + " lbs to " + farmId + " on " + date;
			}

			// if weight cannot be parsed, display error
		} catch (NumberFormatException e) {
			response = " Error: Could not parse weight as an integer";

			// if any other exception occurs, display error
		} catch (Exception f) {
			response = " Error adding weight: " + f.getMessage();
		}

		// Display message of success or failure
		Scene responseScene = new Scene(new Label(response), WINDOW_WIDTH, WINDOW_HEIGHT);
		responseStage.setScene(responseScene);
		responseStage.show();
	}

	/**
	 * Displays a stage which shows the weight stored in the given farm on the given
	 * date. If an error occurs, the stage will instead contain a descriptive error
	 * message.
	 * 
	 * @param farmId to view weight of
	 * @param date   to view weight on
	 */
	private void viewWeightAction(String farmId, LocalDate date) {
		Stage responseStage = new Stage();
		responseStage.setTitle("View Weight");
		String response;

		// catch various errors up front. If none apply, try to display the weight
		if (farmId == null || date == null) {
			response = " Must select ID and date";

		} else if (!farmLand.contains(farmId)) {
			response = " Farm ID is not contained in data structure";

		} else {
			try {

				long weight = farmLand.getFarm(farmId).getDailyWeight(date.getYear(), date.getMonthValue(),
						date.getDayOfMonth());

				response = " Farm ID: " + farmId + "\n Date: " + date + "\n Weight : " + weight + " lbs";

				// if anything above causes an exception, display error with descripitve message
			} catch (Exception e) {
				response = " Error accessing weight: " + e.getMessage();
			}

		}

		// Display informative message or error message
		Scene responseScene = new Scene(new Label(response), WINDOW_WIDTH, WINDOW_HEIGHT);
		responseStage.setScene(responseScene);
		responseStage.show();
	}

}
