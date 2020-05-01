/**
 * ChooseGenerateReport.java
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

import java.io.File;
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
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ChooseGenerateReport {

	// All valid integer month values
	final static Integer[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

	/**
	 * Creates the center dialogue box which allows the user to select the report
	 * they would like to create and display the selected report
	 * 
	 * @param farmLand to get data from
	 * @param stage of main GUI
	 * 
	 * @return vertical box containing UI controls for report generation
	 */
	static public VBox tabPane(FarmLand farmLand, Stage stage) {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		// Have one tab for each type of report
		Tab tab1 = new Tab("Farm Report", farmRep(farmLand, stage));
		Tab tab2 = new Tab("Annual Report", annualRep(farmLand,stage));
		Tab tab3 = new Tab("Monthly Report", monthRep(farmLand));
		Tab tab4 = new Tab("Range Report", rangeRep(farmLand));

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);
		tabPane.getTabs().add(tab4);

		VBox vBox = new VBox(tabPane);

		return vBox;
	}

	/**
	 * Defines the UI controls under the Farm Report tab. Will prompt user for a
	 * farm Id and a year, and when the generate button is pressed will display the
	 * corresponding report.
	 * 
	 * @return VBox containing UI controls for Farm Report
	 */
	static public VBox farmRep(FarmLand farmLand, Stage stage) {
		// Vertical box containing everything below
		VBox farmRep = new VBox();
		farmRep.setSpacing(30);

		// ComboBox gets a String from the user for the farm Id
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farmLand.getFarms()));
		VBox farmId = new VBox(new Label("Select Farm ID:"), idBox);

		// ComboBox gets an Integer from the user for the year number
		// Limited to years which there is data on to make it easier for
		// user to navigate
		ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(farmLand.getYears()));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		// Sub vertical box containing all user selections
		VBox selections = new VBox(30, farmId, year);

		// Button which is pressed to actually make the report
		Button gButton = new Button("Display Report");
		// Make green to distinguish
		gButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		gButton.setOnAction(e -> farmReportAction(farmLand, idBox.getValue(), yearBox.getValue()));

		// Button pressed to save Farm Report to file
		Button fileButton = new Button("Save Report to Directory");
		fileButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		DirectoryChooser chooser = new DirectoryChooser();
		fileButton.setOnAction(e -> {
			File dir = chooser.showDialog(stage);
			farmReportFile(dir, farmLand, idBox.getValue(), yearBox.getValue());

		});

		// Add all above nodes, plus an explanatory message
		farmRep.getChildren().addAll(selections, gButton,fileButton,
				new Label("Note: If an ID or a year is not selectable, there is not data on it"));

		return farmRep;

	}

	/**
	 * Defines the UI controls under the Farm Report tab. Will prompt user for a
	 * year, and when the generate button is pressed will display the corresponding
	 * report.
	 * 
	 * @return VBox containing the UI controls for the Annual Report tab
	 */
	static public VBox annualRep(FarmLand farmLand, Stage stage) {
		VBox yearRep = new VBox();
		yearRep.setSpacing(30);

		// ComboBox gets Integer from user for the year selection
		// Limited to years which have data on them in order to simplify
		// for user.
		ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(farmLand.getYears()));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		// Button which is pressed to generate report
		Button gButton = new Button("Display Report");
		// Make green to distinguish
		gButton.setOnAction(e -> annualReportAction(farmLand, yearBox.getValue()));
		gButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		
		// Button pressed to save Farm Report to file
		Button fileButton = new Button("Save Report to Directory");
		// Make it green to distinguish
		fileButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		DirectoryChooser chooser = new DirectoryChooser();
		fileButton.setOnAction(e -> {
			File dir = chooser.showDialog(stage);
			annualReportFile(dir, farmLand, yearBox.getValue());

		});

		// Add all above nodes to main box, along with explanatory message
		yearRep.getChildren().addAll(year, gButton, fileButton,
				new Label("Note: If year is not selectable, there is no data on it"));

		return yearRep;

	}

	/**
	 * Defines the UI controls under the Month Report tab. Will prompt user for a
	 * year and month, and when the generate button is pressed will display the
	 * corresponding report.
	 * 
	 * @return a VBox containing all UI controls for the Month Report tab
	 */
	static public VBox monthRep(FarmLand farmLand) {
		VBox monthRep = new VBox();
		monthRep.setSpacing(30);

		// Box containing all selections
		VBox selections = new VBox();
		selections.setSpacing(30);

		// Used to get an Integer representing the selected year
		ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(farmLand.getYears()));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		// Used to get an Integer representing the selected month
		ComboBox<Integer> monthBox = new ComboBox<Integer>(FXCollections.observableArrayList(months));
		VBox month = new VBox(new Label("Select Month:"), monthBox);

		selections.getChildren().addAll(year, month);

		// Button which is pressed to actually make the report
		Button gButton = new Button("Display Report");
		gButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		gButton.setOnAction(e -> monthlyReportAction(farmLand, yearBox.getValue(), monthBox.getValue()));

		// Add all above nodes along with an explanatory message
		monthRep.getChildren().addAll(selections, gButton,
				new Label("Note: If year is selectable, there is no data on it"));

		return monthRep;
	}

	/**
	 * Defines the UI controls under the Range Report tab. Will prompt user for two
	 * dates, and when the generate button is pressed will display the corresponding
	 * report.
	 * 
	 * @return VBox containing UI controls for Range Report tab
	 */
	static public VBox rangeRep(FarmLand farmLand) {
		VBox rangeRep = new VBox();
		rangeRep.setSpacing(30);

		// Box containing selection prompts
		VBox selections = new VBox();
		selections.setSpacing(30);

		// Gets the start date from the user
		DatePicker startBox = new DatePicker();
		startBox.setEditable(false);
		VBox start = new VBox(new Label("Select start date:"), startBox);

		// Gets the end date from the user
		DatePicker endBox = new DatePicker();
		endBox.setEditable(false);
		VBox end = new VBox(new Label("Selected end date:"), endBox);

		selections.getChildren().addAll(start, end);

		// Button which is pressed to actually make report
		Button gButton = new Button("Display Report");
		gButton.setStyle("-fx-background-color: #99ff99 ; -fx-border-color: #005500; ");
		gButton.setOnAction(e -> rangeReportAction(farmLand, startBox.getValue(), endBox.getValue()));

		// Add all nodes to main VBox
		rangeRep.getChildren().addAll(selections, gButton);

		return rangeRep;
	}

	/**
	 * Generates and displays a farm report for the given farmId and the given year.
	 * 
	 * @param farmId to make report on
	 * @param year   to make report on
	 */
	static public void farmReportAction(FarmLand farmLand, String farmId, Integer year) {
		ReportFarm fm = new ReportFarm(farmLand, farmId, year);
		fm.centerOnScreen();
	}

	/**
	 * Generates and displays an annual report for the given year.
	 * 
	 * @param year to make report on
	 */
	static public void annualReportAction(FarmLand farmLand, Integer year) {
		ReportAnnual annRep = new ReportAnnual(farmLand, year);
		annRep.centerOnScreen();

	}

	/**
	 * Generates and displays a month report for the given year and month.
	 * 
	 * @param year  to make report on
	 * @param month to make report on
	 */
	static public void monthlyReportAction(FarmLand farmLand, Integer year, Integer month) {
		ReportMonthly monthRep = new ReportMonthly(farmLand, year, month);
		monthRep.centerOnScreen();
	}

	/**
	 * Generates and displays a range report from the given start date to the given
	 * end date.
	 * 
	 * @param start date to begin range on
	 * @param end   date to end range on
	 */
	static public void rangeReportAction(FarmLand farmLand, LocalDate start, LocalDate end) {
		ReportRange rangeRep = new ReportRange(farmLand, start, end);
		rangeRep.centerOnScreen();
	}

	/**
	 * Tries to save the farm report for the given data to the given directory.
	 * If it cannot do this for whatever reason, displays an error message.
	 * 
	 * @param dir directory to save report to
	 * @param farmLand to get data from
	 * @param farmId of farm to make report on
	 * @param yearNum of year to make report on
	 */
	static public void farmReportFile(File dir, FarmLand farmLand, String farmId, Integer yearNum) {
		if (dir == null || farmLand == null || farmId == null || yearNum == null) {
			Stage responseStage = new Stage();
			responseStage.setTitle("Farm Report");
			String response = "Must choose directory, farm, and year";

			responseStage.setScene(new Scene(new Label(response), 400, 300));
			responseStage.show();
		} else {

			try {
				ReportFarm.printToDirectory(dir, farmLand, farmId, yearNum);

			} catch (Exception e) {
				Stage responseStage = new Stage();
				responseStage.setTitle("Farm Report");
				String response = "Unexpected Error: " + e.getMessage();

				responseStage.setScene(new Scene(new Label(response), 400, 300));
				responseStage.show();

			}
		}
	}
	
	/**
	 * Tries to save the annual report for the given data to the given directory.
	 * If it cannot do this for whatever reason, displays an error message.
	 * 
	 * @param dir directory to save file in
	 * @param farmLand to get data from
	 * @param yearNum to make report on
	 */
	static public void annualReportFile(File dir, FarmLand farmLand, Integer yearNum) {
		if (dir == null || farmLand == null || yearNum == null) {
			// display error message
			Stage responseStage = new Stage();
			responseStage.setTitle("Annual Report");
			String response = "Must choose directory and year";

			responseStage.setScene(new Scene(new Label(response), 400, 300));
			responseStage.show();
			
		} else {

			try {
				// try to print to text file
				ReportAnnual.printToDirectory(dir, farmLand, yearNum);

			} catch (Exception e) {
				// display error message if something goes wrong
				Stage responseStage = new Stage();
				responseStage.setTitle("Annual Report");
				String response = "Unexpected Error: " + e.getMessage();

				responseStage.setScene(new Scene(new Label(response), 400, 300));
				responseStage.show();

			}
		}
	}

}
