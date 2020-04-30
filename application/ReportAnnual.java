/**
 * ReportAnnual.java
 * Author: Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date: 4/29/2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001/002/001
 *
 * List Collaborators: 
 *
 * Other Credits: 
 *
 * Known Bugs: 
 */
package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 
 * FarmReport - Generates a Stage displaying an Annual Report for a given
 * FarmLand object and a given year. If data given is invalid, displays error
 * message.
 *
 */
public class ReportAnnual extends Stage {

	/**
	 * Displays an Annual Report to the screen given the data in the parameters
	 * 
	 * @param farmLand to get report on
	 * @param yearNum  of year to make report on
	 */
	ReportAnnual(FarmLand farmLand, Integer yearNum) {
		// Table and the things containing it
		Scene scene;
		TableView<AnnualRow> table;
		VBox vbox = new VBox();

		// Columns used in the report
		TableColumn<AnnualRow, String> farmCol = new TableColumn<AnnualRow, String>("Farm ID");
		TableColumn<AnnualRow, String> totalweight = new TableColumn<AnnualRow, String>("Total Milk Weight(lbs)");
		TableColumn<AnnualRow, String> percent = new TableColumn<AnnualRow, String>("Percentage of Total(%)");

		table = new TableView<AnnualRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		farmCol.setMinWidth(100);
		farmCol.setCellValueFactory(new PropertyValueFactory<AnnualRow, String>("farm"));

		// total weight column
		totalweight.setMinWidth(140);
		totalweight.setCellValueFactory(new PropertyValueFactory<AnnualRow, String>("weight"));

		// percentage column
		percent.setMinWidth(140);
		percent.setCellValueFactory(new PropertyValueFactory<AnnualRow, String>("percentage"));

		Label title = new Label();

		if (farmLand == null || yearNum == null) {
			title.setText("Error: Must choose year");

		} else {
			// if inputs are valid, create table
			table.getColumns().addAll(farmCol, totalweight, percent);
			table.setItems(getData(farmLand, yearNum));
			title.setText("Annual Report: " + yearNum);

		}

		// Format table and show it
		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(true);
		this.setTitle("Annual Report");
		this.setWidth(450);
		this.setHeight(500);

		// show table
		this.setScene(scene);
		this.show();

	}

	/**
	 * 
	 * AnnualRow - Store data for a single row of the annual report.
	 * 
	 *
	 */
	public static class AnnualRow {
		private SimpleStringProperty farm;// id of farm
		private SimpleLongProperty weight;// long value of weight
		private SimpleStringProperty percentage;// string representing percentage

		/**
		 * Construct row with the given values
		 * 
		 * @param farm       id of farm
		 * @param weight     weight in pounds
		 * @param percentage percentage among all farms
		 */
		public AnnualRow(String farm, Long weight, String percentage) {
			this.farm = new SimpleStringProperty(farm);
			this.weight = new SimpleLongProperty(weight);
			this.percentage = new SimpleStringProperty(percentage);

		}

		public String getFarm() {
			return farm.get();
		}

		public void setFarm(String farm) {
			this.farm.set(farm);
		}

		public Long getWeight() {
			return weight.get();
		}

		public void setWeight(Integer w) {
			weight.set(w);
		}

		public String getPercentage() {
			return percentage.get();
		}

		public void setPercent(String percentage) {
			this.percentage.set(percentage);
		}

	}

	/**
	 * Creates an ObservableList of AnnualRows with the data corresponding to the
	 * given year and returns it
	 * 
	 * @param farmLand to obtain data from
	 * @param yearNum  of year to make annual table on
	 * @return ObservableList with data for Annual Report
	 */
	private static ObservableList<AnnualRow> getData(FarmLand farmLand, Integer yearNum) {
		try {
			// stores rows in order
			ArrayList<AnnualRow> farmList = new ArrayList<AnnualRow>();

			// Total weight among all farms for calculating percentage
			long totalWeight = farmLand.getAllFarmsYearTotal(yearNum);

			for (String farm : farmLand.getFarms()) {

				// Calculate the farms annual weight and it's percentage among
				// all farms. The Double.MIN_NORMAL is added to the denominator to avoid
				// divide by zero errors.
				long weight = farmLand.getFarm(farm).getYearTotal(yearNum);
				double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

				// Convert the farm and percentage to strings for display
				String farmString = farm;
				String percentageString = Double.toString(percentage);

				// Take four significant figures of percentage(plus the decimal point)
				int endIndex = Math.min(4, percentageString.length());
				percentageString = percentageString.substring(0, endIndex);

				// Some formating for special case where percentage is 100%
				if (percentageString.equals("100.")) {
					percentageString = "100";
				} else if (percentageString.equals("0.0")) {
					percentageString = "0";
				}

				// Add row of table with data for this farm
				AnnualRow newAnnualRow = new AnnualRow(farmString, weight, percentageString);

				farmList.add(newAnnualRow);

			}

			ObservableList<AnnualRow> data = FXCollections.observableArrayList(farmList);

			return data;
			
		} catch (Exception e) {
			
			// In case unexpected exception occurs, return empty data set
			// This should never occur because the values inputed are checked
			// for validity before being entered here, but just in case
			ArrayList<AnnualRow> farmList = new ArrayList<AnnualRow>();
			AnnualRow newAnnualRow = new AnnualRow("Error Making Table", 0L, e.getMessage());
			farmList.add(newAnnualRow);
						
			return FXCollections.observableArrayList(farmList);
		}

	}
}
