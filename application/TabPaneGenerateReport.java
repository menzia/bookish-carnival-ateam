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

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneGenerateReport {

	Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
	
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

		Tab tab1 = new Tab("Farm Report", farmRep());
		Tab tab2 = new Tab("Annual Report", annualRep());
		Tab tab3 = new Tab("Monthly Report", new Label("Show all boats available"));
		Tab tab4 = new Tab("Date Report", new Label("Show all boats available"));

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);
		tabPane.getTabs().add(tab4);

		VBox vBox = new VBox(tabPane);
		
		return vBox;
	}

	static public VBox farmRep() {
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

		HBox hb = new HBox(30,farmId, year);
		
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> farmReportAction());
		
		farmRep.getChildren().addAll(hb, gButton);
		
		return farmRep;

	}
	
	static public VBox annualRep() {
		VBox yearRep = new VBox();
		yearRep.setSpacing(30);
		

		ComboBox<Integer> yearBox = new ComboBox<Integer>(
				FXCollections.observableArrayList(years)
				);
		VBox year = new VBox(new Label("Select Year:"), yearBox);
		
		Button gButton = new Button("Generate");
		gButton.setOnAction(e -> annualReportAction());
		
		yearRep.getChildren().addAll(year, gButton);
		
		return yearRep;

	}
	
	

	// click on generate to generate a table view format farm report
	static public void farmReportAction() {
		farmReport fm = new farmReport();
		fm.centerOnScreen();
	}
	
	//Generates a table view format annual report
	static public void annualReportAction() {
		//TODO: Implement
		
	}

}
