/**
 * RangeReport.java 
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
 * RangeReport - Creates the display for a ranged report. For use in TabPaneGenerateReport.java
 * 
 * @author menzia (2020)
 *
 */
public class RangeReport extends Stage {

	private Scene scene; //encapsulates display
	private TableView<FarmRow> table; //where rows of data are stored
	private VBox vbox = new VBox(); //stores table and title

	private static TableColumn<FarmRow, String> farmCol = new TableColumn<FarmRow, String>("Farm ID");
	private static TableColumn<FarmRow, String> totalWeights = new TableColumn<FarmRow, String>(
			"Total Milk Weight(lbs)");
	private static TableColumn<FarmRow, String> percent = new TableColumn<FarmRow, String>("Percentage of Total(%)");

	/**
	 * Creates a Range Report display for the given FarmLand object and given
	 * start and end dates
	 */
	RangeReport(FarmLand farmLand, LocalDate start, LocalDate end) {
		table = new TableView<FarmRow>();
		vbox = new VBox();

		scene = new Scene(new Group());
		table.setEditable(true);

		// month column
		farmCol.setMinWidth(100);
		farmCol.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("farm"));

		// total weights column
		totalWeights.setMinWidth(140);
		totalWeights.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("weight"));

		// percentage column
		percent.setMinWidth(140);
		percent.setCellValueFactory(new PropertyValueFactory<FarmRow, String>("percentage"));

		// Title placed above table in scene
		Label title = new Label();

		if (farmLand == null || start == null || end == null) {
			// if parameters invalid, make title an error message
			// and don't put any data in table
			title.setText("Error: Must choose start and end dates");

		} else {
			//otherwise, add data to table and give corresponding title
			table.getColumns().addAll(farmCol, totalWeights, percent);
			table.setItems(getData(farmLand, start, end));
			title.setText("Range Report: " + start + " until " + end);

		}

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
	 * FarmRow - Stores a row of data for a range report. First row is farm id,
	 * second row is weight, last row is percentage
	 * 
	 * @author menzia (2020)
	 *
	 */
	public static class FarmRow {
		private SimpleStringProperty farm;
		private SimpleLongProperty weight;
		private SimpleStringProperty percentage;

		/**
		 * Create FarmRow with the given properties
		 * @param farm id of row
		 * @param weights weight num in row
		 * @param percentage in row
		 */
		private FarmRow(String farm, Long weight, String percentage) {
			this.farm = new SimpleStringProperty(farm);
			this.weight = new SimpleLongProperty(weight);
			this.percentage = new SimpleStringProperty(percentage);

		}

		/**
		 * Returns the farm id for this row
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
	 * Creates an ObservableList of FarmRows with the data corresponding to the
	 * given range and returns it
	 * 
	 * @param FarmLand  farmLand to obtain data from
	 * @param LocalDate start of range
	 * @param LocalDate end of range
	 * @return an ObservableList with data for Annual Report
	 */
	private static ObservableList<FarmRow> getData(FarmLand farmLand, LocalDate start, LocalDate end) {
		ArrayList<FarmRow> farmList = new ArrayList<FarmRow>();

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
			long weight = farmLand.getFarm(farm).getRange(startYear, startMonth, startDay, endYear, endMonth, endDay);
			double percentage = 100 * weight / (totalWeight + Double.MIN_NORMAL);

			// Convert the month, weight, and percentage to strings for display
			String farmString = farm;
			String percentageString = Double.toString(percentage);

			// Take four significant figures of percentage(plus the decimal point)
			int endIndex = Math.min(4, percentageString.length());
			percentageString = percentageString.substring(0, endIndex);

			// Row of table with data for this farm
			FarmRow newFarmRow = new FarmRow(farmString, weight, percentageString);

			farmList.add(newFarmRow);

		}

		// list containg all data in the table
		ObservableList<FarmRow> data = FXCollections.observableArrayList(farmList);

		return data;

	}
}
