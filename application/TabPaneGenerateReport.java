/**
 * TabPaneGenerateReport.java
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
 * List Collaborators: n/a
 * 
 * Other Credits: n/a
 *
 * Know Bugs: n/a
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneGenerateReport {

	// All valid integer month values
	final static Integer[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

	// farms and years are hard-coded in here, but in actual
	// implementation will be calculated from the FarmLand
	// object given
	static String[] farms = { "Farm A", "Farm B", "Farm C" };
	static Integer[] years = { 2017, 2018, 2019, 2020 };

	/**
	 * Creates the center dialogue box which allows the user to select the report
	 * they would like to create. In actual implementation will need to pass in
	 * access to the FarmLand object so that a report can be created
	 * 
	 * @return vertical box containing UI controls for report generation
	 */
	static public VBox tabPane() {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		//Have one tab for each type of report
		Tab tab1 = new Tab("Farm Report", farmRep());
		Tab tab2 = new Tab("Annual Report", annualRep());
		Tab tab3 = new Tab("Monthly Report", monthRep());
		Tab tab4 = new Tab("Range Report", rangeRep());

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);
		tabPane.getTabs().add(tab4);

		VBox vBox = new VBox(tabPane);

		return vBox;
	}

	/**
	 * Defines the UI controls under the Farm Report tab.
	 * Will prompt user for a farm Id and a year, and when
	 * the generate button is pressed will display the corresponding
	 * report.
	 * 
	 * @return VBox containing UI controls for Farm Report
	 */
	static public VBox farmRep() {
		//Vertical box containing everything below
		VBox farmRep = new VBox();
		farmRep.setSpacing(30);

		//ComboBox gets a String from the user for the farm Id
		ComboBox<String> idBox = new ComboBox<String>(FXCollections.observableArrayList(farms));
		VBox farmId = new VBox(new Label("Select Farm ID:"), idBox);

		//ComboBox gets an Integer from the user for the year number
		ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(years));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		// Sub vertical box containg all user selections
		VBox selections = new VBox(30, farmId, year);

		//Button which is pressed to actually make the report
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> farmReportAction(idBox.getValue(), yearBox.getValue()));

		// Add all above nodes, plus an explanatory message
		farmRep.getChildren().addAll(selections, gButton,
				new Label("Note: If an ID or a year is not selectable, there is not data on it"));

		return farmRep;

	}

	/**
	 * Defines the UI controls under the Farm Report tab.
	 * Will prompt user for a year, and when the generate button
	 * is pressed will display the corresponding report.
	 * 
	 * @return VBox containing the UI controls for the Annual Report tab
	 */
	static public VBox annualRep() {
		VBox yearRep = new VBox();
		yearRep.setSpacing(30);

		//ComboBox gets Integer from user for the year selection
		ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(years));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		// Button which is pressed to generate report
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> annualReportAction(yearBox.getValue()));

		//Add all above nodes to main box, along with explanatory message
		yearRep.getChildren().addAll(year, gButton,
				new Label("Note: If year is not selectable, there is no data on it"));

		return yearRep;

	}

	/**
	 * Defines the UI controls under the Month Report tab.
	 * Will prompt user for a year and month, and when
	 * the generate button is pressed will display the corresponding
	 * report.
	 * 
	 * @return a VBox containing all UI controls for the Month Report tab
	 */
	static public VBox monthRep() {
		VBox monthRep = new VBox();
		monthRep.setSpacing(30);

		//Box containing all selctions
		VBox selections = new VBox();
		selections.setSpacing(30);

		//Used to get an Integer representing the selected year
		ComboBox<Integer> yearBox = new ComboBox<Integer>(FXCollections.observableArrayList(years));
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		//Used to get an Integer representing the selected month
		ComboBox<Integer> monthBox = new ComboBox<Integer>(FXCollections.observableArrayList(months));
		VBox month = new VBox(new Label("Select Month:"), monthBox);

		selections.getChildren().addAll(year, month);

		// Button which is pressed to actually make the report
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> monthlyReportAction(yearBox.getValue(), monthBox.getValue()));

		// Add all above nodes along with an explanatory message
		monthRep.getChildren().addAll(selections, gButton,
				new Label("Note: If year is selectable, there is no data on it"));

		return monthRep;
	}

	/**
	 * Defines the UI controls under the Range Report tab.
	 * Will prompt user for two dates, and when the generate button 
	 * is pressed will display the corresponding report.
	 *  
	 * @return VBox containing UI controls for Range Report tab
	 */
	static public VBox rangeRep() {
		VBox rangeRep = new VBox();
		rangeRep.setSpacing(30);

		// Box containing selection prompts
		VBox selections = new VBox();
		selections.setSpacing(30);

		// Gets the start date from the user
		DatePicker startBox = new DatePicker();
		VBox start = new VBox(new Label("Select start date:"), startBox);

		// Gets the end date from the user
		DatePicker endBox = new DatePicker();
		VBox end = new VBox(new Label("Selected end date:"), endBox);

		selections.getChildren().addAll(start, end);

		// Button which is pressed to actually make report
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> rangeReportAction(startBox.getValue(), endBox.getValue()));

		// Add all nodes to main VBox
		rangeRep.getChildren().addAll(selections, gButton);

		return rangeRep;
	}

	/**
	 * Generates and displays a farm report for the given farmId and
	 * the given year.
	 * 
	 * TODO: Finish once data structure complete
	 * 
	 * @param farmId to make report on
	 * @param year to make report on
	 */
	static public void farmReportAction(String farmId, Integer year) {
		// TODO:Implement non-hardcoded version
		System.out.println("Farm Report: " + farmId + " " + year);
		farmReport fm = new farmReport();
		fm.centerOnScreen();
	}

	/**
	 * Generates and displays an annual report for the given year.
	 * 
	 * TODO: Finish once data structure complete
	 * @param year to make report on
	 */
	static public void annualReportAction(Integer year) {
		// TODO: Implement using FarmLand
		System.out.println("Annual Report: " + year);

	}

	/**
	 * Generates and displays a month report for the given year
	 * and month.
	 * 
	 * TODO: Finish once data structure complete
	 * 
	 * @param year to make report on
	 * @param month to make report on
	 */
	static public void monthlyReportAction(Integer year, Integer month) {
		// TODO: Implement
		System.out.println("Month Report: " + year + "/" + month);
	}

	/**
	 * Generates and displays a range report from the given start
	 * date to the given end date.
	 * 
	 * TODO: Finish once data structure complete
	 * 
	 * @param start date to begin range on
	 * @param end date to end range on
	 */
	static public void rangeReportAction(LocalDate start, LocalDate end) {
		// TODO: Implement
		System.out.println("Range Report: " + start + " to " + end);
	}

}
