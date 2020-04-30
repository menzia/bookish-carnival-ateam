/**
 * ReportRange.java 
 * Author: Linyi Lyu (llyu4@wisc.edu) Ethan Huang (ihuang22@wisc.edu) Alex Menzia(menzia@wisc.edu)
 * Date: 4/29/2020
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001/002/001
 *
 * List Collaborators: n/a
 * 
 * Other Credits: n/a
 *
 * Known Bugs: n/a
 */
package application;

import java.time.LocalDate;
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
 * ReportRange - Generates a Stage displaying a Range Report for the given
 * FarmLand object and the given range of dates
 * 
 * If data is invalid, displays an error message
 *
 */
public class ReportRange extends Stage {

	/**
	 * Creates a Range Report display for the given FarmLand object and given start
	 * and end dates
	 */
	ReportRange(FarmLand farmLand, LocalDate start, LocalDate end) {
		// Table and it's containers
		Scene scene;
		TableView<RangeRow> table;
		VBox vbox = new VBox();

		// The columns used in the table
		TableColumn<RangeRow, String> farmCol = new TableColumn<RangeRow, String>("Farm ID");
		TableColumn<RangeRow, String> weightCol = new TableColumn<RangeRow, String>("Total Milk Weight(lbs)");
		TableColumn<RangeRow, String> percentCol = new TableColumn<RangeRow, String>("Percentage of Total(%)");

		table = new TableView<RangeRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		farmCol.setMinWidth(100);
		farmCol.setCellValueFactory(new PropertyValueFactory<RangeRow, String>("farm"));

		// weight column
		weightCol.setMinWidth(140);
		weightCol.setCellValueFactory(new PropertyValueFactory<RangeRow, String>("weight"));

		// percentage column
		percentCol.setMinWidth(140);
		percentCol.setCellValueFactory(new PropertyValueFactory<RangeRow, String>("percentage"));

		// Title placed above table in scene
		Label title = new Label();

		if (farmLand == null || start == null || end == null) {
			// if parameters invalid, make title an error message
			// and don't put any data in table
			title.setText("Error: Must choose start and end dates");

		} else {
			// otherwise, add data to table and give corresponding title
			table.getColumns().addAll(farmCol, weightCol, percentCol);
			table.setItems(getData(farmLand, start, end));
			title.setText("Range Report: " + start + " until " + end);

		}

		// Format table and display it
		title.setFont(new Font("Arial", 20));

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(title, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		table.setEditable(false);
		this.setTitle("Range Report");
		this.setWidth(450);
		this.setHeight(500);

		// show the above scene
		this.setScene(scene);
		this.show();

	}

	/**
	 * 
	 * RangeRow - Stores a row of data for a range report
	 * 
	 */
	public static class RangeRow {
		private SimpleStringProperty farm;// id of farm
		private SimpleLongProperty weight;// total weight of farm in range
		private SimpleStringProperty percentage;// percentage among all farms

		/**
		 * Create RangeRow with the given values
		 * 
		 * @param farm       id of row
		 * @param weights    weight num in row
		 * @param percentage in row
		 */
		private RangeRow(String farm, Long weight, String percentage) {
			this.farm = new SimpleStringProperty(farm);
			this.weight = new SimpleLongProperty(weight);
			this.percentage = new SimpleStringProperty(percentage);

		}

		/**
		 * Returns the farm id for this row
		 * 
		 * @return farm id
		 */
		public String getFarm() {
			return farm.get();
		}

		/**
		 * Sets the farm id for this row to the given String
		 * 
		 * @param farm string to set farm id to
		 */
		public void setFarm(String farm) {
			this.farm.set(farm);
		}

		/**
		 * Returns the weight value stored in this row
		 * 
		 * @return weight value
		 */
		public Long getWeight() {
			return weight.get();
		}

		/**
		 * Sets the weight value for this row to the given value
		 * 
		 * @param w weight to set
		 */
		public void setWeights(Long weight) {
			this.weight.set(weight);
		}

		/**
		 * Returns the percentage value for this row
		 * 
		 * @return percentage value
		 */
		public String getPercentage() {
			return percentage.get();
		}

		/**
		 * Sets the percentage value for this row to the given value
		 * 
		 * @param percentage value to set
		 */
		public void setPercent(String percentage) {
			this.percentage.set(percentage);
		}

	}

	/**
	 * Creates an ObservableList of RangeRows with the data corresponding to the
	 * given range and returns it
	 * 
	 * @param FarmLand  farmLand to obtain data from
	 * @param LocalDate start of range
	 * @param LocalDate end of range
	 * @return an ObservableList with data for Annual Report
	 */
	private static ObservableList<RangeRow> getData(FarmLand farmLand, LocalDate start, LocalDate end) {
		try {

			// stores rows in order
			ArrayList<RangeRow> farmList = new ArrayList<RangeRow>();

			// values for the start date
			int startYear = start.getYear();
			int startMonth = start.getMonthValue();
			int startDay = start.getDayOfMonth();

			// values for the end date
			int endYear = end.getYear();
			int endMonth = end.getMonthValue();
			int endDay = end.getDayOfMonth();

			// total weight for all farms in this range
			long totalWeight = farmLand.getAllFarmRange(startYear, startMonth, startDay, endYear, endMonth, endDay);

			for (String farm : farmLand.getFarms()) {

				// Calculate weight for this farm and percentage of total weight for the range
				// The Double.MIN_NORMAL is added to the denominator to avoid
				// divide by zero errors.
				long weight = farmLand.getFarm(farm).getRange(startYear, startMonth, startDay, endYear, endMonth,
						endDay);
				double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

				// Convert the month, weight, and percentage to strings for display
				String farmString = farm;
				String percentageString = Double.toString(percentage);

				// Take four significant figures of percentage(plus the decimal point)
				int endIndex = Math.min(4, percentageString.length());
				percentageString = percentageString.substring(0, endIndex);

				// Some formatting for special cases
				if (percentageString.equals("100.")) {
					percentageString = "100";
				} else if (percentageString.equals("0.0")) {
					percentageString = "0";
				}
				
				// Row of table with data for this farm
				RangeRow newRangeRow = new RangeRow(farmString, weight, percentageString);

				farmList.add(newRangeRow);

			}

			// list containing all data in the table
			ObservableList<RangeRow> data = FXCollections.observableArrayList(farmList);

			return data;

		} catch (Exception e) {
			
			// In case unexpected exception occurs, return empty data set
			// This should never occur because the values inputed are checked
			// for validity before being entered here, but just in case
			ArrayList<RangeRow> farmList = new ArrayList<RangeRow>();
			RangeRow newMonthRow = new RangeRow("Error Making Table", 0L, e.getMessage());
			farmList.add(newMonthRow);

			return FXCollections.observableArrayList(farmList);
		}
	}
}
