/**
 * ChooseAddFarm
 * Author:   Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date: 4/29/2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 *
 * List Collaborators: 
 *
 * Other Credits: 
 *
 * Known Bugs: 
 */
package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ChooseAddFarm - Used to display box in which user can add a new farm id
 * to their data structure from Main.java
 * 
 * @author menzia (2020)
 *
 */
public class ChooseAddFarm {
	
	private final static int WINDOW_WIDTH = 350;
	private final static int WINDOW_HEIGHT = 200;
	
	private FarmLand farmLand;
	
	/**
	 * Constructs an instance of ChooseAddFarm with the
	 * given FarmLand object to modify.
	 * 
	 * @param farmLand
	 */
	public ChooseAddFarm(FarmLand farmLand) {
		this.farmLand = farmLand;
	}
	
	/**
	 * Returns a Box containing the UI controls for adding
	 * a farm with a given farm ID to the data structure.
	 * 
	 * @return VBox with UI controls for adding a farm
	 */
	public VBox addFarmBox() {
		VBox addFarm = new VBox();
		addFarm.setSpacing(30);

		// Gets text from user for the new farm Id
		TextField farmIdField = new TextField();
		VBox farmId = new VBox(new Label(" Enter Farm ID to add:"), farmIdField);

		// Button which is pressed to actually create new farm
		Button gButton = new Button("Submit");
		gButton.setOnAction(e -> addFarmAction(farmIdField));

		addFarm.getChildren().addAll(farmId, gButton);

		return addFarm;

	}
	
	/**
	 * Adds a farm with the given ID to the collection of farms
	 * 
	 * @param farmId to add to structure
	 */
	public void addFarmAction(TextField farmIdField) {
		String farmId = farmIdField.getText();
		Stage responseStage = new Stage();
		String response;
		
		if (farmId == null || farmId.length() == 0) {
			response = " No farm id selected";
			
		} else if (farmLand.contains(farmId)) {
			response = " Data structure already contains that farm";
			
		} else if (farmId.contains(",")) {
			response = " Farm not added: ID cannot contain commas";
			
		} else {
			farmLand.addFarm(farmId);
			response = " The farm \"" + farmId + "\" was successfully added.";
			
		}
		
		Scene responseScene = new Scene(new Label(response),WINDOW_WIDTH,WINDOW_HEIGHT);
		
		// show response, successful or unsuccessful
		responseStage.setScene(responseScene);
		responseStage.show();
		farmIdField.clear();
		
		
	}
}
