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
import javafx.scene.control.Tab;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * ChooseEditData - TODO Describe the purpose of this user-defined type
 * @author menzia (2020)
 *
 */
public class ChooseEditData {

	private final static int WINDOW_WIDTH = 350;
	private final static int WINDOW_HEIGHT = 200;
	
	// Keep track of the farmLand and of each box 
	// in the tab pane
	private FarmLand farmLand;
	
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

		//Make one tab for each type of edit
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
	 *  Returns a VBox which contains the UI controls
	 *  for changing the weight of a given farm on a
	 *  given date to a given number
	 *  
	 *  @return VBox with UI controls for editing weight
	 */
	public VBox editWeight() {
		VBox editWeight = new VBox();
		editWeight.setSpacing(30);

		// Gets farm Id from user
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farmLand.getFarms()));
		VBox farmId = new VBox(new Label(" Select Farm:"), idBox);

		// Gets date from user
		DatePicker dateBox = new DatePicker();
		VBox date = new VBox(new Label(" Select Date:"), dateBox);

		// Gets weight from user
		Spinner<Integer> weightBox = new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
		VBox weight = new VBox(new Label(" Select Weight to Set(lbs)"), weightBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> editWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getValue()));

		// Include all above nodes plus an explanatory label
		editWeight.getChildren().addAll(selections, gButton,
				new Label(" Note: Must first add the farm if not already included."));

		return editWeight;

	}

	/**
	 *  Returns a VBox which contains the UI controls
	 *  for adding to the weight of a given farm on a
	 *  given date by a given number
	 *  
	 *  @return VBox with UI controls for adding weight
	 */
	public VBox addWeight() {
		VBox addWeight = new VBox();
		addWeight.setSpacing(30);

		// Gets farm Id from user
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farmLand.getFarms()));
		VBox farmId = new VBox(new Label(" Select Farm:"), idBox);
		

		// Gets date from user
		DatePicker dateBox = new DatePicker();
		VBox date = new VBox(new Label(" Select Date:"), dateBox);

		//Gets the weight to add from user
		Spinner<Integer> weightBox = new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
		weightBox.setEditable(true);
		VBox weight = new VBox(new Label(" Select Weight to Add(lbs)"), weightBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date, weight);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> addWeightAction(idBox.getValue(), dateBox.getValue(), weightBox.getValue()));

		// Include all above nodes plus an explanatory label
		addWeight.getChildren().addAll(selections, gButton,
				new Label(" Note: Must first add the farm if not already included."));

		return addWeight;

	}

	/**
	 * Box containing UI controls for the user to view the weight of a
	 * given farm on a given day
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
		VBox date = new VBox(new Label(" Select Date:"), dateBox);

		// Box containing all selection controls
		VBox selections = new VBox(farmId, date);
		selections.setSpacing(30);

		// Button pressed to actually implement changes
		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> viewWeightAction(idBox.getValue(), dateBox.getValue()));

		// Include all above nodes plus an explanatory label
		viewWeight.getChildren().addAll(selections, gButton,
				new Label(" Note: Must first add the farm if not already included."));

		return viewWeight;
	}
	

	/**
	 * Changes the weight associated to the given farm
	 * on the given date to the given number.
	 * 
	 * Also displays a stage which displays a descriptive success or failure
	 * message.
	 * 
	 * @param farmId to change weight of
	 * @param date to change weight on
	 * @param weight to change to
	 */
	public void editWeightAction(String farmId, LocalDate date, Integer weight) {
		Stage responseStage = new Stage();
		responseStage.setTitle("Edit Weight");
		String response;
		
		if (farmId == null || date == null || weight == null) {
			response = " Must select ID, date, and weight";
			
		} else if (!farmLand.contains(farmId)) {
			response = " Farm ID is not contained in data structure";
			
		} else {
			try {
				
				farmLand.setDailyWeight(farmId, weight, date.getYear(), date.getMonthValue(),date.getDayOfMonth());
				
				response = " Successfully set weight of " + farmId + " on " + date + " to " + weight + " lbs";
				
			} catch (Exception e) {
				response = " Error setting weight: " + e.getMessage();
			}
			
		}
		
		Scene responseScene = new Scene(new Label(response), WINDOW_WIDTH, WINDOW_HEIGHT);
		responseStage.setScene(responseScene);
		responseStage.show();

	}

	/**
	 * Adds the given amount of weight to the given farm
	 * on the given date.
	 * 
	 * Also displays stage with a descriptive success or failure message 
	 * 
	 * @param farmId to change weight of
	 * @param date to change weight on
	 * @param weight to add to previous weight
	 */
	public void addWeightAction(String farmId, LocalDate date, Integer weight) {
		Stage responseStage = new Stage();
		responseStage.setTitle("Add Weight");
		String response;
		
		if (farmId == null || date == null || weight == null) {
			response = " Must select ID, date, and weight";
			
		} else if (!farmLand.contains(farmId)) {
			response = " Farm ID is not contained in data structure";
			
		} else {
			try {
				
				farmLand.addToDailyWeight(farmId, weight, date.getYear(), date.getMonthValue(),date.getDayOfMonth());
				
				response = " Successfully added " + weight + " lbs to " + farmId + " on " + date;
				
			} catch (Exception e) {
				response = " Error adding weight: " + e.getMessage();
			}
			
		}
		
		Scene responseScene = new Scene(new Label(response), WINDOW_WIDTH, WINDOW_HEIGHT);
		responseStage.setScene(responseScene);
		responseStage.show();
	}
	
	/**
	 * Displays a stage which shows the weight stored in the
	 * given farm on the given date. If an error occurs, the stage
	 * will instead contain a descriptive error message.
	 * 
	 * @param farmId to view weight of
	 * @param date to view weight on
	 */
	private void viewWeightAction(String farmId, LocalDate date) {
		Stage responseStage = new Stage();
		responseStage.setTitle("View Weight");
		String response;
		
		if (farmId == null || date == null ) {
			response = " Must select ID and date";
			
		} else if (!farmLand.contains(farmId)) {
			response = " Farm ID is not contained in data structure";
			
		} else {
			try {
				
				long weight = farmLand.getFarm(farmId).getDailyWeight(date.getYear(), date.getMonthValue(),date.getDayOfMonth());
				
				response = " Farm ID: " + farmId + "\n Date: " + date + "\n Weight : " + weight + " lbs";
				
			} catch (Exception e) {
				response = " Error accessing weight: " + e.getMessage();
			}
			
		}
		
		Scene responseScene = new Scene(new Label(response), WINDOW_WIDTH, WINDOW_HEIGHT);
		responseStage.setScene(responseScene);
		responseStage.show();
	}

}
