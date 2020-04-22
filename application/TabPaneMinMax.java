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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneMinMax {

	final static Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
	
	//farms and years are hard-coded in here, but in actual
	//implementation will be calculated from the FarmLand
	//object given
	static String[] farms = {"Farm A","Farm B","Farm C"};
	static Integer[] years = {2017,2018,2019,2020};
	
	// Creates the center dialogue box which allows the user to
	// select the report they would like to create.
	// In actual implementation will need to pass in access
	// to the FarmLand object so that a report can be created
	static public VBox tabPane() {

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		Tab tab1 = new Tab("By Month", byMonth());
		Tab tab2 = new Tab("By Farm", byFarm());

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);

		VBox vBox = new VBox(tabPane);
		
		return vBox;
	}

	static public VBox byMonth() {
		VBox farmRep = new VBox();
		farmRep.setSpacing(30);
		
		ComboBox<String> idBox = new ComboBox<String>(
				FXCollections.observableArrayList(farms)
				);
		VBox farmId = new VBox(new Label("Select Farm ID:"), idBox);

		ComboBox<Integer> yearBox = new ComboBox<Integer>(
				FXCollections.observableArrayList(years)
				);
		VBox year = new VBox(new Label("Select Year:"), yearBox);

		VBox selections = new VBox(30,farmId, year);
		
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> byMonthAction(idBox.getValue(), yearBox.getValue()));
		
		farmRep.getChildren().addAll(selections, gButton, new Label("Note: If an ID or a year is not selectable, there is not data on it"));
		
		return farmRep;

	}
	
	static public VBox byFarm() {
		VBox monthRep = new VBox();
		monthRep.setSpacing(30);
		
		VBox selections = new VBox();
		selections.setSpacing(30);
		
		ComboBox<Integer> yearBox = new ComboBox<Integer>(
				FXCollections.observableArrayList(years)
				);
		VBox year = new VBox(new Label("Select Year:"), yearBox);
		
		ComboBox<Integer> monthBox = new ComboBox<Integer>(
				FXCollections.observableArrayList(months)
				);
		VBox month = new VBox(new Label("Select Month:"), monthBox);
		
		selections.getChildren().addAll(year,month);
		
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> byFarmAction(yearBox.getValue(), monthBox.getValue()));
		
		
		monthRep.getChildren().addAll(selections, gButton, new Label("Note: If year is selectable, there is no data on it"));
		
		return monthRep;
	}
	
	// Generate a scene containing the min/max/average
	// for a given farm on a given year by month
	static public void byMonthAction(String farmId, Integer year) {
		//TODO:Implement actual version
		System.out.println("By Month: " + farmId + " " + year);
	}
	
	//Generates a table view format annual report
	//Actual version will need to take month and year
	//As input
	static public void byFarmAction(Integer year, Integer month) {
		//TODO: Implement actual version
		System.out.println("By Farm: " + year + "/" + month);
		
	}
	
}

