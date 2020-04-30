/**
 * ChooseMinMax.java
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

import javafx.collections.FXCollections;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChooseMinMax {
	// All possible valid month values
	private final static Integer[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private static FarmLand farmland;
	// farms and years are hard-coded in here, but in actual
	// implementation will be calculated from the FarmLand
	// object given
	private static String[] farms = { "Farm A", "Farm B", "Farm C" };
	private static Integer[] years = { 2017, 2018, 2019, 2020 };

	
	public ChooseMinMax(FarmLand farmland) {
	    this.farmland = farmland;
	}
	
	
	
	/**
	 * Creates the center dialogue box which allows the user to select the Min/Max
	 * report they would like to create. In actual implementation will need to pass
	 * in access to the FarmLand object so that a report can be created
	 * 
	 * @return VBox containg UI for Min/Max display
	 */
	public VBox tabPane() {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		// Create tab for each of the types of Min/Max displays
		Tab tab1 = new Tab("By Month", byMonth());
		Tab tab2 = new Tab("By Farm", byFarm());

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);

		VBox vBox = new VBox(tabPane);

		return vBox;
	}

	/**
	 * Returns a VBox containing the UI controls necessary to display the
	 * Min/Max/Avg for a given farm in a given year.
	 * 
	 * @return VBox with UI controls for the By Month tab
	 */
	private VBox byMonth() {
		VBox farmRep = new VBox();
		farmRep.setSpacing(30);

		// Gets farm Id from user
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farmland.getFarms()));
		VBox farmId = new VBox(new Label("Select Farm ID:"), idBox);

		// Gets year from user
        ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(farmland.getYears()));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		// Box containing all selection controls
		VBox selections = new VBox(30, farmId, year);

		
		// Button pressed to actually generate report
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> byMonthAction(idBox.getValue(), yearBox.getValue()));

		// Add all above nodes plus an explanatory label
		farmRep.getChildren().addAll(selections, gButton,
				new Label("Note: If an ID or a year is not selectable, there is not data on it"));

		return farmRep;

	}

	/**
	 * Returns a VBox containing the UI controls necessary to display the
	 * Min/Max/Avg for a given year across all farms.
	 * 
	 * @return VBox with UI controls for the By Farm tab
	 */
	private VBox byFarm() {
		VBox monthRep = new VBox();
		monthRep.setSpacing(30);

		// Box containing all selection controls
		VBox selections = new VBox();
		selections.setSpacing(30);

		// Gets year number from user
        ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(farmland.getYears()));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		// Gets month number from user
		ComboBox<Integer> monthBox = new ComboBox<Integer>(FXCollections.observableArrayList(months));
		VBox month = new VBox(new Label("Select Month:"), monthBox);

		selections.getChildren().addAll(year, month);

		// Button pressed to actually make report
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> byFarmAction(yearBox.getValue(), monthBox.getValue()));

		// Add all above nodes plus explanatory label
		monthRep.getChildren().addAll(selections, gButton,
				new Label("Note: If year is selectable, there is no data on it"));

		return monthRep;
	}

	/**
	 * Generate a scene containing the min/max/average for a given farm on a given
	 * year by month
	 * 
	 * TODO:Implement when data structure finished
	 * 
	 * @param farmId to make report of
	 * @param year   to make report of
	 */
	private void byMonthAction(String farmId, Integer year) {
		// TODO:Implement actual version
		System.out.println("By Month: " + farmId + " " + year);
<<<<<<< HEAD
		byMonthReport monthRep = new byMonthReport(farmland,farmId,year);
		monthRep.show();	
=======
		ReportMinMaxMonthly monthRep = new ReportMinMaxMonthly(farmland,farmId,year);
		monthRep.centerOnScreen();
		
		
		
		
>>>>>>> 40cc3a32713c21acee80a3e32ae8f5c85bb0a2c2
	}

	/**
	 * Generate a scene containing the min/max/avg across all farms for a given year
	 * and month
	 * 
	 * TODO:Implement when data structure finished
	 * 
	 * @param year to make report of
	 * @param month to make report of
	 */
	private void byFarmAction(Integer year, Integer month) {
		// TODO: Implement actual version
		MinMaxbyFarm monthRep = new MinMaxbyFarm(farmland, year, month);
		monthRep.show();
		System.out.println("By Farm: " + year + "/" + month);

	}

}
